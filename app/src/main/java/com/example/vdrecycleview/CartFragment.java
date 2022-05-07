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
                Toast.makeText(view.getContext(),"Đã thành công đặt hàng",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setUpRecycleviewCart(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Addtocart");

        //Khi lấy dữ liệu thì nó thêm child mới thì nó chỉ cho child mới còn những child khác không ảnh hưởng gì cả
        // khi sửa một item trong list thì nó chỉ sửa item đó còn item khác nó sẽ không bị tác động tới
        // nếu ta tương tác với item thì nó sẽ ko ảnh hưởng tới item khác
        //trải nghiệm tốt hơn cách kia vì nó không phải load lại với lại cách kia dễ thêm nhầm một sp nữa nếu không code if modelLis!=null
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
                    return;//nếu 3 cái trên trống thì không làm gì cả
                }
                for (int i = 0; i<modelList.size();i++){
                    if (models.getIdcart()==modelList.get(i).getIdcart()){
                        modelList.remove(modelList.get(i));
                        break;//lệnh này khiến chỉ khi xóa xong thằng mình muốn xóa thì dừng lại ko là nó sẽ tiếp tục tìm sp khác giống để xóa
                    }
                }
                //Khi modelList này dữ liệu thay đổi thì câu lệnh này sẽ load lại
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
    //Thông báo nếu mình muốn xóa khỏi giỏ hàng
    private void onClickDeleteDrinkS(modelcart models){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Addtocart");
        new AlertDialog.Builder(getContext())
                .setTitle("Xóa khỏi giỏ hàng")
                .setMessage("Bạn có muốn xóa đồ uống này ra khỏi giỏ hàng không?")
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                reference.child(String.valueOf(models.getIdcart())).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getContext(),"Đồ uống đã được xóa",Toast.LENGTH_SHORT).show();
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
                    totalPrice.setText(String.valueOf(sum)+ " VNĐ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;

    }

}
