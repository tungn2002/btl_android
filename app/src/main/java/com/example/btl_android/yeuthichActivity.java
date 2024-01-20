package com.example.btl_android;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.view.View;
        import android.widget.Button;

        import com.example.btl_android.tour.Tour;
        import com.example.btl_android.tour.TourAdapter;
        import com.example.btl_android.yeuthich.Yeuthich;
        import com.example.btl_android.yeuthich.YeuthichAdapter;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;

// TourActivity.java
public class yeuthichActivity extends AppCompatActivity {

    private RecyclerView tourRecyclerView;
    private YeuthichAdapter TourAdapter;
    private List<Integer> tourItemList;
    private Button button13;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeuthich);

        button13 = findViewById(R.id.button13);

        tourRecyclerView = findViewById(R.id.rec1);
        tourItemList = new ArrayList<>();
        TourAdapter = new YeuthichAdapter(tourItemList,yeuthichActivity.this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        tourRecyclerView.setLayoutManager(layoutManager);
        tourRecyclerView.setAdapter(TourAdapter);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(yeuthichActivity.this);
        int iduser = sharedPreferences.getInt("iduser", -1);


        String node = "yeuthich";
        String path = node + "/" + iduser;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(path);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tourSnapshot : dataSnapshot.getChildren()) {
                    Yeuthich yeuthich = tourSnapshot.getValue(Yeuthich.class);
                    int idtour = yeuthich.getIdtour();
                    tourItemList.add(idtour);
                }

                // Cập nhật Adapter khi có dữ liệu thay đổi
                TourAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(yeuthichActivity.this, TrangChuActivity.class);
                startActivity(intent);
            }
        });
    }
}