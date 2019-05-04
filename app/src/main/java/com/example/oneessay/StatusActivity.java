package com.example.oneessay;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class StatusActivity extends AppCompatActivity {

    ListView statusListView;

    ArrayList<Student> studentList;

    Student student;

    EssayActivity activity;

    StatusAdapter statusAdapter;

    TextView nonetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_list);

        statusListView = (ListView) findViewById(R.id.statusListView);

        nonetext = (TextView) findViewById(R.id.nonestatus);

        LoginActivity.mRootRef.child("activity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                studentList = new ArrayList<Student>();

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext()) {
                    DataSnapshot s = iterator.next();
                    activity = s.getValue(EssayActivity.class);
                    if (activity.getStatus())
                        break;
                }
                if (activity != null) {
                    studentList = activity.getNextstudents();
                }

                if (studentList.size() > 0) {
                    nonetext.setVisibility(View.GONE);
                    statusListView.setVisibility(View.VISIBLE);
                    statusAdapter = new StatusAdapter(StatusActivity.this, studentList);
                    statusListView.setAdapter(statusAdapter);
                } else {
                    nonetext.setVisibility(View.VISIBLE);
                    statusListView.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(StatusActivity.this);

            builder.setTitle("ONE ESSAY");
            builder.setMessage("Are you sure you want to log out?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(StatusActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }

            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    dialog.dismiss();
                }
            });

            android.app.AlertDialog alert = builder.create();
            alert.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
