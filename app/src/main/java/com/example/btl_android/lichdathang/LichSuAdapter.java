package com.example.btl_android.lichdathang;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android.LichSuActivity;
import com.example.btl_android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

// ...

public class LichSuAdapter extends RecyclerView.Adapter<LichSuAdapter.ViewHolder> {

    private List<LichSuItem> lichSuItemList;
    private Context context;

    // Constructor
    public LichSuAdapter(List<LichSuItem> lichSuItemList, Context context) {
        this.lichSuItemList = lichSuItemList;
        this.context = context;
    }

    // Inner ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTenTour, tvTongTien, tvNgayBatDau, tvNgayKetThuc;
        public Button huy;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTenTour = itemView.findViewById(R.id.tvTenTour);
            tvTongTien = itemView.findViewById(R.id.tvTongTien);
            tvNgayBatDau = itemView.findViewById(R.id.tvNgayBatDau);
            tvNgayKetThuc = itemView.findViewById(R.id.tvNgayKetThuc);

            huy = itemView.findViewById(R.id.huy);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lichsu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LichSuItem lichSuItem = lichSuItemList.get(position);

        // Hiển thị thông tin lịch sử
        holder.tvTenTour.setText("Tên Tour: " + lichSuItem.getTenTour());
        holder.tvTongTien.setText("Tổng Tiền: " + lichSuItem.getTongTien());
        holder.tvNgayBatDau.setText("Ngày Bắt Đầu: " + lichSuItem.getNgayBatDau());
        holder.tvNgayKetThuc.setText("Ngày Kết Thúc: " + lichSuItem.getNgayKetThuc());

        holder.huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDialog(view.getContext(), lichSuItem);
            }
        });
    }
    private void showConfirmDialog(Context context, LichSuItem lichSuItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận hủy đơn hàng");
        builder.setMessage("Bạn có chắc chắn muốn hủy đơn hàng này?");

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Xử lý hủy đơn hàng ở đây
                int iduser = lichSuItem.getIduser();
                int idlichtour = lichSuItem.getIdlichtour();
                // Gọi phương thức để xóa đơn hàng
                deleteDonHang(iduser, idlichtour);

                // Cập nhật RecyclerView
                lichSuItemList.remove(lichSuItem);
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
    private void deleteDonHang(int iduser, int idlichtour) {

        String node = "don";
        String path = node + "/" + iduser + "/" + idlichtour;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(path);
        databaseReference.removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Xóa thành công
                            Toast.makeText(context, "Đã hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            // Xóa thất bại
                            Toast.makeText(context, "Xóa đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public int getItemCount() {
        return lichSuItemList.size();
    }
}

