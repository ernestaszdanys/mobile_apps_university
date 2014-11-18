package com.marius.ernestas.todolist.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.marius.ernestas.todolist.R;

public class SettingsFragment extends Fragment {

    private TextView notificationButtonTextView;
    private Spinner spinnerLanguages;
    private SharedPreferences sharedPreferences;

    private String notifications = "notificationsKey";
    private String language = "languageKey";

    public SettingsFragment() {
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        notificationButtonTextView = (TextView) rootView.findViewById(R.id.textButtonNotifications);
        spinnerLanguages = (Spinner) rootView.findViewById(R.id.spinnerLanguages);

        sharedPreferences = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        handleSpinner();
        handleNotificationButton();

        return rootView;
    }

    private void handleSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.languages, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguages.setAdapter(arrayAdapter);

        spinnerLanguages.setSelection((int) sharedPreferences.getLong(language, 0));

        spinnerSelection();
    }

    private void spinnerSelection() {
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        spinnerLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                editor.putLong(language, (parent.getItemIdAtPosition(i))).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    // FIXME: this is a ghetto way of doing a text button... I feel ashamed...
    private void handleNotificationButton() {
        if (sharedPreferences.getBoolean(notifications, true)) {
            notificationButtonTextView.setText(getResources().getString(R.string.on));
            notificationButtonTextView.setTextColor(getResources().getColor(R.color.textHighlight2));
        } else {
            notificationButtonTextView.setText(R.string.off);
            notificationButtonTextView.setTextColor(getResources().getColor(R.color.textSimple));
        }

        notificationButtonClick();
    }

    private void notificationButtonClick() {
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        notificationButtonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notificationButtonTextView.getText() == getResources().getString(R.string.off)) {
                    notificationButtonTextView.setText(getResources().getString(R.string.on));
                    notificationButtonTextView.setTextColor(getResources().getColor(R.color.textHighlight2));

                    editor.putBoolean(notifications, true).apply();
                } else {
                    notificationButtonTextView.setText(getResources().getString(R.string.off));
                    notificationButtonTextView.setTextColor(getResources().getColor(R.color.textSimple));

                    editor.putBoolean(notifications, false).apply();

                }
            }
        });
    }

}
