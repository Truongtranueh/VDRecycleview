package com.example.vdrecycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder>
{
    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model)
    {
//       holder.name.setText(model.getName());
//       holder.course.setText(model.getCourse());
//       holder.email.setText(model.getEmail());
//       Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);
        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.price.setText(model.getPrice());
        Glide.with(holder.img.getContext()).load(model.getUrl()).into(holder.img);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
       return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
//        CircleImageView img;
//        TextView name,course,email;
        ImageView img;
        TextView name, description, price;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

//            img=(CircleImageView)itemView.findViewById(R.id.img1);
//            name=(TextView)itemView.findViewById(R.id.nametext);
//            course=(TextView)itemView.findViewById(R.id.coursetext);
//            email=(TextView)itemView.findViewById(R.id.emailtext);
            img=(ImageView) itemView.findViewById(R.id.img1);
            name=(TextView)itemView.findViewById(R.id.nametext);
            description=(TextView)itemView.findViewById(R.id.descriptiontext);
            price=(TextView)itemView.findViewById(R.id.pricetext);
        }
    }
}
