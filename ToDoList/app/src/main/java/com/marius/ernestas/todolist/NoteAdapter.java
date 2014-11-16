package com.marius.ernestas.todolist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.marius.ernestas.todolist.database.Note;

import java.util.List;

public class NoteAdapter extends BaseAdapter {
    private Context context;
    private List<Note> notes;

    public NoteAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Note getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.note_item, null);
        }

        ImageView importanceImageView = (ImageView) view.findViewById(R.id.imageViewImportance);
        TextView titleTextView = (TextView) view.findViewById(R.id.textViewTitle);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.textViewDescription);
        TextView dateTextView = (TextView) view.findViewById(R.id.textViewDate);
        TextView textViewId = (TextView) view.findViewById(R.id.textViewId);

        Note note = getItem(position);

        if (note != null) {
            switch (note.getImportance()) {
                case 0:
                    // Using deprecated setBackgroundDrawable method in order to support api level 10
                    importanceImageView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.important));
                    break;
                case 1:
                    importanceImageView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.major));
                    break;
                case 2:
                    importanceImageView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.primary));
                    break;
                default:
                    importanceImageView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.important));
                    break;
            }
            titleTextView.setText(note.getTitle());
            descriptionTextView.setText(note.getDescription());
            dateTextView.setText(note.getDate());
            textViewId.setText(note.getId() + "");
            //Toast.makeText(context, note.getId() + "" , Toast.LENGTH_LONG).show();
        }

        return view;
    }
}