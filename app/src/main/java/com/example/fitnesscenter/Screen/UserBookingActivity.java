package com.example.fitnesscenter.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnesscenter.Model.User;
import com.example.fitnesscenter.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserBookingActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<User> userArrayList =new ArrayList<User>();
    DatabaseReference dRef;
    ArrayAdapter arrayAdapter;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking);
        recyclerView=findViewById(R.id.user_recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dRef=  FirebaseDatabase.getInstance().getReference("Booking Packages");
        progressBar=findViewById(R.id.pg);
        getData();
    }


    public void getData(){
        progressBar.setVisibility(View.VISIBLE);
        userArrayList.clear();
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    userArrayList.add(new User(postSnapshot.child("Name").getValue(String.class),
                            postSnapshot.child("Date").getValue(String.class),postSnapshot.child("StartTime").getValue(String.class),
                            postSnapshot.child("EndTime").getValue(String.class), postSnapshot.child("Status").getValue(String.class)));
                }
                arrayAdapter =new ArrayAdapter();
                recyclerView.setAdapter(arrayAdapter);

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserBookingActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public class ArrayAdapter extends RecyclerView.Adapter<ArrayAdapter.ImageViewHoler> {

        public ArrayAdapter(){
        }
        @NonNull
        @Override
        public ArrayAdapter.ImageViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(UserBookingActivity.this).inflate(R.layout.item_users1,parent,false);
            return  new ArrayAdapter.ImageViewHoler(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final ArrayAdapter.ImageViewHoler holder, @SuppressLint("RecyclerView") int position) {

            holder.user_starttime.setText("StartTime : "+userArrayList.get(position).getStarttime());
            holder.user_endtime.setText("EndTime : "+userArrayList.get(position).getEndtime());
            holder.user_date.setText("Date : "+userArrayList.get(position).getDate());
            holder.user_name.setText("Name : "+userArrayList.get(position).getName());
            holder.user_status.setText("Status : "+userArrayList.get(position).getStatus());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CharSequence[] options = {"Approve", "Disapprove","Cancel"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserBookingActivity.this);
                    builder.setTitle("Select option");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (options[item].equals("Approve")) {
                                dialog.dismiss();
                                dRef.child(userArrayList.get(position).getName()).child("Status").setValue("Approve");
                                getData();

                            } else if (options[item].equals("Disapprove")) {
                                dRef.child(userArrayList.get(position).getName()).child("Status").setValue("Disapprove");
                                getData();
                            }
                                else if (options[item].equals("Cancel")) {
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return userArrayList.size();
        }

        public class ImageViewHoler extends RecyclerView.ViewHolder {

            TextView user_name,user_date,user_starttime,user_endtime,user_status;
            CardView cardView;
            public ImageViewHoler(@NonNull View itemView) {
                super(itemView);
                user_status=itemView.findViewById(R.id.user_status);
                user_name=itemView.findViewById(R.id.user_name);
                user_date=itemView.findViewById(R.id.user_date);
                user_starttime=itemView.findViewById(R.id.user_starttime);
                user_endtime=itemView.findViewById(R.id.user_endtime);
                cardView=itemView.findViewById(R.id.cardView);
            }
        }
    }
}