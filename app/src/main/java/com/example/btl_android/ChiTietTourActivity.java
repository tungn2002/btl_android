package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_android.lichtour.LichTour;
import com.example.btl_android.lichtour.LichTourAdapter;
import com.example.btl_android.tour.Tour;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChiTietTourActivity extends AppCompatActivity {

    String idd;
    TextView tv36;
    TextView tv38;
    TextView tv40;
    TextView tv42;
    TextView tv44;
    TextView tv46;

    Button b8;
    Button b9;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_tour);
        Intent intent=getIntent();
        int data = intent.getIntExtra("key",0);
        tv36=findViewById(R.id.textView36);
        tv38=findViewById(R.id.textView38);
        tv40=findViewById(R.id.textView40);
        tv42=findViewById(R.id.textView42);
        tv44=findViewById(R.id.textView44);
        tv46=findViewById(R.id.textView46);
        b8=findViewById(R.id.button8);

        //
        String t="tour/"+String.valueOf(data);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference(t);

        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Tour tour = dataSnapshot.getValue(Tour.class);
                    tv36.setText(tour.getTentour());
                    tv38.setText(tour.getDiachixuatphat());
                    tv40.setText(String.valueOf(tour.getDongia()));
                    tv42.setText(String.valueOf(tour.getIdkhachsan()));
                    tv44.setText(String.valueOf(tour.getIdtour()));
                    tv46.setText(tour.getLichtrinh());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });

        //
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m=new Intent(ChiTietTourActivity.this, TrangChuActivity.class);
                startActivity(m);
            }
        });
        b9=findViewById(R.id.button9);
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //luu  tour dang xu ly
                SharedPreferences sharedPref = ChiTietTourActivity.this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("tourID",  Integer.valueOf(tv44.getText().toString()));
                editor.apply();
                //luu tong tien
                SharedPreferences sharedPref2 = ChiTietTourActivity.this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPref2.edit();
                editor2.putFloat("dongia", Float.valueOf(tv40.getText().toString()));
                editor2.apply();
                //
                Intent intent = new Intent(ChiTietTourActivity.this, DatLichTourActivity.class);
                startActivity(intent);
            }
        });
        //
        /*
        myRef1.child(idd).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tour value = dataSnapshot.getValue(Tour.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });*/
/*
        Query query = myRef1.orderByChild("idtour").equalTo(Integer.valueOf(idd));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tourSnapshot : dataSnapshot.getChildren()) {
                    Tour tour = tourSnapshot.getValue(Tour.class);
                    // Hiển thị thông tin của tour lên các view
                    tv36.setText(tour.getTentour());
                    tv38.setText(tour.getDiachixuatphat());
                    tv40.setText(String.valueOf(tour.getDongia()));
                    tv42.setText(String.valueOf(tour.getIdkhachsan()));
                    tv44.setText(String.valueOf(tour.getIdtour()));
                    tv46.setText(tour.getLichtrinh());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });*/
        /*
        Query query = myRef1.orderByChild("idtour").equalTo(Integer.valueOf(idd));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                a.clear(); // Xóa dữ liệu cũ trong ArrayList trước khi thêm mới
                for (DataSnapshot tourSnapshot : dataSnapshot.getChildren()) {
                    LichTour tour = tourSnapshot.getValue(LichTour.class);
                    a.add(tour);
                }

                // Cập nhật Adapter khi có dữ liệu thay đổi
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });*/

    }
}