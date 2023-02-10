package com.example.fitnesscenter.Screen;

import static com.example.fitnesscenter.Constant.setUserLoginStatus;
import static com.example.fitnesscenter.Constant.setUsername;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText user_password, user_wic,user_email,user_name;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        user_email=findViewById(R.id.user_email);
        user_password=findViewById(R.id.user_password);
        user_wic=findViewById(R.id.user_wic);
        user_name=findViewById(R.id.user_name);
        progressBar=findViewById(R.id.pg);
        firebaseAuth= FirebaseAuth.getInstance();
    }

    public void signupUser(View view) {
        if(user_email.getText().toString().trim().isEmpty()){
            user_email.setError("required");
        }
        else if(user_password.getText().toString().trim().isEmpty()){
            user_password.setError("required");
        }  else if(user_name.getText().toString().trim().isEmpty()){
            user_name.setError("required");
        }
        else if(user_wic.getText().toString().trim().isEmpty()){
            user_wic.setError("required");
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(user_email.getText().toString(),user_password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        setUserLoginStatus(SignUpActivity.this,true);
                        setUsername(SignUpActivity.this,user_name.getText().toString());
                        add();
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        progressBar.setVisibility(View.GONE);
                        finish();

                    }
                    else {
                        Toast.makeText(SignUpActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    private void add(){
        myRef=  FirebaseDatabase.getInstance().getReference("user").child(user_name.getText().toString());
        myRef.child("Name").setValue(user_name.getText().toString());
        myRef.child("Mail").setValue(user_email.getText().toString());
        myRef.child("WIC").setValue(user_wic.getText().toString());
        myRef.child("Status").setValue("Active");
    }
    public void moveToLoginScreen(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }
}