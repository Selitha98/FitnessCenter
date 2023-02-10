package com.example.fitnesscenter.Screen;

import static com.example.fitnesscenter.Constant.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fitnesscenter.MainActivity;
import com.example.fitnesscenter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText user_password,user_email;
    FirebaseAuth firebaseAuth;
    DatabaseReference myRef;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user_email=findViewById(R.id.user_email);
        user_password=findViewById(R.id.user_password);
        progressBar=findViewById(R.id.pg);
        firebaseAuth= FirebaseAuth.getInstance();

    }

    public void loginUser(View view) {
        //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(user_email.getEditableText().toString().trim().isEmpty()){
            user_email.setError("Please fill out the user email");
        }
        else if(user_password.getEditableText().toString().trim().isEmpty()){
            user_password.setError("Please fill out the user email");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(user_email.getEditableText().toString().trim()).matches()){
            user_email.setError("Please fill out the user email");
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(user_email.getText().toString().trim(), user_password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Incorrect username or password" + task.getException(), Toast.LENGTH_LONG).show();
                    } else if (task.isSuccessful()) {
                        firebaseAuth.getCurrentUser();
                        getData();
                        setUserLoginStatus(LoginActivity.this, true);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        progressBar.setVisibility(View.GONE);
                        finish();
                    }

                }
            });
        }

    }
    private void getData(){
        final String user_m=user_email.getText().toString().trim();
        myRef=  FirebaseDatabase.getInstance().getReference().child("user");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(user_m.equals(dataSnapshot1.child("Mail").getValue(String.class))) {
                        setUsername(LoginActivity.this,dataSnapshot1.child("Name").getValue(String.class));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void moveToSignUpScreen(View view) {
        startActivity(new Intent(this,SignUpActivity.class));
    }
}