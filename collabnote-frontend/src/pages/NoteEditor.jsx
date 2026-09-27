import {useEffect, useState } from 'react';
import { useEditor, EditorContent } from '@tiptap/react';
import StarterKit from '@tiptap/starter-kit';
import Collaboration from '@tiptap/extension-collaboration';
import * as Y from 'yjs';

export default function NoteEditor() {
  const [ydoc] = useState(() => new Y.Doc());
  useEffect(() => {
  ydoc.on('update', () => {
    console.log('Yjs doc updated:', ydoc.toJSON());
  });
}, [ydoc]);

  const editor = useEditor({
    extensions: [
      StarterKit.configure({
        history: false,
      }),
      Collaboration.configure({
        document: ydoc,
      }),
    ],
  });

  return (
    <div>
      <h1>Note Editor (local test — no sync yet)</h1>
      <EditorContent editor={editor} />
    </div>
  );
}
