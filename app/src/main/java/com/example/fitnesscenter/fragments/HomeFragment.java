package com.example.fitnesscenter.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitnesscenter.Model.Image;
import com.example.fitnesscenter.Model.User;
import com.example.fitnesscenter.R;
import com.example.fitnesscenter.Screen.UserListActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Image> imageArrayList =new ArrayList<Image>();
   ArrayAdapter arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.image_recyler);
        addImages();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
           arrayAdapter=new ArrayAdapter();
           recyclerView.setAdapter(arrayAdapter);
        return view;
    }
    public void addImages(){
        imageArrayList.add(new Image(R.drawable.image1));
        imageArrayList.add(new Image(R.drawable.image4));
        imageArrayList.add(new Image(R.drawable.image3));
        imageArrayList.add(new Image(R.drawable.image5));
        imageArrayList.add(new Image(R.drawable.image2));
        imageArrayList.add(new Image(R.drawable.image6));


    }
    public class ArrayAdapter extends RecyclerView.Adapter<ArrayAdapter.ImageViewHoler> {

        public ArrayAdapter(){
        }
        @NonNull
        @Override
        public ArrayAdapter.ImageViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(getContext()).inflate(R.layout.item_image,parent,false);
            return  new ArrayAdapter.ImageViewHoler(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final ArrayAdapter.ImageViewHoler holder, @SuppressLint("RecyclerView") int position) {

                      holder.imageView.setImageResource(imageArrayList.get(position).getImage());

        }

        @Override
        public int getItemCount() {
            return imageArrayList.size();
        }

        public class ImageViewHoler extends RecyclerView.ViewHolder {

            ImageView imageView;
            public ImageViewHoler(@NonNull View itemView) {
                super(itemView);
                imageView=itemView.findViewById(R.id.home_image);
            }
        }
    }
}