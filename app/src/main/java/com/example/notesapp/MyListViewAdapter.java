package com.example.notesapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyListViewAdapter extends ArrayAdapter<NotesModel> {
    LinearLayout linearLayout, linearLayout1;
    ListView lv;

    private List<NotesModel> notesModels;
    Context context;


    public MyListViewAdapter(@NonNull Context context, @NonNull List<NotesModel> notesModels) {
        super(context, R.layout.list_view, notesModels);
        this.context = context;
        this.notesModels = notesModels;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        final NotesModel notesModel = getItem(position);
        final LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View view = layoutInflater.inflate(R.layout.list_view,null,false);

        TextView titleText = view.findViewById(R.id.titleText);
        TextView descText = view.findViewById(R.id.descText);
        ImageButton deleteTask = view.findViewById(R.id.deleteNote);

        titleText.setText(notesModel.getTitle());
        descText.setText(notesModel.getDescription());

        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesModel deletenote = getItem(position);
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                dataBaseHelper.deleteNote(deletenote);
                remove(deletenote);
                notifyDataSetChanged();

                if(dataBaseHelper.getAllNotes().size() <= 0) {
                    lv = (ListView) view.getParent();
                    lv.setVisibility(View.GONE);
                    linearLayout = (LinearLayout) view.getParent().getParent();
                    linearLayout1 = linearLayout.findViewById(R.id.linear3);
                    linearLayout1.setVisibility(View.VISIBLE);
                }

            }
        });

        return view;
    }
}
