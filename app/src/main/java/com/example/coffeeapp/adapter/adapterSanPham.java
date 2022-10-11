package com.example.coffeeapp.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.coffeeapp.fragment.HomeFragment;
import com.example.coffeeapp.fragment.SeachFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adapterSanPham extends RecyclerView.Adapter<adapterSanPham.SanPhamHolder> {

    Context context;
    ArrayList<SanPham> SanPhamArrayList;
    String urlSp = "https://website1812.000webhostapp.com/Coffee/Sanpham.php";

    public adapterSanPham(Context context, ArrayList<SanPham> SanPhamArrayList) {
        this.context = context;
        this.SanPhamArrayList = SanPhamArrayList;
    }

    @NonNull
    @Override
    public adapterSanPham.SanPhamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham, parent, false);
        return new adapterSanPham.SanPhamHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterSanPham.SanPhamHolder holder, @SuppressLint("RecyclerView") int position) {
        SanPham timKiem = SanPhamArrayList.get(position);

        holder.tvTenSP.setText(timKiem.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.GiaSP.setText("Giá: " + decimalFormat.format(timKiem.getGiaBan()) + " VND");
        Picasso.get().load(timKiem.getHinhanhSP())
                .into(holder.imgSP);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setMessage("Please Wait..");
                progressDialog.show();
                RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSp, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(context, ChiTietSanPham.class);
                        intent.putExtra("data", SanPhamArrayList.get(position));
                        context.startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(v.getContext(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("TenSP", timKiem.getTenSP());
                        return params;
                    }
                };

                requestQueue.add(stringRequest);
                notifyItemChanged(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return SanPhamArrayList.size();
    }

    public class SanPhamHolder extends RecyclerView.ViewHolder {
        ImageView imgSP;
        TextView tvTenSP, GiaSP;
        CardView cardView;

        public SanPhamHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.CarviewSP);
            imgSP = (ImageView) itemView.findViewById(R.id.imgSP);
            tvTenSP = (TextView) itemView.findViewById(R.id.tvtenSP);
            GiaSP = (TextView) itemView.findViewById(R.id.tvGiaSP);

        }
    }

}
