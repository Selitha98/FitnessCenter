package com.example.fitnesscenter.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnesscenter.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdatePackageActivity extends AppCompatActivity {
          EditText start_time;
    DatabaseReference dRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_package);
        start_time=findViewById(R.id.start_time);
        dRef=  FirebaseDatabase.getInstance().getReference("Packages");
    }

    @Override
    protected void onStart() {
        start_time.setText(getIntent().getStringExtra("StartingTime"));
        super.onStart();
    }

    public void updatePackage(View view) {
        if(start_time.getText().toString().trim().isEmpty()){
            start_time.setError("required");
        }else {
            dRef.child(getIntent().getStringExtra("Name")).child("StartingTime").setValue(start_time.getText().toString());
            Toast.makeText(UpdatePackageActivity.this,"package is updated",Toast.LENGTH_LONG).show();
        }
    }
}