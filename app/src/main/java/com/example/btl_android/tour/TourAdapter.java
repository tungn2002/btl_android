package com.example.btl_android.tour;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_android.QuanLyActivity;
import com.example.btl_android.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder> {//bc2
    private List<Tour> products;//truyen dl tu data vao day
    private Context context;

    public TourAdapter(Context context, List<Tour> products) {
        this.context = context;
        this.products = products;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//xac dinh view chocac item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item, parent, false);//toi muon dung file my_itemxml trong file nay
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourAdapter.ViewHolder holder, int position) {//hien thi tung item
        Tour pro = products.get(position);
        Bitmap imageBitmap = decodeBase64(pro.getImage());
        holder.imgview.setImageBitmap(imageBitmap);
        //holder.imgview.setText(pro.getImage());
        holder.textview10.setText(String.valueOf(pro.getIdtour()));
        holder.textview12.setText(pro.getTentour());


        holder.imgview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context).setTitle("a").setMessage("Bạn có muốn xóa không")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef1 = database.getReference("tour");
                                myRef1.child(String.valueOf(pro.getIdtour())).removeValue();
                                Intent intent = new Intent(context, QuanLyActivity.class);
                                context.startActivity(intent);
                            }
                        }).setNegativeButton("Cancel",null).show();

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
        private ImageView imgview2;

        private TextView textview10;
        private TextView textview12;


        public ViewHolder(@NonNull View itemView) {//khoi tao cac thanh phan de hien cua moi item

            super(itemView);
            cardview = itemView.findViewById(R.id.cardv);
            imgview = itemView.findViewById(R.id.imageView);
            imgview2 = itemView.findViewById(R.id.imageView3);

            textview10 = itemView.findViewById(R.id.textView10);
            textview12 = itemView.findViewById(R.id.textView12);

        }
    }
}