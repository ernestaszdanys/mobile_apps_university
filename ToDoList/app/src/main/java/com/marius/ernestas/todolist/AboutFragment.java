package com.marius.ernestas.todolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Ernestas on 2014.10.15.
 */
public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        Button contactButton = (Button) rootView.findViewById(R.id.contactButton);
        Button likeButton = (Button) rootView.findViewById(R.id.likeButton);

        return rootView;
    }
}
