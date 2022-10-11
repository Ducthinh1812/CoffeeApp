package com.example.coffeeapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.coffeeapp.Model.HoaDon;
import com.example.coffeeapp.Model.HoaDonCT;
import com.example.coffeeapp.Model.TaiKhoan;
import com.example.coffeeapp.R;
import com.example.coffeeapp.adapter.AdapterHoaDon;
import com.example.coffeeapp.adapter.adapterSanPham;
import com.example.coffeeapp.adapter.adapterTLcafe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HoaDonActivity extends AppCompatActivity {
    RecyclerView rclDStop;
    ArrayList<HoaDon> HoaDonArrayList;
    AdapterHoaDon adapterhoadon;
    String url = "https://website1812.000webhostapp.com/Coffee/getHoaDon.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        rclDStop = findViewById(R.id.rcllistHoaDon);
        rclDStop.setHasFixedSize(true);
        rclDStop.setLayoutManager(new LinearLayoutManager(HoaDonActivity.this));
        HoaDonArrayList = new ArrayList<>();
        adapterhoadon = new AdapterHoaDon(HoaDonActivity.this, HoaDonArrayList);
        rclDStop.setAdapter(adapterhoadon);
        getHD();
    }

    private void getHD() {
        final ProgressDialog progressDialog = new ProgressDialog(HoaDonActivity.this);
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
                            HoaDonArrayList.add(new HoaDon(
                                    object.getString("MaHD"),
                                    object.getInt("MaKH"),
                                    object.getString("Date"),
                                    object.getInt("ThanhTien")
                            ));
                            adapterhoadon.notifyDataSetChanged();
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
                SharedPreferences preferences = getApplication().getSharedPreferences("user_file", Context.MODE_PRIVATE);
                String user = preferences.getString("gmail", "");
                params.put("Gmail", user);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}