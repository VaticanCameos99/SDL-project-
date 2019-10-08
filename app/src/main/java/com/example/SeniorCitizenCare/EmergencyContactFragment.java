package com.example.SeniorCitizenCare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EmergencyContactFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ContactClass> list;
    MyAdapterContactClass adapter;

    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_emergency_contacts, container, false);

        fab = v.findViewById(R.id.fab);

        recyclerView = v.findViewById(R.id.emergencyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();

        list.add(new ContactClass((R.drawable.ic_person), "John Doe", "999", "Son"));
        list.add(new ContactClass((R.drawable.ic_person), "Jane Doe", "123", "Daughter"));

        adapter = new MyAdapterContactClass(list);
        recyclerView.setAdapter(adapter);


        adapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListContacts.class);
                startActivityForResult(intent,1);
            }
        });

//        return inflater.inflate(R.layout.fragment_emergency_contacts, container, false);
        return  v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                int contactImage = data.getIntExtra("contactImage",0);
                String contactName = data.getStringExtra("contactName");
                String contactNumber = data.getStringExtra("contactNumber");
                String contactRelation = data.getStringExtra("contactRelation");

                list.add(new ContactClass(contactImage,contactName,contactNumber,contactRelation));
                adapter.notifyDataSetChanged();

            }
            else if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(getContext(), "Please Select a Contact", Toast.LENGTH_LONG).show();
            }
        }
    }
}
