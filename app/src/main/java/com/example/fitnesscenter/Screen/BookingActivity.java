package com.example.fitnesscenter.Screen;

import static com.example.fitnesscenter.Constant.getUsername;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fitnesscenter.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {
    DatabaseReference dRef;
    ProgressBar progressBar;
    EditText setdate,start_time,end_time;
    AlertDialog.Builder builder;
    final Calendar myCalendar= Calendar.getInstance();
    DatePickerDialog datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        progressBar =findViewById(R.id.pg);
        setdate =findViewById(R.id.date);
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                String myFormat="MM/dd";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                setdate.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker =  new DatePickerDialog(BookingActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePicker.show();
            }
        });



        start_time =findViewById(R.id.start_time);
        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedMinute==0){
                            start_time.setText( selectedHour + ":" + "00");
                        }
                        else {
                            start_time.setText( selectedHour + ":" + selectedMinute);
                        }

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        end_time =findViewById(R.id.end_time);
        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedMinute==0){
                            end_time.setText( selectedHour + ":" + "00");
                        }
                        else {
                            end_time.setText( selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        builder = new AlertDialog.Builder(this);
        builder.setMessage("The maximum time that can be selected by a user is one hour")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.setCancelable(false);
        AlertDialog alert = builder.create();
        alert.setTitle("Booking alert");
        alert.show();
    }



    public void add(){
        dRef=  FirebaseDatabase.getInstance().getReference("Booking Packages").child(getUsername(BookingActivity.this));
        dRef.child("Name").setValue(getUsername(BookingActivity.this));
        dRef.child("Date").setValue(setdate.getText().toString());
        dRef.child("StartTime").setValue(start_time.getText().toString());
        dRef.child("EndTime").setValue(end_time.getText().toString());
        dRef.child("Status").setValue("Disapprove");
        Toast.makeText(this,"your booking is submitted",Toast.LENGTH_LONG).show();
    }

    public void submitBooking(View view) {
        if(setdate.getText().toString().trim().isEmpty()){
            setdate.setError("required");
        }
        else if(start_time.getText().toString().trim().isEmpty()){
            start_time.setError("required");
        }  else if(end_time.getText().toString().trim().isEmpty()){
            end_time.setError("required");
        }
        else {
             ShowMessageDialog(this);
        }

    }
    public  void ShowMessageDialog(Context context ){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Are you sure");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        add();
                    }
                });
        builder1.setNegativeButton(
                "no",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void back(View view) {
        finish();
    }
}