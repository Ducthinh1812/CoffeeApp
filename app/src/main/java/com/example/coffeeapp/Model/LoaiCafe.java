package com.example.coffeeapp.Model;

import java.io.Serializable;

public class LoaiCafe  {
    public int MaTL;
    public String HinhAnhTL;
    public String TenTL;

    public LoaiCafe(int maTL, String hinhAnhTL, String tenTL) {
        MaTL = maTL;
        HinhAnhTL = hinhAnhTL;
        TenTL = tenTL;
    }

    public String getHinhAnhTL() {
        return HinhAnhTL;
    }

    public void setHinhAnhTL(String hinhAnhTL) {
        HinhAnhTL = hinhAnhTL;
    }

    public LoaiCafe() {
    }

    public int getMaTL() {
        return MaTL;
    }

    public void setMaTL(int maTL) {
        MaTL = maTL;
    }

    public String getTenTL() {
        return TenTL;
    }

    public void setTenTL(String tenTL) {
        TenTL = tenTL;
    }
}
