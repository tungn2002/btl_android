package com.example.btl_android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.don.Don;
import com.example.btl_android.lichdathang.LichSuAdapter;
import com.example.btl_android.lichdathang.LichSuItem;
import com.example.btl_android.lichtour.LichTour;
import com.example.btl_android.tour.Tour;
import com.example.btl_android.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// LichSuActivity.java
public class LichSuActivity extends AppCompatActivity {

    private RecyclerView lichSuRecyclerView;
    private LichSuAdapter lichSuAdapter;
    private List<LichSuItem> lichSuItemList;
    Button quaylai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lichsu_activity);

        quaylai = findViewById(R.id.quaylai);

        lichSuRecyclerView = findViewById(R.id.lichSuRecyclerView);
        lichSuItemList = new ArrayList<>();
        lichSuAdapter = new LichSuAdapter(lichSuItemList,LichSuActivity.this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        lichSuRecyclerView.setLayoutManager(layoutManager);
        lichSuRecyclerView.setAdapter(lichSuAdapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LichSuActivity.this);
        int iduser = sharedPreferences.getInt("iduser", -1);

        DatabaseReference donReference = FirebaseDatabase.getInstance().getReference("don").child(String.valueOf(iduser));
        donReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot donSnapshot : dataSnapshot.getChildren()) {
                    Don don = donSnapshot.getValue(Don.class);

                    if (don != null) {
                        int idLichTour = don.getIdlichtour();

                        DatabaseReference lichTourReference = FirebaseDatabase.getInstance().getReference("lichtour").child(String.valueOf(idLichTour));
                        lichTourReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot lichTourSnapshot) {
                                if (lichTourSnapshot.exists()) {
                                    LichTour lichTour = lichTourSnapshot.getValue(LichTour.class);
                                    if (lichTour != null) {
                                        int idTour = lichTour.getIdtour();

                                        DatabaseReference tourReference = FirebaseDatabase.getInstance().getReference("tour").child(String.valueOf(idTour));
                                        tourReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot tourSnapshot) {
                                                if (tourSnapshot.exists()) {
                                                    Tour tour = tourSnapshot.getValue(Tour.class);
                                                    if (tour != null) {
                                                        // Lấy thông tin từ các bảng và thêm vào lichSuItemList
                                                        String tenTour = tour.getTentour();
                                                        double tongTien = don.getTongtien();
                                                        String ngayBatDau = lichTour.getNgaybatdau();
                                                        String ngayKetThuc = lichTour.getNgayketthuc();

                                                        // Tạo một LichSuItem và thêm vào danh sách
                                                        LichSuItem lichSuItem = new LichSuItem(iduser, idLichTour, tenTour, tongTien, ngayBatDau, ngayKetThuc);
                                                        lichSuItemList.add(lichSuItem);

                                                        // Cập nhật Adapter khi thay đổi dữ liệu
                                                        lichSuAdapter.notifyDataSetChanged();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                // Xử lý khi có lỗi
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Xử lý khi có lỗi
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi
            }
        });

        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LichSuActivity.this, UserDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
