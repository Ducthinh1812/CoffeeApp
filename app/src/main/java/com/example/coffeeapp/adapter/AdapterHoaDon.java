package com.example.coffeeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.coffeeapp.Model.HoaDon;
import com.example.coffeeapp.Model.TaiKhoan;
import com.example.coffeeapp.R;
import com.example.coffeeapp.activity.ChiTietHDActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterHoaDon extends RecyclerView.Adapter<AdapterHoaDon.HoaDonHolder> {

    Context context;
    ArrayList<HoaDon> hoaDonArrayList;

    public AdapterHoaDon(Context context, ArrayList<HoaDon> hoaDonArrayList) {
        this.context = context;
        this.hoaDonArrayList = hoaDonArrayList;
    }

    @NonNull
    @Override
    public HoaDonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoa_don, parent, false);
        return new HoaDonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonHolder holder, int position) {
        HoaDon hoaDon = hoaDonArrayList.get(position);
        holder.tvMaHD.setText(hoaDon.getMaHD());
        holder.tvMAKHHD.setText(String.valueOf(hoaDon.getMaKH()));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvTongtien.setText(decimalFormat.format(hoaDon.getThanhTien()) + " VND");
        holder.tvNgayTaoHD.setText(hoaDon.getDate());

        holder.cvHoaDons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChiTietHDActivity.class);
                intent.putExtra("mahoadon", hoaDon.getMaHD());
                intent.putExtra("tenkh", hoaDon.getMaKH());
                intent.putExtra("Thanhtien", hoaDon.getThanhTien());
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return hoaDonArrayList.size();
    }

    public class HoaDonHolder extends RecyclerView.ViewHolder {
        TextView tvMaHD, tvMAKHHD, tvNgayTaoHD, tvTongtien;
        CardView cvHoaDons;

        public HoaDonHolder(@NonNull View itemView) {
            super(itemView);
            tvMaHD = (TextView) itemView.findViewById(R.id.tvMaHD);
            tvMAKHHD = (TextView) itemView.findViewById(R.id.tvMAKHHD);
            tvTongtien = (TextView) itemView.findViewById(R.id.tvThanhtien);
            tvNgayTaoHD = (TextView) itemView.findViewById(R.id.tvNgayTaoHD);
            cvHoaDons = itemView.findViewById(R.id.cvHoaDon);
        }
    }
}