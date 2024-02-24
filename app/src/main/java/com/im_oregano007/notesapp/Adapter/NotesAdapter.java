package com.im_oregano007.notesapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.im_oregano007.notesapp.AddNewNote;
import com.im_oregano007.notesapp.MainActivity;
import com.im_oregano007.notesapp.Model.NoteModel;
import com.im_oregano007.notesapp.R;
import com.im_oregano007.notesapp.Utils.DatabaseHelper;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private List<NoteModel> modelList;
    private FragmentActivity mainActivity;
    private DatabaseHelper myDb;

    public NotesAdapter(FragmentActivity activity, DatabaseHelper myDb){
        this.mainActivity = activity;
        this.myDb = myDb;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_recycler_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final NoteModel item = modelList.get(position);
        holder.titleText.setText(item.getTitle());
        holder.contentText.setText(item.getContent());

    }

    public Context getContext(){
        return mainActivity;
    }

    public void setNote(List<NoteModel> mList){
        this.modelList = mList;
        notifyDataSetChanged();
    }

    public void deleteNote(int position){
        NoteModel item = modelList.get(position);
        myDb.deleteNote(item.getId());
        modelList.remove(position);
        notifyItemRemoved(position);

    }

    public void editNote(int position){
        NoteModel item = modelList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("title", item.getTitle());
        bundle.putString("content",item.getContent());

        AddNewNote task = new AddNewNote();
        task.setArguments(bundle);
        task.show(mainActivity.getSupportFragmentManager(), task.getTag());

    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleText, contentText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            contentText = itemView.findViewById(R.id.contentText);
        }
    }
}
