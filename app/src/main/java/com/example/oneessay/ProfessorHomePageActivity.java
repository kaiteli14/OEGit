package com.example.oneessay;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProfessorHomePageActivity extends AppCompatActivity {

    ListView studentListView;
    ListView activityListView;

    DatabaseReference studentsRef;

    Student student;
    List<String> studentList;

    EssayActivity activity;
    List<String> activityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_home_page);


        Button button = findViewById(R.id.phpbackbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfessorHomePageActivity.this, LoginActivity.class);
                //intent.putExtra();
                startActivity(intent);
            }
        });


        studentListView = (ListView) findViewById(R.id.studentlist);
        //new Jolly(ProfessorHomePageActivity.this).execute(10);
        studentList = new ArrayList<String>();

        LoginActivity.mRootRef.child("student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                studentList.clear();

                while (iterator.hasNext()) {
                    DataSnapshot s = iterator.next();
                    student = s.getValue(Student.class);
                    studentList.add(student.getName());
                }

                ArrayAdapter adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, studentList);
                if (studentList.size() > 0) {
                    studentListView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        activityListView = (ListView) findViewById(R.id.activitylist);

        activityList = new ArrayList<String>();


        LoginActivity.mRootRef.child("activity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                activityList.clear();

                while (iterator.hasNext()) {
                    DataSnapshot s = iterator.next();
                    activity = s.getValue(EssayActivity.class);
                    if (activity.getStatus())
                        activityList.add(activity.getEssaytopic());
                }

                ArrayAdapter adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, activityList);
                if (activityList.size() > 0) {
                    activityListView.setAdapter(adapter);
                }


                activityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getApplicationContext(), "You have clicked the topic", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
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

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ProfessorHomePageActivity.this);

            builder.setTitle("ONE ESSAY");
            builder.setMessage("Are you sure you want to log out?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(ProfessorHomePageActivity.this, LoginActivity.class);
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
    public void studAdd(View v) {
        Intent intent = new Intent(this, AddStudentActivity.class);
        startActivity(intent);
    }

    public void onClickTopic(View view) {
        Intent intent = new Intent(this, DisplayTopicActivity.class);
        startActivity(intent);
    }
}
