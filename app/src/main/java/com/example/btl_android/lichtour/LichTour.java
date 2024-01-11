package com.example.btl_android.lichtour;

import java.util.Date;

public class LichTour {
    private int idlichtour;
    private int idtour;
    private String ngaybatdau;
    private String ngayketthuc;

    public LichTour() {
    }

    public LichTour(int idlichtour, int idtour, String ngaybatdau, String ngayketthuc) {
        this.idlichtour = idlichtour;
        this.idtour = idtour;
        this.ngaybatdau = ngaybatdau;
        this.ngayketthuc = ngayketthuc;
    }

    public int getIdlichtour() {
        return idlichtour;
    }

    public void setIdlichtour(int idlichtour) {
        this.idlichtour = idlichtour;
    }

    public int getIdtour() {
        return idtour;
    }

    public void setIdtour(int idtour) {
        this.idtour = idtour;
    }

    public String getNgaybatdau() {
        return ngaybatdau;
    }

    public void setNgaybatdau(String ngaybatdau) {
        this.ngaybatdau = ngaybatdau;
    }

    public String getNgayketthuc() {
        return ngayketthuc;
    }

    public void setNgayketthuc(String ngayketthuc) {
        this.ngayketthuc = ngayketthuc;
    }
}
