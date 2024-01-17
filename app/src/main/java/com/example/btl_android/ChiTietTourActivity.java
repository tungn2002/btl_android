package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    ImageView iv9;
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
        iv9=findViewById(R.id.imageView9);
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
                    Bitmap imageBitmap = decodeBase64(tour.getImage());
                    iv9.setImageBitmap(imageBitmap);
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
                //luu tong tien va lichtour
                SharedPreferences sharedPref2 = ChiTietTourActivity.this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPref2.edit();
                editor2.putFloat("dongia", Float.valueOf(tv40.getText().toString()));
                editor2.apply();
                //luu lichtoi

                //
                Intent intent = new Intent(ChiTietTourActivity.this, DatLichTourActivity.class);
                startActivity(intent);
            }
        });


    }
    private Bitmap decodeBase64(String base64String) {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}