package com.example.coffeeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeapp.Model.GioHang;
import com.example.coffeeapp.Model.SanPham;
import com.example.coffeeapp.R;
import com.example.coffeeapp.fragment.HomeFragment;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSanPham extends AppCompatActivity {
    ImageView imgChitiet;
    TextView txtten, txtgia, txtmota, Soluong;
    Button btndatmua, btntru, btncong;
    String Tensanpham;
    Integer Giasanpham;
    String Hinhanhsanpham, Motasanpham;
    int id, IDSanpham;
    ArrayList<SanPham> sanPhamArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        sanPhamArrayList = new ArrayList<>();
        Anhxa();
        GetInformation();
        ThemGioHang();
    }

    private void GetInformation() {
        SanPham sanpham = (SanPham) getIntent().getSerializableExtra("data");
        Tensanpham = sanpham.getTenSP();
        Giasanpham = sanpham.getGiaBan();
        Hinhanhsanpham = sanpham.getHinhanhSP();
        Motasanpham = sanpham.getAbout();
        IDSanpham = sanpham.getMaSP();
        id = sanpham.getMaSP();
        //
        txtten.setText(Tensanpham);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá: " + decimalFormat.format(Giasanpham) + " VND");
        txtmota.setText(Motasanpham);
        Picasso.get().load(Hinhanhsanpham).into(imgChitiet);
    }

    private void Anhxa() {
        imgChitiet = findViewById(R.id.imageviewchitietsanpham);
        txtten = findViewById(R.id.textviewtenchitietsanpham);
        txtgia = findViewById(R.id.textviewgiachitietsanpham);
        txtmota = findViewById(R.id.textviewmotachitietsanpham);
        Soluong = findViewById(R.id.spinner);
        btndatmua = findViewById(R.id.buttondatmua);
        btncong = findViewById(R.id.buttoncongCTSP);
        btntru = findViewById(R.id.buttontruCTSP);
    }

    void ThemGioHang() {
        btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoi = Integer.parseInt(Soluong.getText().toString()) - 1;
                Soluong.setText(Integer.toString(slmoi));
            }
        });
        btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoi = Integer.parseInt(Soluong.getText().toString()) + 1;
                Soluong.setText(Integer.toString(slmoi));
            }
        });
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeFragment.giohanglist.size() > 0)//gio hang khong rong
                {
                    int sl = Integer.parseInt(Soluong.getText().toString());//lay so luong trong spinner
                    boolean tontaimahang = false;
                    for (int i = 0; i < HomeFragment.giohanglist.size(); i++)//dem xem trong gio hang co gi
                    {
                        if (HomeFragment.giohanglist.get(i).getIdsp() == id)//neu co hang ta muon mua them
                        {
                            //so luong = so luong cu + moi
                            HomeFragment.giohanglist.get(i).setSoluongsp(HomeFragment.giohanglist.get(i).getSoluongsp() + sl);
//                            if(HomeFragment.giohanglist.get(i).getSoluongsp()>=10)//neu so luong sp >10, giu nguyen 10
//                            {
//                                HomeFragment.giohanglist.get(i).setSoluongsp(10);
//                            }
                            //tinh tien = Don gia * so luong
                            HomeFragment.giohanglist.get(i).setGiasp(Giasanpham * HomeFragment.giohanglist.get(i).getSoluongsp());
                            tontaimahang = true;
                        }
                    }
                    if (tontaimahang == false)//truong hop co hang, nhung ma hang muon mua them khong ton tai
                    {
                        int sl1 = Integer.parseInt(Soluong.getText().toString());//lay so luong trong spinner
                        //tinh tien
                        long Tien2 = sl1 * Giasanpham;
                        //them vao mang gio hang
                        HomeFragment.giohanglist.add(new GioHang(id, Tensanpham, Tien2, Hinhanhsanpham, sl1));
                    }
                } else //gio hang rong
                {
                    int sl2 = Integer.parseInt(Soluong.getText().toString());//lay so luong trong spinner
                    //tinh tien
                    long Tien2 = sl2 * Giasanpham;
                    //them vao mang gio hang
                    HomeFragment.giohanglist.add(new GioHang(id, Tensanpham, Tien2, Hinhanhsanpham, sl2));
                }
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

}