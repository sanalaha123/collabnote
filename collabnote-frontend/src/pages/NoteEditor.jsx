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

  const editor = useEditor({
    extensions: [
      StarterKit.configure({ history: false }),
      Collaboration.configure({ document: ydoc }),
    ],
  });

  useEffect(() => {
    return () => {
      provider.destroy();
    };
  }, [provider]);

  return (
    <div>
      <h1>Note Editor (live sync test)</h1>
      <EditorContent editor={editor} />
    </div>
  );
}