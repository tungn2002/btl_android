package com.example.btl_android.yeuthich;

public class Yeuthich {
    private int iduser;
    private int idtour;

    // Constructor
    public Yeuthich() {
        // Empty constructor required for Firebase
    }

    // Constructor với tham số
    public Yeuthich(int iduser, int idtour) {
        this.iduser = iduser;
        this.idtour = idtour;
    }

    // Getter và Setter cho iduser
    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    // Getter và Setter cho idtour
    public int getIdtour() {
        return idtour;
    }

    public void setIdtour(int idtour) {
        this.idtour = idtour;
    }
}
