package com.example.fitnesscenter.fragments;

import static com.example.fitnesscenter.Constant.getUsername;
import static com.example.fitnesscenter.Constant.setUserLoginStatus;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fitnesscenter.MainActivity;
import com.example.fitnesscenter.R;
import com.example.fitnesscenter.Screen.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ContactFragment extends Fragment {
        Button submit;
                   EditText user_email,user_des;
                   ProgressBar progressBar;
    DatabaseReference myRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        submit=view.findViewById(R.id.submit);
        user_des=view.findViewById(R.id.user_des);
        user_email=view.findViewById(R.id.user_email);
        progressBar=view.findViewById(R.id.pg);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_email.getEditableText().toString().trim().isEmpty()){
                    user_email.setError("required");
                }
                else if(user_des.getEditableText().toString().trim().isEmpty()){
                    user_des.setError("required");
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                              add();
                }
            }
        });
        return view;
    }
    private void add(){
        myRef=  FirebaseDatabase.getInstance().getReference("user").child(getUsername(getContext()));
        myRef.child("Name").setValue(getUsername(getContext()));
        myRef.child("Mail").setValue(user_email.getText().toString());
        myRef.child("Description").setValue(user_des.getText().toString());
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(),"Thanks for contacting us",Toast.LENGTH_LONG).show();
    }
}