package com.im_oregano007.notesapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.im_oregano007.notesapp.Adapter.NotesAdapter;
import com.im_oregano007.notesapp.Model.NoteModel;
import com.im_oregano007.notesapp.Utils.DatabaseHelper;
import com.im_oregano007.notesapp.Utils.RecyclerViewTouchHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotesFragment extends Fragment implements onDialogCloseListner{

    RecyclerView recyclerView;
    FloatingActionButton addNewNoteBtn;
    List<NoteModel> modelList;
    NotesAdapter adapter;
    DatabaseHelper myDb;


    public NotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        recyclerView = view.findViewById(R.id.recyclerV);
        addNewNoteBtn = view.findViewById(R.id.addNewNote);
        modelList = new ArrayList<>();
        myDb = new DatabaseHelper(getContext());
        modelList = myDb.getAllNotes();
        adapter = new NotesAdapter(getActivity(),myDb);
        adapter.setNote(modelList);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        addNewNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewNote.newInstance().show(getActivity().getSupportFragmentManager(), AddNewNote.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return view;
    }
    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        modelList = myDb.getAllNotes();
        adapter.setNote(modelList);
        adapter.notifyDataSetChanged();
    }


}