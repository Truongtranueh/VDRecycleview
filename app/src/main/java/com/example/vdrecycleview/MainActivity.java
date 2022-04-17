package com.example.vdrecycleview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button btn_dangky, btn_dangnhap;
    EditText txtemail, txtpass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        btn_dangky = findViewById(R.id.btn_dangky);
        btn_dangnhap = findViewById(R.id.btn_dangnhap);

        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DangKy.class);
                startActivity(i);
            }
        });
        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserLogin();
            }
        });

    }
    private  void UserLogin(){
//        txtemail = findViewById(R.id.edt_email);
//        txtpass = findViewById(R.id.edt_password);
//
//        String a = txtemail.getText().toString().trim();
//        String b = txtpass.getText().toString().trim();
//
//        if(a.isEmpty()){
//            txtemail.setError("Bạn chưa nhập email!");
//            txtemail.requestFocus();
//            return;
//        }
//        if(b.isEmpty()){
//            txtpass.setError("Bạn chưa nhập mật khẩu!");
//            txtpass.requestFocus();
//            return;
//        }
//        mAuth.signInWithEmailAndPassword(a,b).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
                    Intent sucess = new Intent(MainActivity.this,DirectionFragment.class);
                    startActivity(sucess);
//                }
//                else {
//                    Toast.makeText(getApplicationContext(),"Đăng nhập thất bại!", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }


}