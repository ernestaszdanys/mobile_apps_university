package com.marius.ernestas.todolist.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.ListView;
import android.widget.Toast;

import com.marius.ernestas.todolist.NoteAdapter;
import com.marius.ernestas.todolist.R;
import com.marius.ernestas.todolist.database.Database;

public class DeleteNoteFragment extends DialogFragment {
    private Database database;
    private NoteAdapter noteAdapter;
    private int listViewPos;
    private int databasePos;

    public DeleteNoteFragment(Database database, NoteAdapter noteAdapter, int listViewPos, int databasePos) {
        this.database = database;
        this.noteAdapter = noteAdapter;
        this.listViewPos = listViewPos;
        this.databasePos = databasePos;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_delete_note)
                .setPositiveButton(R.string.dialog_delete_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        database.removeNote(databasePos);
                        noteAdapter.remove(listViewPos);
                        noteAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Note deleted successfully!", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(R.string.dialog_delete_denny, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }


}
