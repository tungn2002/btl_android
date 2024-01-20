package com.example.btl_android.tour;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.ChiTietTourActivity;
import com.example.btl_android.QuanLyActivity;
import com.example.btl_android.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DKTourAdapter extends RecyclerView.Adapter<DKTourAdapter.ViewHolder> {//bc2
    private List<Tour> products;//truyen dl tu data vao day
    private Context context;

    public DKTourAdapter(Context context, List<Tour> products) {
        this.context = context;
        this.products = products;
    }


    @NonNull
    @Override
    public DKTourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//xac dinh view chocac item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item3, parent, false);//toi muon dung file my_itemxml trong file nay
        return new DKTourAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DKTourAdapter.ViewHolder holder, int position) {//hien thi tung item
        Tour pro = products.get(position);
        Bitmap imageBitmap = decodeBase64(pro.getImage());
        holder.imgview.setImageBitmap(imageBitmap);
        holder.textview10.setText(pro.getTentour());
        holder.textview12.setText(String.valueOf(pro.getDongia()));
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef1 = database.getReference("tour");
                myRef1.child(String.valueOf(pro.getIdtour())).removeValue();
                Intent intent = new Intent(context, QuanLyActivity.class);
                context.startActivity(intent);
*/
                //Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ChiTietTourActivity.class);
                intent.putExtra("key", pro.getIdtour());
                context.startActivity(intent);
            }
        });

    }
    private Bitmap decodeBase64(String base64String) {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    @Override
    public int getItemCount() {
        return products.size();//tra ve so lan lap
    }

    public class ViewHolder extends RecyclerView.ViewHolder {//class lay tu layout item//viet phan nay trc
        private CardView cardview;
        private ImageView imgview;

        private TextView textview10;
        private TextView textview12;


        public ViewHolder(@NonNull View itemView) {//khoi tao cac thanh phan de hien cua moi item

            super(itemView);
            cardview = itemView.findViewById(R.id.cardd);
            imgview = itemView.findViewById(R.id.imageView7);

            textview10 = itemView.findViewById(R.id.textView31);
            textview12 = itemView.findViewById(R.id.textView33);

        }
    }
}