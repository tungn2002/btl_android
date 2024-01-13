package com.example.btl_android;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.btl_android.tour.Tour;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddTourActivity extends AppCompatActivity {
    TextView tv;
    Button b2;
    EditText ten;
    EditText song;
    EditText diachi;
    EditText gia;
    EditText lich;
    Button b1;
    Spinner s;
    Button b5;
    private String base64Image;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    ArrayList<String> khachSanIds;
    ArrayAdapter<String> spinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);
        tv=findViewById(R.id.textView29);
//hien spinner

        s=findViewById(R.id.spinner);
        khachSanIds = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, khachSanIds);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(spinnerAdapter);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("khachsan");

        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot khachSanSnapshot : dataSnapshot.getChildren()) {
                    String khachSanId = khachSanSnapshot.getKey();
                    khachSanIds.add(khachSanId);
                }
                spinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });
//
//test lay id:
        DatabaseReference myRef4 = database.getReference("tour");

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
                // Sử dụng largestId de them:


                //
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });

//
        ten = findViewById(R.id.editTextText);
        diachi = findViewById(R.id.editTextText3);
        gia = findViewById(R.id.editTextNumber2);
        lich = findViewById(R.id.editTextTextMultiLine);
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chọn ảnh từ thiết bị
                pickImage();
            }
        });
        b5=findViewById(R.id.button5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m=new Intent(AddTourActivity.this, QuanLyActivity.class);
                startActivity(m);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Tour a=new Tour();
                a.setIdtour(Integer.valueOf(tv.getText().toString()));
                a.setTentour(ten.getText().toString());
                a.setDiachixuatphat(diachi.getText().toString());
                a.setDongia(Double.valueOf(gia.getText().toString()));
                a.setLichtrinh(lich.getText().toString());
                a.setImage(base64Image);
                a.setIdkhachsan(Integer.valueOf(s.getSelectedItem().toString()));
                // Lưu thông tin tour vào Firebase

                DatabaseReference myRef = database.getReference("tour");

                myRef.child(String.valueOf(a.getIdtour())).setValue(a);
                int ht=Integer.valueOf(tv.getText().toString())+1;
                tv.setText(String.valueOf(ht));
                // Thực hiện các thao tác khác với dữ liệu tour và ảnh đã chọn ở đây
                //if (base64Image != null) {
                    // Sử dụng base64Image để lưu vào Firebase
                //}
            }
        });

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        if (selectedImageUri != null) {
                            // Chuyển đổi ảnh thành chuỗi Base64
                            base64Image = convertImageToBase64(selectedImageUri);
                            // Thực hiện các thao tác khác với ảnh ở đây
                        }
                    }
                }
            }
        });
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

       private String convertImageToBase64(Uri imageUri) {
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(imageUri);
            byte[] imageBytes = getBytesFromInputStream(inputStream);
            return android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private byte[] getBytesFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}