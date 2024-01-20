package com.example.btl_android;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_android.user.User;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDetailActivity extends AppCompatActivity {

    Button btnDangXuat, btnBack,lichsu;
    TextView tvTen, tvMail, tvSdt;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail_layout);

        tvTen = findViewById(R.id.tvTen);
        tvMail = findViewById(R.id.tvMail);
        tvSdt = findViewById(R.id.tvSdt);

        btnBack = findViewById(R.id.btnBack);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        lichsu = findViewById(R.id.lichsu);
        // Lấy iduser từ SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(UserDetailActivity.this);
        int iduser = sharedPreferences.getInt("iduser", -1); // 0 là giá trị mặc định nếu không tìm thấy "iduser"
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(String.valueOf(iduser));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Dữ liệu tồn tại, bạn có thể lấy và cập nhật giao diện người dùng
                    User user = dataSnapshot.getValue(User.class);
                    updateUI(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailActivity.this, TrangChuActivity.class);
                startActivity(intent);
            }
        });
        lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailActivity.this, LichSuActivity.class);
                startActivity(intent);
            }
        });
//        btnDangXuat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(UserDetailActivity.this);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.remove("iduser");
//                editor.apply();
//
//                // Chuyển về MainActivity sau khi đăng xuất
//                Intent intent = new Intent(UserDetailActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo hộp thoại xác nhận
                AlertDialog.Builder builder = new AlertDialog.Builder(UserDetailActivity.this);
                builder.setTitle("Xác nhận đăng xuất");
                builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");

                // Thiết lập nút đồng ý
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý đăng xuất
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(UserDetailActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("iduser");
                        editor.apply();

                        // Chuyển về MainActivity sau khi đăng xuất
                        Intent intent = new Intent(UserDetailActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                // Thiết lập nút hủy
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng hộp thoại
                        dialog.dismiss();
                    }
                });

                // Hiển thị hộp thoại
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
    private void updateUI(User user) {
        if (user != null) {
            // Cập nhật giao diện người dùng
            tvTen.setText("Tên: " + user.getTen());
            tvMail.setText("Email: " + user.getEmail());
            tvSdt.setText("Số điện thoại: " + user.getSodienthoai());
        }
    }
}
