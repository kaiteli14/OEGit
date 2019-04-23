package com.example.oneessay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    DatabaseReference newUserRef;

    private EditText name, password, confirmpassword, email, studentid;
    private Spinner professor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        professor = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(getApplicationContext(),R.array.Professor,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        professor.setAdapter(arrayAdapter);
        professor.setOnItemSelectedListener(this);
        professor.setSelection(0);

    }

    public void onClickRegister(View view) {

        name = (EditText) findViewById(R.id.Name);
        String uName = name.getText().toString();

        email = (EditText) findViewById(R.id.Email);
        String emailid = email.getText().toString();

        password = (EditText) findViewById(R.id.Password);
        String pass = password.getText().toString();

        confirmpassword = (EditText) findViewById(R.id.ConfirmPassword);
        String confirmPass = confirmpassword.getText().toString();

        studentid = (EditText) findViewById(R.id.StudentID);
        String stuID = studentid.getText().toString();

        Boolean result = validateEmptyFields(uName, emailid, pass, confirmPass, stuID);

        if (result) {

                if (pass.length() > 5) {

                    if (password.getText().toString().equals(confirmpassword.getText().toString())) {

                        progressDialog.setMessage("You are Registering .....");
                        progressDialog.show();

                        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isComplete()) {

                                    newUserRef = LoginActivity.mRootRef.child("student").child(email.getText().toString().replace("@", "").replace(".", ""));

                                    newUserRef.child("email").setValue(email.getText().toString());
                                    newUserRef.child("name").setValue(name.getText().toString());
                                    newUserRef.child("professor").setValue(professor.getSelectedItem().toString());
                                    newUserRef.child("studentid").setValue(studentid.getText().toString());

                                    Toast.makeText(getApplicationContext(), "Registered Succesfully " + email.getText().toString(), Toast.LENGTH_SHORT).show();

                                    progressDialog.dismiss();

                                    Intent in = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(in);

                                } else {
                                    Toast.makeText(SignUpActivity.this, "Could not Register Succesfully", Toast.LENGTH_SHORT).show();
                                }
                            }


                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords do not match !!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Passwords should be atleast 6 characters !!", Toast.LENGTH_LONG).show();
                }

        }
        else
        {
            Toast.makeText(this,"The Fields cannot be empty",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private boolean validateEmptyFields(String uName, String emailid, String user, String password, String sid) {
        Boolean res = false;
        if(!(uName.isEmpty() || emailid.isEmpty() || user.isEmpty() || password.isEmpty() || sid.isEmpty()))
        {
            //Toast.makeText(this,"The User Name and Password Fields cannot be empty",Toast.LENGTH_LONG).show();
            res = true;

        }

        return res;
    }
}