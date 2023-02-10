package com.example.fitnesscenter.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnesscenter.Model.Package;
import com.example.fitnesscenter.R;
import com.example.fitnesscenter.fragments.PackgesFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {
    GridView gridView;
    CustomAdapter customAdapter;
    DatabaseReference dRef;
    ProgressBar progressBar;
    ArrayList<Package> packageArrayList=new ArrayList<Package>();
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        gridView=findViewById(R.id.gridview);
        progressBar=findViewById(R.id.pg);
        dRef=  FirebaseDatabase.getInstance().getReference("Packages");
        builder = new AlertDialog.Builder(this);
    }

    @Override
    protected void onStart() {

        getData();
        super.onStart();
    }

    public void getData(){
        progressBar.setVisibility(View.VISIBLE);
        packageArrayList.clear();
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    packageArrayList.add(new Package(postSnapshot.child("Name").getValue(String.class),
                            postSnapshot.child("StartingTime").getValue(String.class)
                            ,postSnapshot.child("Price").getValue(String.class)));
                }
                customAdapter = new CustomAdapter();
                gridView.setAdapter(customAdapter);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AdminHomeActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addPackge(View view) {
        startActivity(new Intent(this,AddPackgeActivity.class));
    }

    public void openUserScreen(View view) {
                      startActivity(new Intent(this,UserListActivity.class));
    }
    public void openUserBookingScreen(View view) {
        startActivity(new Intent(this,UserBookingActivity.class));
    }

    public class CustomAdapter extends BaseAdapter {

        LayoutInflater inflter;
        public CustomAdapter() {
            inflter = (LayoutInflater.from(AdminHomeActivity.this));
        }
        @Override
        public int getCount() {
            return packageArrayList.size();
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.grid_view_items, null); // inflate the layout

            TextView item_name=view.findViewById(R.id.item_name);
            TextView item_time=view.findViewById(R.id.item_time);
            TextView item_price=view.findViewById(R.id.item_price);
            CardView cardview= view.findViewById(R.id.cardview);
            item_name.setText(packageArrayList.get(i).getName());
            item_time.setText(packageArrayList.get(i).getStarting_time());
            item_price.setText(packageArrayList.get(i).getPrice());
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                       startActivity(new Intent(AdminHomeActivity.this,UpdatePackageActivity.class)
                               .putExtra("startTime",packageArrayList.get(i).getStarting_time())
                       .putExtra("name",packageArrayList.get(i).getName()));
                }
            });
            cardview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    builder.setMessage("Are you sure")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dRef.child(packageArrayList.get(i).getName()).removeValue();
                                    getData();
                                }
                            });
                    builder.setCancelable(true);
                    AlertDialog alert = builder.create();
                    alert.setTitle("Delete Package");
                    alert.show();
                    return false;
                }
            });
            return view;
        }
    }
}