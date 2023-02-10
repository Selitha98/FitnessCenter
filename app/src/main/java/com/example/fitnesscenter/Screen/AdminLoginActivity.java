package com.example.fitnesscenter.Screen;

import static com.example.fitnesscenter.Constant.setUserLoginStatus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnesscenter.MainActivity;
import com.example.fitnesscenter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class AdminLoginActivity extends AppCompatActivity {
    EditText admin_password,admin_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        admin_email=findViewById(R.id.admin_email);
        admin_password=findViewById(R.id.admin_password);
    }

    public void loginAdmin(View view) {
        if(admin_password.getEditableText().toString().trim().isEmpty()){
            admin_password.setError("Please fill out the username");
        }
        else if(admin_email.getEditableText().toString().trim().isEmpty()){
            admin_email.setError("Please fill out the username");
        }
        else {
               if(admin_email.getText().toString().trim().equals("admin@gmail.com") && admin_password.getText().toString().trim().equals("123456")){
                     startActivity(new Intent(AdminLoginActivity.this,AdminHomeActivity.class));
               }
               else {
                   Toast.makeText(AdminLoginActivity.this,"Incorrect email or password",Toast.LENGTH_LONG).show();
               }
        }
    }
}