package com.example.coffeeapp.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.coffeeapp.Model.HoaDonCT;
import com.example.coffeeapp.Model.SanPham;
import com.example.coffeeapp.R;
import com.example.coffeeapp.activity.ChiTietSanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adapterSanPhamHD extends RecyclerView.Adapter<adapterSanPhamHD.SanPhamHolder> {

    Context context;
    ArrayList<HoaDonCT> HoaDonCTArrayList;

    public adapterSanPhamHD(Context context, ArrayList<HoaDonCT> HoaDonCTArrayList) {
        this.context = context;
        this.HoaDonCTArrayList = HoaDonCTArrayList;
    }

    @NonNull
    @Override
    public adapterSanPhamHD.SanPhamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham_hd, parent, false);
        return new adapterSanPhamHD.SanPhamHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterSanPhamHD.SanPhamHolder holder, @SuppressLint("RecyclerView") int position) {
        HoaDonCT timKiem = HoaDonCTArrayList.get(position);

        holder.tvTenSP.setText(timKiem.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.GiaSP.setText(decimalFormat.format(timKiem.getGiaBan()) + " VND");
        holder.tvsoluong.setText(timKiem.getSoLuong() + "");
        holder.tvthanhtienCTHD.setText(decimalFormat.format(timKiem.getTongTien()) + " VND");
    }


    @Override
    public int getItemCount() {
        return HoaDonCTArrayList.size();
    }

    public class SanPhamHolder extends RecyclerView.ViewHolder {
        TextView tvTenSP, GiaSP, tvsoluong, tvthanhtienCTHD;

        public SanPhamHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSP = (TextView) itemView.findViewById(R.id.tvtenTL);
            GiaSP = (TextView) itemView.findViewById(R.id.tvGiaSPHD);
            tvsoluong = (TextView) itemView.findViewById(R.id.tvSoluongSPHD);
            tvthanhtienCTHD = (TextView) itemView.findViewById(R.id.tvthanhtienCTHD);
        }
    }
}
