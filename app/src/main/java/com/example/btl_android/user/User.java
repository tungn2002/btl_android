package com.example.btl_android.user;

public class User {
    private int iduser;
    private String ten;
    private String email;
    private String sodienthoai;
    private String matkhau;
    private int idvaitro;

    public User(int iduser, String ten, String email, String sodienthoai, String matkhau, int idvaitro) {
        this.iduser = iduser;
        this.ten = ten;
        this.email = email;
        this.sodienthoai = sodienthoai;
        this.matkhau = matkhau;
        this.idvaitro = idvaitro;
    }

    public User() {
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public int getIdvaitro() {
        return idvaitro;
    }

    public void setIdvaitro(int idvaitro) {
        this.idvaitro = idvaitro;
    }
}
