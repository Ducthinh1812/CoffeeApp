package com.example.coffeeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.coffeeapp.Model.HoaDonCT;
import com.example.coffeeapp.Model.LoaiCafe;
import com.example.coffeeapp.Model.TaiKhoan;
import com.example.coffeeapp.R;
import com.example.coffeeapp.adapter.adapterSanPhamHD;
import com.example.coffeeapp.fragment.AccountFragment;
import com.example.coffeeapp.fragment.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietHDActivity extends AppCompatActivity {

    String maHD;
    int maKH, thanhtien;
    ArrayList<HoaDonCT> hoaDonCTS;
    adapterSanPhamHD adaptersanphamHD;
    RecyclerView rclCTHD;
    Intent intent;
    TextView tvMaHD, tvtenKH, tvThanhtien, tvthongbao;
    ArrayList<TaiKhoan> taiKhoanArrayList;
    String urlGetCTHD = "https://website1812.000webhostapp.com/Coffee/HoadonCT.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hdactivity);
        getintent();
        //
        findviewid();
        //
        hoaDonCTS = new ArrayList<>();
        intent = getIntent();
        adaptersanphamHD = new adapterSanPhamHD(getApplicationContext(), hoaDonCTS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rclCTHD.setLayoutManager(linearLayoutManager);
        rclCTHD.setAdapter(adaptersanphamHD);
        getCTHD(urlGetCTHD);
    }

    private void getCTHD(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("status");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (result.equals("thanh cong")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            hoaDonCTS.add(new HoaDonCT(
                                    object.getInt("MaHD"),
                                    object.getInt("MaSP"),
                                    object.getInt("MaKH"),
                                    object.getString("TenSP"),
                                    object.getInt("SoLuong"),
                                    object.getInt("GiaBan"),
                                    object.getInt("TongTien")
                            ));
                            adaptersanphamHD.notifyDataSetChanged();
                        }
                        adaptersanphamHD.notifyDataSetChanged();
                        tvthongbao.setVisibility(View.INVISIBLE);
                        rclCTHD.setVisibility(View.VISIBLE);
                    } else {
                        adaptersanphamHD.notifyDataSetChanged();
                        tvthongbao.setVisibility(View.VISIBLE);
                        rclCTHD.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Sản phẩm có thể bị xóa hoặc không có trong kho!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("MaHD", maHD);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void findviewid() {
        tvMaHD = findViewById(R.id.TvMaCTHD);
        tvtenKH = findViewById(R.id.tvtenKHcthd);
        tvThanhtien = findViewById(R.id.TvThanhtoanCTHD);
        tvthongbao = findViewById(R.id.textviewthongbao1);
        tvMaHD.setText(maHD);
        tvtenKH.setText(AccountFragment.tenkh);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvThanhtien.setText(decimalFormat.format(thanhtien) + " VND");
        rclCTHD = findViewById(R.id.rcllistspoftl);
    }

    public void getintent() {
        maHD = getIntent().getStringExtra("mahoadon");
        maKH = getIntent().getIntExtra("tenkh", 0);
        thanhtien = getIntent().getIntExtra("Thanhtien", 0);
    }

}