package com.example.coffeeapp.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.coffeeapp.Model.LoaiCafe;
import com.example.coffeeapp.Model.SanPham;
import com.example.coffeeapp.R;
import com.example.coffeeapp.activity.ChiTietSanPham;
import com.example.coffeeapp.activity.ConnectActivity;
import com.example.coffeeapp.activity.HelloActivity;
import com.example.coffeeapp.activity.listSPofTLActivity;
import com.example.coffeeapp.fragment.HomeFragment;
import com.example.coffeeapp.fragment.MenuFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adapterTLcafe extends RecyclerView.Adapter<adapterTLcafe.TLcafeHolder> {

    Context context;
    LinearLayout linearTL;
    ArrayList<LoaiCafe> TheLoaicafeArrayList;
    ArrayList<SanPham> SanPhamArrayList;
    String urlTL = "https://website1812.000webhostapp.com/Coffee/Theloaicafe.php";

    public adapterTLcafe(Context context, ArrayList<LoaiCafe> TheLoaicafeArrayList) {
        this.context = context;
        this.TheLoaicafeArrayList = TheLoaicafeArrayList;
    }

    @NonNull
    @Override
    public adapterTLcafe.TLcafeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_the_loai, parent, false);
        linearTL = view.findViewById(R.id.linearTL);
        return new adapterTLcafe.TLcafeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterTLcafe.TLcafeHolder holder, @SuppressLint("RecyclerView") int position) {
        LoaiCafe timKiem = TheLoaicafeArrayList.get(position);
        holder.tvTenQA.setText(timKiem.getTenTL());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(linearTL.getContext());
                progressDialog.setMessage("Please Wait..");
                progressDialog.show();
                RequestQueue requestQueue = Volley.newRequestQueue(linearTL.getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlTL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(context, listSPofTLActivity.class);
                        intent.putExtra("Key_2", timKiem.getMaTL());
                        context.startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(linearTL.getContext(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("MaTL", timKiem.getTenTL());
                        return params;
                    }
                };

                requestQueue.add(stringRequest);
                notifyItemChanged(position);
            }
        });
        Picasso.get().load(timKiem.getHinhAnhTL())
                .into(holder.imgTL);

    }


    @Override
    public int getItemCount() {
        return TheLoaicafeArrayList.size();
    }

    public class TLcafeHolder extends RecyclerView.ViewHolder {
        ImageView imgTL;
        TextView tvTenQA;
        CardView cardView;

        public TLcafeHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.CarviewTheLoai);
            imgTL = (ImageView) itemView.findViewById(R.id.imgTL);
            tvTenQA = (TextView) itemView.findViewById(R.id.tvtenTL);
        }
    }
}
