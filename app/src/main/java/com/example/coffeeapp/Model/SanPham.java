package com.example.coffeeapp.Model;

import java.io.Serializable;

public class SanPham implements Serializable {
    public int MaSP;
    public int MaTL;
    public String HinhanhSP;
    public String TenSP;
    public int SoLuong;
    public String Size;
    public int GiaBan;
    public String about;

    public SanPham() {
    }

    public SanPham(int maSP, int maTL, String hinhanhSP, String tenSP, int soLuong, String size, int giaBan, String about) {
        MaSP = maSP;
        MaTL = maTL;
        HinhanhSP = hinhanhSP;
        TenSP = tenSP;
        SoLuong = soLuong;
        Size = size;
        GiaBan = giaBan;
        this.about = about;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }

    public int getMaTL() {
        return MaTL;
    }

    public void setMaTL(int maTL) {
        MaTL = maTL;
    }

    public String getHinhanhSP() {
        return HinhanhSP;
    }

    public void setHinhanhSP(String hinhanhSP) {
        HinhanhSP = hinhanhSP;
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

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public int getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(int giaBan) {
        GiaBan = giaBan;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
