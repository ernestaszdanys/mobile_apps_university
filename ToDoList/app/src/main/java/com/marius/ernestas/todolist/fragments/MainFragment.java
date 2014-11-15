package com.marius.ernestas.todolist.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

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

    private void handleNotes(ListView notesListView) {
        if (database.getNoteCount() != 0) {
            notesListView.setAdapter(new NoteAdapter(getActivity(), database.getAllNotes()));
        }
    }
}
