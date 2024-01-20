package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.btl_android.tour.Tour;
import com.example.btl_android.tour.TourAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuanLyActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Tour> a;
    TourAdapter adapter;
    Button b3;
    Button b11;

    TextView tv8;
    Button logout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);

        logout = findViewById(R.id.btnDangXuat);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo hộp thoại xác nhận
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyActivity.this);
                builder.setTitle("Xác nhận đăng xuất");
                builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");

                // Thiết lập nút đồng ý
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý đăng xuất
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(QuanLyActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("iduser");
                        editor.apply();

                        // Chuyển về MainActivity sau khi đăng xuất
                        Intent intent = new Intent(QuanLyActivity.this, MainActivity.class);
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
        //
        tv8=findViewById(R.id.textView8);
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {Color.parseColor("#04E1FF"), Color.parseColor("#EEEFEE")}
        );
        tv8.setBackground(gradientDrawable);
        //
        b3=findViewById(R.id.button3);
        b11=findViewById(R.id.button11);
        //Mở trang thêm
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m=new Intent(QuanLyActivity.this, AddTourActivity.class);
                startActivity(m);
            }
        });
        //Mở trang quản lý lich tour
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m=new Intent(QuanLyActivity.this, QuanLyLichTourActivity.class);
                startActivity(m);
            }
        });
        //Hiện dữ liệu
        a = new ArrayList<>();
        recyclerView = findViewById(R.id.re);

        // Lấy dữ liệu từ Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("tour");
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                a.clear(); // Xóa dữ liệu cũ trong ArrayList trước khi thêm mới
                for (DataSnapshot tourSnapshot : dataSnapshot.getChildren()) {
                    Tour tour = tourSnapshot.getValue(Tour.class);
                    a.add(tour);
                }

                // Cập nhật Adapter khi có dữ liệu thay đổi
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });

        // Khởi tạo RecyclerView và cài đặt LayoutManager
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        // Khởi tạo Adapter và gắn nó với RecyclerView
        adapter = new TourAdapter(this, a);
        recyclerView.setAdapter(adapter);
    }
}