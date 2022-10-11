package com.example.coffeeapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.coffeeapp.Model.LoaiCafe;
import com.example.coffeeapp.Model.SanPham;
import com.example.coffeeapp.R;
import com.example.coffeeapp.adapter.adapterSanPham;
import com.example.coffeeapp.adapter.adapterTLcafe;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class listSPofTLActivity extends AppCompatActivity {
    RecyclerView rclDStop;
    ArrayList<SanPham> SanPhamArrayList;
    adapterSanPham adapterSanPham;
    int value2;
    String url = "https://website1812.000webhostapp.com/Coffee/Theloaicafe.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listspoftl);
        rclDStop = findViewById(R.id.rcllistspoftl);
        getintent();
        rclDStop.setHasFixedSize(true);
        rclDStop.setLayoutManager(new LinearLayoutManager(listSPofTLActivity.this));
        SanPhamArrayList = new ArrayList<>();
        adapterSanPham = new adapterSanPham(listSPofTLActivity.this, SanPhamArrayList);
        rclDStop.setAdapter(adapterSanPham);
        getlistTL();
    }

    public void getintent() {
        Intent intent = getIntent();
        value2 = intent.getIntExtra("Key_2", 0);
    }

    private void getlistTL() {
        final ProgressDialog progressDialog = new ProgressDialog(listSPofTLActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("status");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (result.equals("thanh cong")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            SanPhamArrayList.add(new SanPham(
                                    object.getInt("MaSP"),
                                    object.getInt("MaTL"),
                                    object.getString("HinhAnhSP"),
                                    object.getString("TenSP"),
                                    object.getInt("SoLuong"),
                                    object.getString("Size"),
                                    object.getInt("GiaBan"),
                                    object.getString("about")
                            ));
                            adapterSanPham.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Không tìm thấy sản nào phù hợp.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("MaTL", String.valueOf(value2));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}