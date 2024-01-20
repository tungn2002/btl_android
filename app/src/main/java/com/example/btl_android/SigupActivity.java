package com.example.btl_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_android.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigupActivity extends AppCompatActivity {

    EditText ten,mail,sdt,matkhau;
    Button dangky,back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sigup_activity);

        dangky = findViewById(R.id.dangky);
        back = findViewById(R.id.back);
        ten = findViewById(R.id.ten);
        mail = findViewById(R.id.mail);
        sdt = findViewById(R.id.sdt);
        matkhau = findViewById(R.id.matkhau);
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dangky.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userTen = ten.getText().toString();
                        String userMail = mail.getText().toString();
                        String userSdt = sdt.getText().toString();
                        String userMatkhau = matkhau.getText().toString();

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef1 = database.getReference("user");
                        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int maxIdUser = 0;
                                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                    User user = userSnapshot.getValue(User.class);
                                    if (user != null && user.getIduser() > maxIdUser) {
                                        maxIdUser = user.getIduser();
                                    }
                                }
                                User newUser = new User(maxIdUser + 1, userTen, userMail, userSdt, userMatkhau,1);

                                // Thêm người dùng mới vào Firebase
                                String userId = String.valueOf(maxIdUser + 1);
                                myRef1.child(userId).setValue(newUser);

                                Toast.makeText(SigupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SigupActivity.this, MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
