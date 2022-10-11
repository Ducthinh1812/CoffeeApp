package com.example.coffeeapp.Model;

public class HoaDon {
    public String MaHD;
    public int MaKH;
    public String Date;
    public int ThanhTien;

    public HoaDon() {
    }

    public HoaDon(String maHD, int maKH, String date, int thanhTien) {
        MaHD = maHD;
        MaKH = maKH;
        Date = date;
        ThanhTien = thanhTien;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String maHD) {
        MaHD = maHD;
    }

    public int getMaKH() {
        return MaKH;
    }

    public void setMaKH(int maKH) {
        MaKH = maKH;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(int thanhTien) {
        ThanhTien = thanhTien;
    }
}
