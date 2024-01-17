package com.example.btl_android.don;

public class Don {
    private int iduser;
    private int idlichtour;
    private int songuoi;
    private double tongtien;
    public Don(int iduser, int idlichtour, int songuoi, double tongtien) {
        this.iduser = iduser;
        this.idlichtour = idlichtour;
        this.songuoi = songuoi;
        this.tongtien=tongtien;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }

    public Don() {
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIdlichtour() {
        return idlichtour;
    }

    public void setIdlichtour(int idlichtour) {
        this.idlichtour = idlichtour;
    }

    public int getSonguoi() {
        return songuoi;
    }

    public void setSonguoi(int songuoi) {
        this.songuoi = songuoi;
    }
}
