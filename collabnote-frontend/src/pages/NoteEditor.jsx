import { useEffect, useState } from 'react';
import { useEditor, EditorContent } from '@tiptap/react';
import StarterKit from '@tiptap/starter-kit';
import Collaboration from '@tiptap/extension-collaboration';
import * as Y from 'yjs';
import { WebsocketProvider } from 'y-websocket';

export default function NoteEditor() {
  const [ydoc] = useState(() => new Y.Doc());
  const [provider] = useState(
    () => new WebsocketProvider('ws://localhost:1234', 'note-room-1', ydoc)
  );
  const [collaborators, setCollaborators] = useState([]);

  // Disconnect cleanly when the component unmounts
  useEffect(() => {
    return () => {
      provider.destroy();
    };
  }, [provider]);

  // Set this tab's local identity (name + color) on the awareness state
  useEffect(() => {
    const randomColor = '#' + Math.floor(Math.random() * 16777215).toString(16);
    const randomName = 'User-' + Math.floor(Math.random() * 1000);

    provider.awareness.setLocalStateField('user', {
      name: randomName,
      color: randomColor,
    });
  }, [provider]);

  // Track and update the list of currently connected collaborators
  useEffect(() => {
    const updateCollaborators = () => {
      const states = Array.from(provider.awareness.getStates().values());
      setCollaborators(states.filter(s => s.user).map(s => s.user));
    };

    provider.awareness.on('change', updateCollaborators);
    updateCollaborators(); // run once immediately

    return () => provider.awareness.off('change', updateCollaborators);
  }, [provider]);

  const editor = useEditor({
    extensions: [
      StarterKit.configure({
        undoRedo: false,
      }),
      Collaboration.configure({
        document: ydoc,
      }),
    ],
  });

  return (
    <div>
      <h1>Note Editor (live sync test)</h1>
      <div style={{ marginBottom: '10px' }}>
        <strong>Active now:</strong>{' '}
        {collaborators.map((user, i) => (
          <span
            key={i}
            style={{
              color: user.color,
              marginRight: '8px',
              fontWeight: 'bold',
            }}
          >
            {user.name}
          </span>
        ))}
      </div>
      <EditorContent editor={editor} />
    </div>
  );
}