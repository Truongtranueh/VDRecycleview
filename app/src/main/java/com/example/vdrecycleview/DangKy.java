package com.example.vdrecycleview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.database.FirebaseDatabase;

public class DangKy extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private EditText edtname, edtage, edtemail, edtpass;
    private Button btndangky, btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        mAuth = FirebaseAuth.getInstance();

        edtname = (EditText)findViewById(R.id.name);
        edtage = (EditText)findViewById(R.id.age) ;
        edtemail = (EditText) findViewById(R.id.edt_emaildk);
        edtpass = (EditText) findViewById(R.id.edt_passworddk);
        btnDangKyClick();
        btnBackClick();
    }
    private void  btnBackClick(){
        btnback = (Button) findViewById(R.id.btn_trovetrangchu);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bc = new Intent(DangKy.this,MainActivity.class);
                startActivity(bc);
            }
        });
    }
    private  void btnDangKyClick(){
        btndangky = (Button) findViewById(R.id.btn_dangky);

        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtname.getText().toString().trim();
                String age = edtage.getText().toString().trim();
                String email = edtemail.getText().toString().trim();
                String pass = edtpass.getText().toString().trim();

                if(name.isEmpty()){
                    edtname.setError("Bạn chưa nhập tên");
                    edtname.requestFocus();
                    return;
                }
                if(age.isEmpty()){
                    edtage.setError("Bạn chưa nhập tuổi!");
                    edtage.requestFocus();
                    return;
                }
                if(email.isEmpty()){
                    edtemail.setError("Email không được để trống");
                    edtemail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edtemail.setError("Email chưa chính xác!");
                    edtemail.requestFocus();
                    return;
                }
                if(pass.isEmpty()){
                    edtpass.setError("Bạn chưa nhập mật khẩu");
                    edtpass.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    User user = new User(name,age,email);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(DangKy.this,"Đăng ký thành công", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(DangKy.this,"Đăng ký không thành công", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }
}