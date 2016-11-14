package com.titan.ecotech.smartnotepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Notes extends AppCompatActivity {

    EditText etTitle;
    EditText etDes;
    RadioGroup rgPriority;
    CheckBox doneCB;
    Notepad notepad;
    NoteManager nManager;
    int idS;
    Notepad noteFetch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        etTitle = (EditText) findViewById(R.id.mytitle);
        etDes = (EditText) findViewById(R.id.mydetails);
        rgPriority = (RadioGroup) findViewById(R.id.myradiogroup);
        doneCB = (CheckBox) findViewById(R.id.donecb);

        nManager = new NoteManager(this);

        Intent intentS=getIntent();
        idS = intentS.getIntExtra("idS",-1);
        if(idS>0){
            noteFetch=nManager.getNote(idS);
            etTitle.setText(noteFetch.getTitle());
            etDes.setText(noteFetch.getDetails());
            String priority = noteFetch.getPriority();
            if(priority.equals("Low"))
                rgPriority.check(R.id.rblow);
            else if(priority.equals("Medium"))
                rgPriority.check(R.id.rbmedium);
            else if (priority.equals("High"))
                rgPriority.check(R.id.rbhigh);
            String tasksts = noteFetch.getTaskdone();
            if(tasksts.equals("Done"))
                doneCB.setChecked(true);
            else
                doneCB.setChecked(false);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            startActivity(new Intent(Notes.this, MainActivity.class));

            return true;
        } else if (id == R.id.save) {

            if(idS>0)
            {
                updateRecord();
            }
            else {

                String priority = ((RadioButton) this.findViewById(rgPriority.getCheckedRadioButtonId())).getText().toString();

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                String time = sdf.format(cal.getTime());
                String status;
                if(doneCB.isChecked())
                    status = "Done";
                else
                    status = "Not Done";


                notepad = new Notepad(etTitle.getText().toString(),
                        etDes.getText().toString(), priority, time, status);

                Log.d("time",time);

                boolean inserted = nManager.addNote(notepad);
                if (inserted) {
                    Toast.makeText(Notes.this, "Record Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Notes.this, "Record is not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
            etTitle.setText("");
            etDes.setText("");
            rgPriority.clearCheck();

            Intent i = new Intent(Notes.this,MainActivity.class);
            startActivity(i);

            return true;
        } else if (id == R.id.delete) {

            boolean deleted =nManager.deleteNote(idS);
            if (deleted) {
                Toast.makeText(Notes.this, "Record Deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Notes.this, "Record is not Deleted", Toast.LENGTH_SHORT).show();
            }
            etTitle.setText("");
            etDes.setText("");
            rgPriority.clearCheck();

            Intent i = new Intent(Notes.this,MainActivity.class);
            startActivity(i);

        }

        return true;

    }

    public void updateRecord(){

        String priority = ((RadioButton) this.findViewById(rgPriority.getCheckedRadioButtonId())).getText().toString();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String time = sdf.format(cal.getTime());
        String status;
        if(doneCB.isChecked())
            status = "Done";
        else
            status = "Not Done";

        notepad = new Notepad(etTitle.getText().toString(),
                etDes.getText().toString(), priority, time, status);

        boolean updated = nManager.updateNote(idS,notepad);
        if (updated) {
            Toast.makeText(Notes.this, "Record Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Notes.this, "Record is not Updated", Toast.LENGTH_SHORT).show();
        }

    }
}
