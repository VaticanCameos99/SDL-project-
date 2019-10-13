package com.example.SeniorCitizenCare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListContacts extends AppCompatActivity implements ContactRelationDialog.DialogListener {

    public RecyclerView recyclerView;
    public EditText editText;

//    public ArrayList<ContactClass> list,temp,listFull;
    public ArrayList<ContactClassSmall> mList,mTemp,mListFull;

//    public MyAdapterContactClass adapter;
    public MyAdapterContactSmallClass adapterContactSmallClass;
    FloatingActionButton fab;
    int currPos=-1;
    int val=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_emergency_contacts);
        fab = findViewById(R.id.fab);
        fab.hide();

        populateList();
        buildRecyclerView();
//        addSearch();
    }

    @Override
    public void onClick(String relation) {
        Log.i("Relation", relation + " established");
        Log.i("Pos", Integer.toString(currPos));
        Intent resultIntent = new Intent();
//        resultIntent.putExtra("contactImage", list.get(currPos).mImageResource);
//        resultIntent.putExtra("contactName", list.get(currPos).mName);
//        resultIntent.putExtra("contactNumber", list.get(currPos).mNumber);


        resultIntent.putExtra("contactImage", mList.get(currPos).mImageResource);
        resultIntent.putExtra("contactName", mList.get(currPos).mName);
        resultIntent.putExtra("contactNumber", mList.get(currPos).mNumber);

        resultIntent.putExtra("contactRelation", relation);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

//    public ArrayList<ContactClass> getContacts(){
    public ArrayList<ContactClassSmall> getContacts(){

            ArrayList<ContactClass> myContacts = new ArrayList<>();
        ArrayList<ContactClassSmall> mContacts = new ArrayList<>();

        Cursor cursor = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null
                ,null,null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
        cursor.moveToFirst();

        while(cursor.moveToNext()){

//            myContacts.add(new ContactClass(0,
//                    cursor.getString(cursor.getColumnIndex((ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))),
//                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)), null));


            mContacts.add(new ContactClassSmall(0,
                    cursor.getString(cursor.getColumnIndex((ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))),
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));

        }
        cursor.close();

//        return myContacts;
        return mContacts;
    }

    public void populateList(){
//        list = new ArrayList<>();
//        listFull = new ArrayList<>();

        mList = new ArrayList<>();
        mListFull= new ArrayList<>();

//        temp = getContacts();
//
//        for(int j=0; j<temp.size(); j++){
//            list.add(new ContactClass((R.drawable.ic_person), temp.get(j).mName, temp.get(j).mNumber,null));
//        }

        mTemp = getContacts();

        for(int j=0; j<mTemp.size(); j++){
            mList.add(new ContactClassSmall((R.drawable.ic_person), mTemp.get(j).mName, mTemp.get(j).mNumber));
        }

//        listFull = new ArrayList<>(list);
        mListFull = new ArrayList<>(mList);
    }

    public void buildRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.emergencyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        adapter = new MyAdapterContactClass(list);
        adapterContactSmallClass = new MyAdapterContactSmallClass(mList);

        recyclerView.setAdapter(adapterContactSmallClass);

        //TODO
//        adapter.setOnItemClickListener(new MyAdapterContactClass.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                currPos = position;
//
//                ContactRelationDialog dialog = new ContactRelationDialog();
//                dialog.show(getSupportFragmentManager(), "dialog");
//            }
//        });

        adapterContactSmallClass.setOnItemClickListener(new MyAdapterContactSmallClass.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                currPos = position;

                ContactRelationDialog dialog = new ContactRelationDialog();
                dialog.show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    //Function for Searching
    public void addSearch() {
        editText = findViewById(R.id.mySearch);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                //Check for Backspace
//                if(val-1 == editable.toString().length())
//                    list = new ArrayList<>(listFull);
//                filter(editable.toString());

                if(val-1 == editable.toString().length())
                    mList = new ArrayList<>(mListFull);
                filter(editable.toString());

                val = editable.toString().length();
            }
        });
    }

    //Search Filter
    public void filter(String text){
        ArrayList<ContactClassSmall>filteredList = new ArrayList<>();

        //Refresh List
//        if(text == null || text.length()==0){
//            list = new ArrayList<>(listFull);
//            filteredList.addAll(list);
//        }
//        for(ContactClass item : list){
//            if(item.getmName().toLowerCase().contains(text.toLowerCase())){
//                filteredList.add(item);
//            }
//        }
//
        if(text == null || text.length()==0){
            mList = new ArrayList<>(mListFull);
            filteredList.addAll(mListFull);
        }
        for(ContactClassSmall item : mList){
            if(item.getmName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }


//        adapter.filterList(filteredList);
//        list = new ArrayList<>(filteredList);

        adapterContactSmallClass.filterList(filteredList);
        mList = new ArrayList<>(filteredList);
    }

}
