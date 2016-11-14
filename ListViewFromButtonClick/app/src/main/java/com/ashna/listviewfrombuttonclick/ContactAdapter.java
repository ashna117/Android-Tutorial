package com.ashna.listviewfrombuttonclick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class ContactAdapter extends ArrayAdapter {

    private Contact contact;
    private TextView nameTV;
    private TextView phoneNOTV;
    private ArrayList<Contact> contactList;
    private Context context;

    public ContactAdapter(Context context, ArrayList<Contact> contactList) {
        super(context, R.layout.row_layout,contactList);
        this.context=context;
        this.contactList=contactList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.row_layout,null);
        nameTV=(TextView)convertView.findViewById(R.id.name);
        phoneNOTV=(TextView)convertView.findViewById(R.id.phoneNo);

        nameTV.setText(contactList.get(position).getName());
        phoneNOTV.setText(contactList.get(position).getPhoneNo());

        return convertView;
    }
}
