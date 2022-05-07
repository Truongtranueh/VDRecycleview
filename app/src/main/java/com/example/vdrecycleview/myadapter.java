package com.example.vdrecycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder>
{

    public ArrayList<model> listmodel;
    private Context context;
    private DatabaseReference reference;

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model)
    {   if (model==null){ return;}
        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.price.setText(model.getPrice() + " VNĐ"); //sửa lại tí

        reference = FirebaseDatabase.getInstance().getReference("Addtocart");
        String idcart = reference.push().getKey();

        holder.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> parameter = new HashMap<>();
                parameter.put("idcart",idcart);//dòng thêm
                parameter.put("name",model.getName());
                parameter.put("description",model.getDescription());
                parameter.put("quantity","1");//dòng thêm
                parameter.put("price",model.getPrice());
                parameter.put("url",model.getUrl());
                reference.child(idcart).setValue(parameter);

            }
        });
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
        ImageView img;
        TextView name, description, price;
        Button btnadd;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            img=(ImageView) itemView.findViewById(R.id.img1);
            name=(TextView)itemView.findViewById(R.id.nametext);
            description=(TextView)itemView.findViewById(R.id.descriptiontext);
            price=(TextView)itemView.findViewById(R.id.pricetext);
            btnadd = (Button) itemView.findViewById(R.id.addtocart);
        }
    }
}
