package com.marius.ernestas.todolist.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.marius.ernestas.todolist.R;

public class AboutFragment extends Fragment {

    private Button contactButton;
    private Button likeButton;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        contactButton = (Button) rootView.findViewById(R.id.buttonContact);
        likeButton = (Button) rootView.findViewById(R.id.buttonLike);

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleContactButton();
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLikeButton();
            }
        });

        return rootView;
    }

    //TODO: test intents
    private void handleContactButton() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ernestas13@gmail.com", "zilinskas.m@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "I would like to contact you about your application!");
        startActivity(Intent.createChooser(emailIntent, ""));
    }

    private void handleLikeButton() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse("http://facebook.com/neegzistuoja"));
        startActivity(browserIntent);
    }
}
