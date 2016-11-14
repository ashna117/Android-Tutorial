package com.titan.ecotech.smartnotepad;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Ashna on 7/21/2016.
 */



public class DataAdapter extends RecyclerView.Adapter<DataAdapter.NoteViewHolder> {

    private ArrayList<Notepad> notepad;
    private Context context;
    int id;


    public DataAdapter(ArrayList<Notepad> notepad, Context context) {
        this.notepad = notepad;
        this.context=context;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView title;
        private TextView taskstatus;
        private TextView priority;
        private TextView time;
        private TextView icon_entry;

        public NoteViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv);
            title=(TextView)itemView.findViewById(R.id.notetitle);
            //taskstatus=(TextView)itemView.findViewById(R.id.status);
            priority=(TextView)itemView.findViewById(R.id.priority);
            time=(TextView)itemView.findViewById(R.id.time);
            icon_entry=(TextView)itemView.findViewById(R.id.icon_entry);
        }
    }

    @Override
    public void onBindViewHolder(NoteViewHolder noteViewHolder, int i) {
        noteViewHolder.title.setText(notepad.get(i).getTitle());
        noteViewHolder.priority.setText(notepad.get(i).getPriority());
        //noteViewHolder.taskstatus.setText(notepad.get(i).getTaskdone());
        noteViewHolder.time.setText(notepad.get(i).getTime());
        noteViewHolder.icon_entry.setText(notepad.get(i).getTitle().toString().charAt(0));

        final int pos=i;

        noteViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = notepad.get(pos).getId();
                Intent intentS=new Intent(context,Notes.class);
                intentS.putExtra("idS",id);
                context.startActivity(intentS);


                Toast.makeText(context,"clicked "+pos+" row", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);
        NoteViewHolder noteViewHolder = new NoteViewHolder(view);
        return noteViewHolder;
    }

    @Override
    public int getItemCount() {
        return notepad.size();
    }
}


