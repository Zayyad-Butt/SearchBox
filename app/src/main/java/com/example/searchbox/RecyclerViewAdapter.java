package com.example.searchbox;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


class RecyclerViewAdapter extends RecyclerView.Adapter<com.example.searchbox.RecyclerViewAdapter.MyViewHolder> {

    List<Student> studentList;
    Activity mAct;
    Context context;

    public RecyclerViewAdapter(List<Student> studentList, Activity mAct,Context cont) {
        this.studentList = studentList;
        this.mAct = mAct;
        this.context=cont;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_items, parent, false);
        return new MyViewHolder(itemView);
    }

    public void setImg(){
        	}


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.data=studentList.get(position);
        holder.name.setText(holder.data.getName());
        holder.age.setText(String.valueOf(holder.data.getAge()));
        holder.cnic.setText(String.valueOf(holder.data.getCNIC()));
        holder.rollNo.setText(String.valueOf(holder.data.getRollNo()));

        if(holder.data.getImg()!=null)
        {
            String uri = "@drawable/"+holder.data.getImg();  // where myresource (without the extension) is the file

            int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
            Drawable res = context.getResources().getDrawable(imageResource);
            holder.imageViewFriend.setImageDrawable(res);
        }


        //Glide.with(mAct).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.imageViewFriend);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewFriend;
        TextView name;
        TextView age;
        TextView rollNo;
        TextView cnic;
        Student data;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewFriend = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            cnic = itemView.findViewById(R.id.cnic);
            rollNo = itemView.findViewById(R.id.rollNo);
        }
    }
}
