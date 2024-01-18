package com.example.btl_android.khachsan;

public class KhachSan {
    private int idkhachsan;
    private String diachi;
    private int sosao;
    private String tenkhachsan;

    public KhachSan(int idkhachsan, String diachi, int sosao, String tenkhachsan) {
        this.idkhachsan = idkhachsan;
        this.diachi = diachi;
        this.sosao = sosao;
        this.tenkhachsan = tenkhachsan;
    }

    public KhachSan() {
    }

    public int getIdkhachsan() {
        return idkhachsan;
    }

    public void setIdkhachsan(int idkhachsan) {
        this.idkhachsan = idkhachsan;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getSosao() {
        return sosao;
    }

    public void setSosao(int sosao) {
        this.sosao = sosao;
    }

    public String getTenkhachsan() {
        return tenkhachsan;
    }

    public void setTenkhachsan(String tenkhachsan) {
        this.tenkhachsan = tenkhachsan;
    }
}
