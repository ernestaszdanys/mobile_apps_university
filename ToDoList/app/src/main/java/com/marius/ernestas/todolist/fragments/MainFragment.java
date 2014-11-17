package com.marius.ernestas.todolist.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.marius.ernestas.todolist.NoteAdapter;
import com.marius.ernestas.todolist.R;
import com.marius.ernestas.todolist.database.Database;

@SuppressLint("ValidFragment")
public class MainFragment extends Fragment {

    private Database database;

    public MainFragment(Database database) {
        this.database = database;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView notesListView = (ListView) rootView.findViewById(R.id.listViewNotes);
        Button newButton = (Button) rootView.findViewById(R.id.buttonNew);

        handleNotes(notesListView);

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddNote(database))
                        .addToBackStack(null).commit();
            }
        });

        return rootView;
    }

    public void handleNotes(final ListView notesListView) {

        if (database.getNoteCount() != 0) {
            final NoteAdapter noteAdapter = new NoteAdapter(getActivity(), database.getAllNotes());
            notesListView.setAdapter(noteAdapter);

            notesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView textViewId = (TextView) view.findViewById(R.id.textViewId);

                    Toast.makeText(getActivity(), textViewId.getText().toString() + "", Toast.LENGTH_LONG).show();

                    DeleteNoteFragment newFragment = new DeleteNoteFragment(database, Integer.parseInt(textViewId.getText().toString()));
                    newFragment.show(getActivity().getSupportFragmentManager(), "deleteConfirmation");

                    return false;
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_date:
                Toast.makeText(getActivity(), "Works like a charm!", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
