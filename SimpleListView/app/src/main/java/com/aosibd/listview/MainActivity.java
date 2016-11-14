package com.aosibd.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listitem;
    ArrayList<String> name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listitem = (ListView) findViewById(R.id.simpleList);
        name = new ArrayList<>();
        name.add("Humaon Ahmed");
        name.add("Jafor Iqbql");
        name.add("Sottojit Roy");
        name.add("Kabi Guru");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, name);
        listitem.setAdapter(stringArrayAdapter);
        listitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data=name.get(position).toString();
                Toast.makeText(getBaseContext(),data,Toast.LENGTH_SHORT).show();
            }
        });



    }

}
