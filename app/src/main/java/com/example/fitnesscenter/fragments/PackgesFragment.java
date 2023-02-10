package com.example.fitnesscenter.fragments;

import static com.example.fitnesscenter.Constant.getUserLoginStatus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnesscenter.MainActivity;
import com.example.fitnesscenter.Model.Package;
import com.example.fitnesscenter.R;
import com.example.fitnesscenter.Screen.AdminHomeActivity;
import com.example.fitnesscenter.Screen.BookingActivity;
import com.example.fitnesscenter.Screen.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PackgesFragment extends Fragment {
    GridView gridView;
    CustomAdapter customAdapter;
    DatabaseReference dRef;
    ProgressBar progressBar;
    ArrayList<Package> packageArrayList=new ArrayList<Package>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_packges, container, false);
        gridView=view.findViewById(R.id.gridview);
        progressBar=view.findViewById(R.id.pg);
        dRef=  FirebaseDatabase.getInstance().getReference("Packages");
        getData();
        return view;
    }
    public void getData(){
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
                Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public class CustomAdapter extends BaseAdapter {

        LayoutInflater inflter;
        public CustomAdapter() {
            inflter = (LayoutInflater.from(getContext()));
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
            CardView cardview= view.findViewById(R.id.cardview);
            item_name.setText(packageArrayList.get(i).getName());
            item_time.setText(packageArrayList.get(i).getStarting_time());
            TextView item_price=view.findViewById(R.id.item_price);;
            item_price.setText(packageArrayList.get(i).getPrice());
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getUserLoginStatus(getContext())){
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setMessage("Are you sure ");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        startActivity(new Intent(getContext(), BookingActivity.class));
                                    }
                                });
                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                    else {
                        Toast.makeText(getContext(),"you must login first",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                }
            });
            return view;
        }
    }

}