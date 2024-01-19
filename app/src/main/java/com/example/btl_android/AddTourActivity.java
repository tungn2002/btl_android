package com.example.btl_android;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView tvz;
    private String base64Image;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    ArrayList<String> khachSanIds;
    ArrayAdapter<String> spinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);
        //
        tvz=findViewById(R.id.textView);
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[] {Color.parseColor("#04E1FF"), Color.parseColor("#EEEFEE")}
        );
        tvz.setBackground(gradientDrawable);
        //
        tv=findViewById(R.id.textView29);
//hien spinner khách sạn
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
//Lấy id max:
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
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        ten = findViewById(R.id.editTextText);
        diachi = findViewById(R.id.editTextText3);
        gia = findViewById(R.id.editTextNumber2);
        lich = findViewById(R.id.editTextTextMultiLine);
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        //Chọn ảnh
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chọn ảnh từ thiết bị
                pickImage();
            }
        });
        b5=findViewById(R.id.button5);
        //Mở trang quản lý
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m=new Intent(AddTourActivity.this, QuanLyActivity.class);
                startActivity(m);
            }
        });
        //Thêm
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Tour a=new Tour();
                if(tv.getText().toString().isEmpty()||ten.getText().toString().isEmpty()||diachi.getText().toString().isEmpty()||gia.getText().toString().isEmpty()||lich.getText().toString().isEmpty()){
                    Toast.makeText(AddTourActivity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
                else{
                    a.setIdtour(Integer.valueOf(tv.getText().toString()));
                    a.setTentour(ten.getText().toString());
                    a.setDiachixuatphat(diachi.getText().toString());
                    a.setDongia(Double.valueOf(gia.getText().toString()));
                    a.setLichtrinh(lich.getText().toString());
                    a.setIdkhachsan(Integer.valueOf(s.getSelectedItem().toString()));
                    a.setImage(base64Image);
                    // Lưu thông tin tour vào Firebase
                    DatabaseReference myRef = database.getReference("tour");
                    myRef.child(String.valueOf(a.getIdtour())).setValue(a);
                    int ht=Integer.valueOf(tv.getText().toString())+1;
                    tv.setText(String.valueOf(ht));
                    Toast.makeText(AddTourActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //ActivityResultLauncher dang ky hoat dong con và lấy kq từ nó.
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {//sau hoạt động lấy ảnh.
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        if (selectedImageUri != null) {
                            // Chuyển đổi ảnh thành chuỗi Base64
                            base64Image = convertImageToBase64(selectedImageUri);
                        }
                    }
                }
            }
        });
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");//lay cac tep hinh anh
        imagePickerLauncher.launch(intent);
    }

    //hàm đổi ảnh thành base64
       private String convertImageToBase64(Uri imageUri) {
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(imageUri);//Lấy dữ liệu từ uri.inputs chứa dl hình ảnh
            byte[] imageBytes = getBytesFromInputStream(inputStream);//chuyển ảnh thành byte
            return android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);//mã hóa byte thành base64
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
    //chuyển ảnh nhận được thành byte
    private byte[] getBytesFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();//byteBuffer:ghi dữ liệu vào 1 mảng byte
        int bufferSize = 1024;//mỗi lần đọc 1024 byte
        byte[] buffer = new byte[bufferSize];//mảng lưu mỗi lần đọc
        int len;//so byte đã đọc mỗi lần
        while ((len = inputStream.read(buffer)) != -1) {// đọc dl từ InputStream và ghi vào byteBuffer. đến khi -1
            //Trong mỗi lần lặp, dữ liệu được đọc từ InputStream vào buffer bằng cách sử dụng inputStream.read(buffer).
            // Giá trị trả về len sẽ là số byte thực sự đã đọc được từ InputStream.
            //Tiếp theo, dữ liệu đã đọc từ buffer sẽ được ghi vào byteBuffer bằng cách sử dụng byteBuffer.write(buffer, 0, len).
            // 0 là vị trí bắt đầu trong buffer và len là số byte cần ghi từ buffer vào byteBuffer.
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}