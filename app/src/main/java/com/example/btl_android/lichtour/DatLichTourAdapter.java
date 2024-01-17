package com.example.btl_android.lichtour;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.DatLichTourActivity;
import com.example.btl_android.QuanLyLichTourActivity;
import com.example.btl_android.R;
import com.example.btl_android.TrangChuActivity;
import com.example.btl_android.don.Don;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DatLichTourAdapter extends RecyclerView.Adapter<com.example.btl_android.lichtour.DatLichTourAdapter.ViewHolder> {//bc2
    private List<LichTour> products;//truyen dl tu data vao day
    private Context context;

    public DatLichTourAdapter(Context context, List<LichTour> products) {
        this.context = context;
        this.products = products;
    }


    @NonNull
    @Override
    public com.example.btl_android.lichtour.DatLichTourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//xac dinh view chocac item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item4, parent, false);//toi muon dung file my_itemxml trong file nay
        return new com.example.btl_android.lichtour.DatLichTourAdapter.ViewHolder(view);//sl
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.btl_android.lichtour.DatLichTourAdapter.ViewHolder holder, int position) {//hien thi tung item

        LichTour pro = products.get(position);

        holder.textview48.setText(pro.getNgaybatdau());

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref3 = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                double tongtien = sharedPref3.getFloat("tongtien", -1);
                int songuoi= sharedPref3.getInt("songuoi",-1);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef1 = database.getReference("don");
                Don a=new Don();
                a.setSonguoi(songuoi);
                a.setTongtien(tongtien);
                a.setIduser(1);//doiid
                a.setIdlichtour(pro.getIdlichtour());
                myRef1.child(String.valueOf(a.getIduser())).child(String.valueOf(a.getIdlichtour())).setValue(a);
                Intent m=new Intent(context, TrangChuActivity.class);
                m.putExtra("notificationMessage", "Đặt thành công");

                context.startActivity(m);
            }
        });

    }


    @Override
    public int getItemCount() {
        return products.size();//tra ve so lan lap
    }

    public class ViewHolder extends RecyclerView.ViewHolder {//class lay tu layout item//viet phan nay trc
        private CardView cardview;

        private TextView textview48;


        public ViewHolder(@NonNull View itemView) {//khoi tao cac thanh phan de hien cua moi item

            super(itemView);
            cardview = itemView.findViewById(R.id.ccc);


            textview48 = itemView.findViewById(R.id.textView48);

        }
    }
}