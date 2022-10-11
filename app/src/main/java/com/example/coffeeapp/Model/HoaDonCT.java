package com.example.coffeeapp.Model;

import java.io.Serializable;

public class HoaDonCT implements Serializable {
    public int MaHD;
    public int MaSP;
    public int MaKH;
    public String TenSP;
    public int SoLuong;
    public int GiaBan;
    public int TongTien;

    public HoaDonCT() {
    }

    public HoaDonCT(int maHD, int maSP, int maKH, String tenSP, int soLuong, int giaBan, int tongTien) {
        MaHD = maHD;
        MaSP = maSP;
        MaKH = maKH;
        TenSP = tenSP;
        SoLuong = soLuong;
        GiaBan = giaBan;
        TongTien = tongTien;
    }

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int maHD) {
        MaHD = maHD;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }

    public int getMaKH() {
        return MaKH;
    }

    public void setMaKH(int maKH) {
        MaKH = maKH;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public int getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(int giaBan) {
        GiaBan = giaBan;
    }

    public int getTongTien() {
        return TongTien;
    }

    public void setTongTien(int tongTien) {
        TongTien = tongTien;
    }
}
