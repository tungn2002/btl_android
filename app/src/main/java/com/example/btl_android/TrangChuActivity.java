package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        a = new ArrayList<>();

        recyclerView=findViewById(R.id.rec);

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
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // Khởi tạo Adapter và gắn nó với RecyclerView
        adapter = new DKTourAdapter(this, a);
        recyclerView.setAdapter(adapter);
    }
}