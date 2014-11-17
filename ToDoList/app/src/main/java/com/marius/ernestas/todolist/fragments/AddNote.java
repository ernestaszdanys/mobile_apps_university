package com.marius.ernestas.todolist.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.marius.ernestas.todolist.R;
import com.marius.ernestas.todolist.database.Database;
import com.marius.ernestas.todolist.database.Note;

public class AddNote extends Fragment {

    private Database database;
    private EditText titleEditText;
    private TextView setDateButtonTextView;
    private Spinner spinnerImportance;
    private EditText descriptionEditText;
    private Button addButton;
    private int importance;
    public static String date;

    public AddNote(Database database) {
        this.database = database;
        importance = 0;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_note, container, false);

        titleEditText = (EditText) rootView.findViewById(R.id.editTextTitle);
        setDateButtonTextView = (TextView) rootView.findViewById(R.id.textButtonSetDate);
        spinnerImportance = (Spinner) rootView.findViewById(R.id.spinnerImportance);
        descriptionEditText = (EditText) rootView.findViewById(R.id.editTextDescription);
        addButton = (Button) rootView.findViewById(R.id.buttonAdd);

        handleSpinner();

        setDateButtonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        return rootView;
    }

    private void setDate() {
        DialogFragment newFragment = new DatePickerFragment(setDateButtonTextView);
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private void handleSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.importance, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerImportance.setAdapter(arrayAdapter);

        spinnerSelection();
    }

    private void spinnerSelection() {
        spinnerImportance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                importance = (int) parent.getItemIdAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void saveData() {
        if (checkData()) {
            Note note = new Note(date, importance, titleEditText.getText().toString(), descriptionEditText.getText().toString());

            database.addNote(note);
            Toast.makeText(getActivity(), "Note has been added!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    private boolean checkData() {
        if (titleEditText.getText().toString().length() == 0 ||
                date == null ||
                descriptionEditText.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "All fields have to be filled!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
