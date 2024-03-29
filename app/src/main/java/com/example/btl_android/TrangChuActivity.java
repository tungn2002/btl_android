package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.btl_android.lichtour.LichTour;
import com.example.btl_android.lichtour.LichTourAdapter;
import com.example.btl_android.tour.DKTourAdapter;
import com.example.btl_android.tour.Tour;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrangChuActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Tour> a;
    DKTourAdapter adapter;
    LinearLayout ln1;
    ImageView imageView6;
    ImageView imageView5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        //
        imageView6 = findViewById(R.id.imageView6);
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuActivity.this, UserDetailActivity.class);
                startActivity(intent);
            }
        });
        imageView5 = findViewById(R.id.imageView5);
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuActivity.this, yeuthichActivity.class);
                startActivity(intent);
            }
        });


        ln1=findViewById(R.id.ln1);
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {Color.parseColor("#04E1FF"), Color.parseColor("#EEEFEE")}
        );
        ln1.setBackground(gradientDrawable);
        //Thong bao: dat thanh cong
        String notificationMessage = getIntent().getStringExtra("notificationMessage");
        if (notificationMessage != null) {
            // Hiển thị thông báo
            Toast.makeText(this, notificationMessage, Toast.LENGTH_SHORT).show();
        }
        //Hien danh sách tour
        a = new ArrayList<>();
        recyclerView=findViewById(R.id.rec);
        // Lấy dữ liệu từ Firebase Realtime Database+ mỗi khi có dl thay đổi =>load
        FirebaseDatabase database = FirebaseDatabase.getInstance();//khởi tạo
        DatabaseReference myRef1 = database.getReference("tour");//tham chiếu đến dl
        myRef1.addValueEventListener(new ValueEventListener() {//dlt thay đổi=>ValueEventListener: trả về all ds vào 1 datasnapshot
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
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);//tạo lưới chứa item
        recyclerView.setLayoutManager(layoutManager);//set layout cho recyle

        // Khởi tạo Adapter và gắn nó với RecyclerView
        adapter = new DKTourAdapter(this, a);//truyền activity và arraylist đối tượng
        recyclerView.setAdapter(adapter);//gán dl cho giao diện để hiển thị dl

    }
}