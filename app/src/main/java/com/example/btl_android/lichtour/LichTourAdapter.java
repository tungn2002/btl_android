package com.example.btl_android.lichtour;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.QuanLyActivity;
import com.example.btl_android.QuanLyLichTourActivity;
import com.example.btl_android.R;
import com.example.btl_android.tour.Tour;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class LichTourAdapter extends RecyclerView.Adapter<com.example.btl_android.lichtour.LichTourAdapter.ViewHolder> {//bc2
    private List<LichTour> products;//truyen dl tu data vao day
    private Context context;

    public LichTourAdapter(Context context, List<LichTour> products) {
        this.context = context;
        this.products = products;
    }


    @NonNull
    @Override
    public com.example.btl_android.lichtour.LichTourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//xac dinh view chocac item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item2, parent, false);//toi muon dung file my_itemxml trong file nay
        return new com.example.btl_android.lichtour.LichTourAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.btl_android.lichtour.LichTourAdapter.ViewHolder holder, int position) {//hien thi tung item

        LichTour pro = products.get(position);

        //holder.imgview.setText(pro.getImage());
        holder.textview1.setText(String.valueOf(pro.getIdlichtour()));
        holder.textview2.setText(String.valueOf(pro.getIdtour()));
        holder.textview3.setText(pro.getNgaybatdau());
        holder.textview4.setText(pro.getNgayketthuc());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(context).setTitle("a").setMessage("Bạn có muốn xóa không")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef1 = database.getReference("lichtour");
                                myRef1.child(String.valueOf(pro.getIdlichtour())).removeValue();
                                Intent intent = new Intent(context, QuanLyLichTourActivity.class);
                                context.startActivity(intent);
                            }
                        }).setNegativeButton("Cancel",null).show();


            }
        });

    }


    @Override
    public int getItemCount() {
        return products.size();//tra ve so lan lap
    }

    public class ViewHolder extends RecyclerView.ViewHolder {//class lay tu layout item//viet phan nay trc
        private CardView cardview;

        private TextView textview1;
        private TextView textview2;

        private TextView textview3;
        private TextView textview4;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {//khoi tao cac thanh phan de hien cua moi item

            super(itemView);
            cardview = itemView.findViewById(R.id.cardv);

            imageView=itemView.findViewById(R.id.imageView2);
            textview1 = itemView.findViewById(R.id.textView18);
            textview2 = itemView.findViewById(R.id.textView20);
            textview3 = itemView.findViewById(R.id.textView22);
            textview4 = itemView.findViewById(R.id.textView24);

        }
    }
}