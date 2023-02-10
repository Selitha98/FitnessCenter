package com.example.fitnesscenter.fragments;

import android.os.Bundle;
import static com.example.fitnesscenter.Constant.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnesscenter.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AccountFragment extends Fragment {

                 TextView name,email,wic;
                 Button back_btn;
    DatabaseReference dRef;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        wic=view.findViewById(R.id.wic);
        progressBar=view.findViewById(R.id.pg);
        progressBar.setVisibility(View.VISIBLE);
        dRef=  FirebaseDatabase.getInstance().getReference("user").child(getUsername(getContext()));
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText("Name : "+snapshot.child("Name").getValue(String.class));
                email.setText("Mail : "+snapshot.child("Mail").getValue(String.class));
                wic.setText("WIC : "+snapshot.child("WIC").getValue(String.class));
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(getContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}