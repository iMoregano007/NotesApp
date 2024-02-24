package com.im_oregano007.notesapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.im_oregano007.notesapp.Model.NoteModel;
import com.im_oregano007.notesapp.Utils.DatabaseHelper;

public class AddNewNote extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewNote";
    EditText titleEditText, contentEditText;
    Button saveBtn;
    DatabaseHelper mydb;

    public static AddNewNote newInstance(){
        return new AddNewNote();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_new_note,container,false);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contentEditText = view.findViewById(R.id.contentEditText);
        titleEditText = view.findViewById(R.id.titleEditText);
        saveBtn = view.findViewById(R.id.saveBtn);

        mydb = new DatabaseHelper(getActivity());

        boolean isUpdate = false;
        Bundle bundle = getArguments();

        if(bundle != null){
            isUpdate = true;
            String title = bundle.getString("title");
            String content = bundle.getString("content");

            contentEditText.setText(content);
            titleEditText.setText(title);

            if(title.length()>0 && content.length()>0){
                saveBtn.setEnabled(false);
            }


        }

        saveBtn.setEnabled(false);

        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    saveBtn.setEnabled(false);
                } else {
                    saveBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    saveBtn.setEnabled(false);
                } else {
                    saveBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        boolean finalIsUpdate = isUpdate;

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();
                if(finalIsUpdate){
                    mydb.updateNote(bundle.getInt("id"),title,content);
                } else{
                    NoteModel note = new NoteModel();
                    note.setTitle(title);
                    note.setContent(content);
                    mydb.insertNote(note);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof onDialogCloseListner){
            ((onDialogCloseListner)activity).onDialogClose(dialog);
        }
    }
}
