package com.example.vdrecycleview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartFragment extends Fragment {

    private RecyclerView recviewcart;
    private ArrayList<modelcart> modelList = new ArrayList<>();
    Cartadapter cartadapter;
    TextView TotalQuantity;
    TextView totalPrice;
    TextView btn_Order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        totalPrice = view.findViewById(R.id.tvTotalAmount);
        TotalQuantity = view.findViewById(R.id.quantityWantBuy);
        recviewcart = view.findViewById(R.id.recview_cart);
        recviewcart.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recviewcart.setLayoutManager(linearLayoutManager);
        cartadapter = new Cartadapter(modelList, new Cartadapter.IClickListener() {
            @Override
            public void onClickDeleteDrink(modelcart models) {
                onClickDeleteDrinkS(models);

            }

        });
        TotalPriceAllD();
        OrderDrink(view);

        recviewcart.setAdapter(cartadapter);
        setUpRecycleviewCart();
        return view;
    }

    private void OrderDrink(View view) {
        btn_Order = view.findViewById(R.id.buttonPlaceYourOrder);
        btn_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"???? th??nh c??ng ?????t h??ng",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setUpRecycleviewCart(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Addtocart");

        //Khi l???y d??? li???u th?? n?? th??m child m???i th?? n?? ch??? cho child m???i c??n nh???ng child kh??c kh??ng ???nh h?????ng g?? c???
        // khi s???a m???t item trong list th?? n?? ch??? s???a item ???? c??n item kh??c n?? s??? kh??ng b??? t??c ?????ng t???i
        // n???u ta t????ng t??c v???i item th?? n?? s??? ko ???nh h?????ng t???i item kh??c
        //tr???i nghi???m t???t h??n c??ch kia v?? n?? kh??ng ph???i load l???i v???i l???i c??ch kia d??? th??m nh???m m???t sp n???a n???u kh??ng code if modelLis!=null
/*
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(modelList!=null){
                    modelList.clear();
                }
                for (DataSnapshot ds : snapshot.getChildren()){
                    modelcart models = ds.getValue(modelcart.class);
                    modelList.add(models);
                }

                cartadapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                modelcart models = snapshot.getValue(modelcart.class);
                if (models==null || modelList==null || modelList.isEmpty()) {
                    return;//n???u 3 c??i tr??n tr???ng th?? kh??ng l??m g?? c???
                }
                for (int i = 0; i<modelList.size();i++){
                    if (models.getIdcart()==modelList.get(i).getIdcart()){
                        modelList.remove(modelList.get(i));
                        break;//l???nh n??y khi???n ch??? khi x??a xong th???ng m??nh mu???n x??a th?? d???ng l???i ko l?? n?? s??? ti???p t???c t??m sp kh??c gi???ng ????? x??a
                    }
                }
                //Khi modelList n??y d??? li???u thay ?????i th?? c??u l???nh n??y s??? load l???i
                cartadapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 if(modelList!=null){
                    modelList.clear();
                }
                for (DataSnapshot ds : snapshot.getChildren()){
                    modelcart models = ds.getValue(modelcart.class);
                    modelList.add(models);
                }

                cartadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //Th??ng b??o n???u m??nh mu???n x??a kh???i gi??? h??ng
    private void onClickDeleteDrinkS(modelcart models){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Addtocart");
        new AlertDialog.Builder(getContext())
                .setTitle("X??a kh???i gi??? h??ng")
                .setMessage("B???n c?? mu???n x??a ????? u???ng n??y ra kh???i gi??? h??ng kh??ng?")
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                reference.child(String.valueOf(models.getIdcart())).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getContext(),"????? u???ng ???? ???????c x??a",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).setNegativeButton("Cancel",null).show();

    }
/*
    private double onCalculateTotalS(modelcart models){
        double totalPrice = 0.0;
        for (int i = 0; i<modelList.size(); i++){
            totalPrice+=modelList.get(i).getPrice()*modelList.get(i).getQuantity();
        }
        return  totalPrice;
    }
*/
    private void TotalPriceAllD(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Addtocart");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sum=0;
                for (DataSnapshot ds: snapshot.getChildren()){
                    Map<String, Object> dmap = (Map<String, Object>) ds.getValue();
                    Object price = dmap.get("price");

                    int dValue = Integer.parseInt(String.valueOf(price));
                    sum+=dValue;
                    totalPrice.setText(String.valueOf(sum)+ " VN??");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;

    }

}
