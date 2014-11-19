package com.marius.ernestas.todolist.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
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

import java.util.Locale;

public class SettingsFragment extends Fragment {

    private Spinner spinnerLanguages;
    private SharedPreferences sharedPreferences;

    private String language = "languageKey";

    public SettingsFragment() {
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        spinnerLanguages = (Spinner) rootView.findViewById(R.id.spinnerLanguages);

        sharedPreferences = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        handleSpinner();

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

                String languageToLoad = null;
                switch (i) {
                    case 0 : languageToLoad = "en"; break;
                    case 1 : languageToLoad = "lt"; break;
                }
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = getResources().getConfiguration();
                config.locale = locale;

                Resources resources = getActivity().getBaseContext().getResources();
                resources.updateConfiguration(config, resources.getDisplayMetrics());

                if (sharedPreferences.getLong(language, 0) != i) {
                    editor.putLong(language, (parent.getItemIdAtPosition(i))).apply();
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

}

/*String languageToLoad = "lt";
Locale locale = new Locale(languageToLoad);
Locale.setDefault(locale);
Configuration config = getResources().getConfiguration();
config.locale = locale;
getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());*/

