package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_android.khachsan.KhachSan;
import com.example.btl_android.tour.Tour;
import com.example.btl_android.yeuthich.Yeuthich;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChiTietTourActivity extends AppCompatActivity {

    String idd;
    TextView tv36;
    TextView tv38;
    TextView tv40;
    TextView tv42;
    TextView tv44;
    TextView tv46;
    ImageView iv9;
    Button b8;
    Button b9;
    RecyclerView recyclerView;
    ImageView iv8;
    ImageView imageView11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_tour);
        Intent intent=getIntent();
        int data = intent.getIntExtra("key",0);
        iv8=findViewById(R.id.imageView8);
        tv36=findViewById(R.id.textView36);
        tv38=findViewById(R.id.textView38);
        tv40=findViewById(R.id.textView40);
        tv42=findViewById(R.id.textView42);
        tv44=findViewById(R.id.textView44);
        tv46=findViewById(R.id.textView46);
        iv9=findViewById(R.id.imageView9);
        b8=findViewById(R.id.button8);
        imageView11 = findViewById(R.id.imageView11);


        //Lấy dữ liệu theo idtour được chọn
        String t="tour/"+String.valueOf(data);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference(t);
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {//nghe 1 lần duy nhất .k load
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Tour tour = dataSnapshot.getValue(Tour.class);
                    tv36.setText(tour.getTentour());
                    tv38.setText(tour.getDiachixuatphat());
                    tv40.setText(String.valueOf(tour.getDongia()));
                    //lấy số sao của khach san
                    String t="khachsan/"+String.valueOf(tour.getIdkhachsan());
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef1 = database.getReference(t);


                    myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                KhachSan tour2 = dataSnapshot.getValue(KhachSan.class);
                                tv42.setText(String.valueOf(tour2.getSosao())+" sao");
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    tv44.setText(String.valueOf(tour.getIdtour()));
                    Bitmap imageBitmap = decodeBase64(tour.getImage());//chuyển ảnh thành
                    iv9.setImageBitmap(imageBitmap);
                    tv46.setText(tour.getLichtrinh());

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });

        //Mo trang chủ
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m=new Intent(ChiTietTourActivity.this, TrangChuActivity.class);
                startActivity(m);
            }
        });
        b9=findViewById(R.id.button9);
        //Nút đặt tour
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lưu tour đang xử lý
                SharedPreferences sharedPref = ChiTietTourActivity.this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("tourID",  Integer.valueOf(tv44.getText().toString()));
                editor.apply();
                //Lưu tổng tiền
                SharedPreferences sharedPref2 = ChiTietTourActivity.this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPref2.edit();
                editor2.putFloat("dongia", Float.valueOf(tv40.getText().toString()));
                editor2.apply();
                Intent intent = new Intent(ChiTietTourActivity.this, DatLichTourActivity.class);
                startActivity(intent);
            }
        });//Mở map
        iv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLocation(tv38.getText().toString());
            }
        });
        imageView11.setOnClickListener(new View.OnClickListener() { //yeu thich
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ChiTietTourActivity.this);
                int iduser = sharedPreferences.getInt("iduser", -1); // 0 là giá trị mặc định nếu không tìm thấy "iduser"
                DatabaseReference yeuthichRef = FirebaseDatabase.getInstance().getReference("yeuthich");
                ;// Tạo một đối tượng Yeuthich mới
                Yeuthich yeuthich = new Yeuthich();
                yeuthich.setIdtour(Integer.valueOf(tv44.getText().toString()));
                yeuthich.setIduser(iduser);

// Đặt giá trị của đối tượng Yeuthich vào Firebase Realtime Database
                yeuthichRef.child(String.valueOf(yeuthich.getIduser())).child(String.valueOf(yeuthich.getIdtour())).setValue(yeuthich);
                Toast.makeText(ChiTietTourActivity.this, "Yêu thích Tour thành công", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void searchLocation(String searchText) {//hàm mở googlemap và tìm
        Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + searchText);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
    private Bitmap decodeBase64(String base64String) {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
    //Chuỗi Base64 sẽ bao gồm các ký tự trong bảng Base64, biểu diễn dữ liệu nhị phân của hình ảnh
}