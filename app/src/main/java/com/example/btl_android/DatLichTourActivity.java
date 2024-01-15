package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.btl_android.lichtour.DatLichTourAdapter;
import com.example.btl_android.lichtour.LichTour;
import com.example.btl_android.tour.DKTourAdapter;
import com.example.btl_android.tour.Tour;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatLichTourActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<LichTour> a;
    DatLichTourAdapter adapter;
    Button b10;
    TextView tv47;
    EditText editTextN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_lich_tour);
        tv47=findViewById(R.id.textView47);
        editTextN=findViewById(R.id.editTextNumber);
        editTextN.setText("");
        tv47.setText("0");
        editTextN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                SharedPreferences sharedPref3 = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                double dongia = sharedPref3.getFloat("dongia", -1);
                if(editTextN.getText().toString().equals("")){
                    tv47.setText("0");
                }
                else{

                    double z=Double.valueOf(editTextN.getText().toString())*dongia;
                    tv47.setText(String.valueOf(z));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        b10=findViewById(R.id.button10);
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m=new Intent(DatLichTourActivity.this, TrangChuActivity.class);
                startActivity(m);
            }
        });
//
        a = new ArrayList<>();

        recyclerView=findViewById(R.id.ref);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("lichtour");

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                a.clear(); // Xóa dữ liệu cũ trong ArrayList trước khi thêm mới
                for (DataSnapshot tourSnapshot : dataSnapshot.getChildren()) {
                    LichTour tour = tourSnapshot.getValue(LichTour.class);
                    SharedPreferences sharedPref = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    int userID = sharedPref.getInt("tourID", -1);
                    if(tour.getIdtour()==userID){
                        a.add(tour);
                    }
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
        adapter = new DatLichTourAdapter(this, a);
        recyclerView.setAdapter(adapter);
    }
}