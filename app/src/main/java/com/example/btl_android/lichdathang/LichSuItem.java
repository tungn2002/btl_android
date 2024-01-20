package com.example.btl_android.lichdathang;

public class LichSuItem {

    private int iduser;
    private int idlichtour;
    private String tenTour;
    private double tongTien;
    private String ngayBatDau;
    private String ngayKetThuc;

    // Constructors, getters, setters...

    // Ví dụ:
    public LichSuItem() {
        // Required default constructor for Firebase
    }

    public LichSuItem(int iduser, int idlichtour, String tenTour, double tongTien, String ngayBatDau, String ngayKetThuc) {
        this.iduser = iduser;
        this.idlichtour = idlichtour;
        this.tenTour = tenTour;
        this.tongTien = tongTien;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getTenTour() {
        return tenTour;
    }

    public double getTongTien() {
        return tongTien;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public int getIduser() {
        return iduser;
    }

    public int getIdlichtour() {
        return idlichtour;
    }
}
