package com.example.btl_android.yeuthich;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.R;
import com.example.btl_android.yeuthichActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class YeuthichAdapter extends RecyclerView.Adapter<YeuthichAdapter.ViewHolder> {//bc2
    private List<Integer> products;

    private Context context;
    public YeuthichAdapter(List<Integer> tourItemList, yeuthichActivity context) {
        this.products = tourItemList;
        this.context = context;
    }

    // Inner ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTenTour;
        public Button huyyt;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTenTour = itemView.findViewById(R.id.tvTenTour);
            huyyt = itemView.findViewById(R.id.huyyt);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item5, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Integer pro = products.get(position);
        holder.tvTenTour.setText("Id tour: "+ pro);
        holder.huyyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDialog(view.getContext(), pro);
            }
        });
    }
    private void showConfirmDialog(Context context, Integer pro) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận hủy đơn hàng");
        builder.setMessage("Bạn có chắc chắn muốn hủy đơn hàng này?");

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Xử lý hủy đơn hàng ở đây
//                int iduser = pro;
                // Gọi phương thức để xóa đơn hàng
                deleteYeuThich(pro);

                products.remove(pro);
                notifyDataSetChanged();

                // Đóng dialog
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Đóng dialog
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // Phương thức xóa đơn hàng từ Firebase hoặc SQLite
    private void deleteYeuThich(Integer pro) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int iduser = sharedPreferences.getInt("iduser", -1);
        String node = "yeuthich";
        String path = node+ "/" + 1 + "/" + pro;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(path);
        databaseReference.removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Xóa thành công
                            Toast.makeText(context, "Đã hủy yêu thích thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            // Xóa thất bại
                            Toast.makeText(context, "Hủy yêu thích thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}