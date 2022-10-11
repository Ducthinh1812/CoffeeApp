package com.example.coffeeapp.Model;

public class TaiKhoan {
    public String MaKH;
    public String TenKH;
    public String HinhAnhKH;
    public String DiaChi;
    public String NamSinh;
    public String SDT;
    public String Gmail;
    public String MatKhau;

    public TaiKhoan(String maKH, String tenKH, String hinhAnhKH, String diaChi, String namSinh, String SDT, String gmail, String matKhau) {
        MaKH = maKH;
        TenKH = tenKH;
        HinhAnhKH = hinhAnhKH;
        DiaChi = diaChi;
        NamSinh = namSinh;
        this.SDT = SDT;
        Gmail = gmail;
        MatKhau = matKhau;
    }

    public String getHinhAnhKH() {
        return HinhAnhKH;
    }

    public void setHinhAnhKH(String hinhAnhKH) {
        HinhAnhKH = hinhAnhKH;
    }

    public TaiKhoan() {
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String tenKH) {
        TenKH = tenKH;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getNamSinh() {
        return NamSinh;
    }

    public void setNamSinh(String namSinh) {
        NamSinh = namSinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getGmail() {
        return Gmail;
    }

    public void setGmail(String gmail) {
        Gmail = gmail;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }
}
