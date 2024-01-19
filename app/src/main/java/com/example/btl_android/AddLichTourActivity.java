package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_android.lichtour.LichTour;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddLichTourActivity extends AppCompatActivity {

    Spinner sp2;
    TextView tv;
    EditText t2;
    EditText n3;
    Button b;
    Button c;
    ArrayList<String> tourIds;
    ArrayAdapter<String> spinnerAdapter;
    TextView tv13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lich_tour);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        c=findViewById(R.id.button7);
        sp2=findViewById(R.id.spinner2);
        t2=findViewById(R.id.editTextDate2);
        n3=findViewById(R.id.editTextNumber3);
        b=findViewById(R.id.button6);
        tv=findViewById(R.id.textView27);
        tv13=findViewById(R.id.textView13);
        //
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {Color.parseColor("#04E1FF"), Color.parseColor("#EEEFEE")}
        );
        tv13.setBackground(gradientDrawable);
        //Mở trang quản lý lichtour
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m=new Intent(AddLichTourActivity.this, QuanLyLichTourActivity.class);
                startActivity(m);
            }
        });
        //Lấy ds tour cho vào spinner
        tourIds = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tourIds);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(spinnerAdapter);
        DatabaseReference myRef2 = database.getReference("tour");

        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot khachSanSnapshot : dataSnapshot.getChildren()) {
                    String khachSanId = khachSanSnapshot.getKey();
                    tourIds.add(khachSanId);
                }
                spinnerAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //Lấy id lớn nhất của lichtour:
        DatabaseReference myRef4 = database.getReference("lichtour");
        myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int maxId = 0; // Khởi tạo biến lưu trữ ID lớn nhất

                for (DataSnapshot khachSanSnapshot : dataSnapshot.getChildren()) {
                    String khachSanId = khachSanSnapshot.getKey();

                    // Chuyển đổi ID từ String sang int
                    int id = Integer.parseInt(khachSanId);

                    // So sánh và cập nhật giá trị ID lớn nhất
                    if (id > maxId) {
                        maxId = id;
                    }
                }
                // Gán giá trị ID lớn nhất vào biến int
                int largestId = maxId+1;
                tv.setText(String.valueOf(largestId));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //Nút thêm
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef1 = database.getReference("lichtour");
                LichTour a=new LichTour();

                a.setIdlichtour(Integer.valueOf(tv.getText().toString()));
                a.setIdtour(Integer.valueOf(sp2.getSelectedItem().toString()));
                if(t2.getText().toString().isEmpty()||n3.getText().toString().isEmpty()){
                    Toast.makeText(AddLichTourActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    a.setNgaybatdau(t2.getText().toString());
                    //

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    try {
                        c.setTime(sdf.parse(t2.getText().toString()));

                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    c.add(Calendar.DATE, Integer.valueOf(n3.getText().toString())); // Số ngày cần thêm

                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                    //
                    a.setNgayketthuc(sdf.format(c.getTime()));
                    myRef1.child(String.valueOf(a.getIdlichtour())).setValue(a);
                    //
                    int ht=Integer.valueOf(tv.getText().toString())+1;
                    tv.setText(String.valueOf(ht));
                    Toast.makeText(AddLichTourActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

                }


                //
            }
        });
    }
}