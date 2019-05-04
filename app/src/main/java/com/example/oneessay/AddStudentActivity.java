package com.example.oneessay;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class AddStudentActivity extends AppCompatActivity {
    Button btnAdd;
    EditText editName;
    EditText editEmail;
    EditText editID;
    EditText editClass;
    EditText editProfessor;
    private FirebaseAuth firebaseAuth;
    DatabaseReference addUserRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        editName=(EditText) findViewById(R.id.editName);
        editEmail=(EditText) findViewById(R.id.editEmail);
        editID=(EditText)findViewById(R.id.editID);
        editClass=(EditText)findViewById(R.id.editClass);
        editProfessor=(EditText)findViewById(R.id.editProfessor);
        editProfessor.setText("Dr Elizabeth Diaz");
        editProfessor.setEnabled(false);
        editClass.setText("English");
        editClass.setEnabled(false);
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddStudentActivity.this);

            builder.setTitle("ONE ESSAY");
            builder.setMessage("Are you sure you want to log out?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(AddStudentActivity.this, LoginActivity.class);
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
    public void add(View v){
        String name=editName.getText().toString();
        String studentid=editID.getText().toString();
        String email=editEmail.getText().toString();
        String password="123456";
        String eclass=editClass.getText().toString();
        String professor=editProfessor.getText().toString();

        if(name.equals("")||email.equals("")){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Invalid Credentials",
                    Toast.LENGTH_SHORT);

            toast.show();
        }
        else{
            firebaseAuth.createUserWithEmailAndPassword(editEmail.getText().toString(), password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isComplete()) {

                        addUserRef = LoginActivity.mRootRef.child("student").child(editEmail.getText().toString().replace("@", "").replace(".", ""));

                        addUserRef.child("email").setValue(editEmail.getText().toString());
                        addUserRef.child("name").setValue(editName.getText().toString());
                        addUserRef.child("professor").setValue(editProfessor.getText().toString());
                        addUserRef.child("studentid").setValue(editID.getText().toString());

                        Toast.makeText(getApplicationContext(), " Student Added" + editEmail.getText().toString(), Toast.LENGTH_SHORT).show();


                        Intent in = new Intent(AddStudentActivity.this, ProfessorHomePageActivity.class);
                        startActivity(in);

                    } else {
                        Toast.makeText(AddStudentActivity.this, "Could not Register Succesfully", Toast.LENGTH_SHORT).show();
                    }
                }


            });
        }
    }
}
