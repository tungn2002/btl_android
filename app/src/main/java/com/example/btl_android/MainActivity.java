package com.example.btl_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.Context;
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

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button login, sigup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        sigup = findViewById(R.id.sigup);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                // Truy vấn dữ liệu từ Firebase Realtime Database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef1 = database.getReference("user");

                myRef1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int iduser = -1;
                        int idvaitro = 0;// Giá trị mặc định nếu không tìm thấy
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            User user = userSnapshot.getValue(User.class);

                            // Lấy thông tin user (ví dụ: password)
                            String storedPassword = user.getMatkhau();
                            String storedEmail = user.getEmail();

                            if (userEmail.equals(storedEmail) && userPassword.equals(storedPassword)) {
                                // Người dùng được tìm thấy, lưu iduser và thoát vòng lặp
                                iduser = user.getIduser();
                                idvaitro = user.getIdvaitro();
                                break;
                            }
                        }

                        if (iduser != -1) {
                            // Mật khẩu khớp, đăng nhập thành công
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("iduser", iduser);
                            editor.apply();
                            if(idvaitro==2){
                                Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, QuanLyActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, TrangChuActivity.class);
                                startActivity(intent);
                            }

                        } else {
                            // Không tìm thấy người dùng hoặc mật khẩu không khớp
                            Toast.makeText(MainActivity.this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                    //...
                });
            }
        });

        sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SigupActivity.class);
                startActivity(intent);
            }
        });
    }
}
