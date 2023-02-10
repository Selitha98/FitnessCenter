package com.example.fitnesscenter.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitnesscenter.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPackgeActivity extends AppCompatActivity {
   EditText package_name,start_time,package_price;
   DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_packge);
        package_name=findViewById(R.id.package_name);
        start_time=findViewById(R.id.start_time);
        package_price=findViewById(R.id.package_price);
    }

    public void addPackage(View view) {
        if(package_name.getEditableText().toString().trim().isEmpty()){
            package_name.setError("required");
        }
        else if(start_time.getEditableText().toString().trim().isEmpty()){
            start_time.setError("required");
        }
        else if(package_price.getEditableText().toString().trim().isEmpty()){
            package_price.setError("required");
        }
        else {
           add();
        }

    }
    private void add(){
        databaseReference=  FirebaseDatabase.getInstance().getReference("Packages").child(package_name.getText().toString());
        databaseReference.child("Name").setValue(package_name.getText().toString());
        databaseReference.child("StartingTime").setValue(start_time.getText().toString());
        databaseReference.child("Price").setValue(package_price.getText().toString());
        Toast.makeText(this,"Package added",Toast.LENGTH_LONG).show();
    }
}