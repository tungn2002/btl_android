package com.example.btl_android.tour;

public class Tour {
    private int idtour;
    private String tentour;
    private String diachixuatphat;
    private double dongia;
    private String lichtrinh;
    private int idkhachsan;
    private String image;

    public Tour(int idtour, String tentour, int songuoi, String diachixuatphat, double dongia, String lichtrinh, int idkhachsan, String image) {
        this.idtour = idtour;
        this.tentour = tentour;
        this.diachixuatphat = diachixuatphat;
        this.dongia = dongia;
        this.lichtrinh = lichtrinh;
        this.idkhachsan = idkhachsan;
        this.image = image;
    }

    public Tour() {
    }

    public int getIdtour() {
        return idtour;
    }

    public void setIdtour(int idtour) {
        this.idtour = idtour;
    }

    public String getTentour() {
        return tentour;
    }

    public void setTentour(String tentour) {
        this.tentour = tentour;
    }

    public String getDiachixuatphat() {
        return diachixuatphat;
    }

    public void setDiachixuatphat(String diachixuatphat) {
        this.diachixuatphat = diachixuatphat;
    }

    public double getDongia() {
        return dongia;
    }

    public void setDongia(double dongia) {
        this.dongia = dongia;
    }

    public String getLichtrinh() {
        return lichtrinh;
    }

    public void setLichtrinh(String lichtrinh) {
        this.lichtrinh = lichtrinh;
    }

    public int getIdkhachsan() {
        return idkhachsan;
    }

    public void setIdkhachsan(int idkhachsan) {
        this.idkhachsan = idkhachsan;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
