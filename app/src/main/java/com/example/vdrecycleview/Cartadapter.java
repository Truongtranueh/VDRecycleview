package com.example.vdrecycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cartadapter extends RecyclerView.Adapter<Cartadapter.MyviewHolder> {

    private ArrayList<modelcart> list;
    private IClickListener mIClickListener;
    int TotalQuantity = 1;
    TextView totalPrice;

    public interface IClickListener {
        void onClickDeleteDrink(modelcart models);
    }

    public Cartadapter( ArrayList<modelcart> list, IClickListener listener) {
        this.list = list;
        this.mIClickListener = listener;
    }

    @NonNull
    @Override
    public Cartadapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowcart,parent,false);

        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Cartadapter.MyviewHolder holder, int position) {

        modelcart models = list.get(position);
        if (models==null){ return;}
        holder.namecart.setText(models.getName());
        holder.pricecart.setText(models.getPrice()+" VNĐ");
        Glide.with(holder.imgcart.getContext()).load(models.getUrl()).into(holder.imgcart);

        holder.icon_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickListener.onClickDeleteDrink(models);
            }
        });

        holder.btn_incre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TotalQuantity>0){
                    TotalQuantity++;
                    holder.quantity.setText(String.valueOf(TotalQuantity));
                }
            }
        });

        holder.btn_decre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TotalQuantity<=0) {
                    TotalQuantity=1;
                }
                else
                    TotalQuantity--;
                holder.quantity.setText(String.valueOf(TotalQuantity));
            }
        });

    }


    @Override
    public int getItemCount() {
        if (list!=null){
        return list.size();
        }
        return 0;
    }

    public static class MyviewHolder extends RecyclerView.ViewHolder{

        ImageView imgcart;
        TextView namecart, descriptioncart, pricecart,quantity,totalPrice,btn_Order;
        ImageView icon_delete,btn_incre,btn_decre;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            imgcart = (ImageView) itemView.findViewById(R.id.img_cart);
            namecart = (TextView) itemView.findViewById(R.id.nametext_cart);
            descriptioncart = (TextView)itemView.findViewById(R.id.descriptiontext_cart) ;
            quantity = (TextView)itemView.findViewById(R.id.quantityWantBuy);
            pricecart = (TextView) itemView.findViewById(R.id.pricetextcart);
            icon_delete = (ImageView) itemView.findViewById(R.id.deleteicon);
            btn_incre = (ImageView) itemView.findViewById(R.id.incrementcoffee);
            btn_decre = (ImageView) itemView.findViewById(R.id.decrementcoffee);
            totalPrice = (TextView) itemView.findViewById(R.id.tvTotalAmount);
            btn_Order = (TextView) itemView.findViewById(R.id.buttonPlaceYourOrder);
        }
    }
}
