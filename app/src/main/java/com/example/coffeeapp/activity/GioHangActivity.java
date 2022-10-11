package com.example.coffeeapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.coffeeapp.MainActivity;
import com.example.coffeeapp.Model.GioHang;
import com.example.coffeeapp.Model.SanPham;
import com.example.coffeeapp.R;
import com.example.coffeeapp.adapter.AdapterGioHang;
import com.example.coffeeapp.fragment.HomeFragment;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GioHangActivity extends AppCompatActivity {

    String user;
    Toolbar toolbar;
    ListView listView;
    TextView txtThongbao;
    static long tongtien = 0;
    static TextView txtTongTien;
    Button btnthanhtoan, btntieptucmuahang;
    static AdapterGioHang adapterGioHang;
    String ngayHT = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Anhxa();
        CheckData();
        ClickItemListview();
        ClickButton();
        UpdateTongTien();
    }

    void Anhxa() {
        listView = findViewById(R.id.listviewgiohang);
        txtThongbao = findViewById(R.id.textviewthongbao);
        txtTongTien = findViewById(R.id.textviewtongtien);
        btnthanhtoan = findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmuahang = findViewById(R.id.buttontieptucmuahang);
        adapterGioHang = new AdapterGioHang(GioHangActivity.this, HomeFragment.giohanglist);
        listView.setAdapter(adapterGioHang);
    }

    public static void loadlistview() {
        adapterGioHang.notifyDataSetChanged();
    }

    void CheckData() {
        if (HomeFragment.giohanglist.size() <= 0) {
            adapterGioHang.notifyDataSetChanged();
            txtThongbao.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        } else {
            adapterGioHang.notifyDataSetChanged();
            txtThongbao.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
    }

    void ClickItemListview() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (HomeFragment.giohanglist.size() < 1) {
                    txtThongbao.setVisibility(View.VISIBLE);
                } else {
                    HomeFragment.giohanglist.remove(i);
                    adapterGioHang.notifyDataSetChanged();
                    UpdateTongTien();
                    if (HomeFragment.giohanglist.size() < 1) {
                        txtThongbao.setVisibility(View.VISIBLE);
                    } else {
                        txtThongbao.setVisibility(View.INVISIBLE);
                        adapterGioHang.notifyDataSetChanged();
                        UpdateTongTien();
                    }
                }
            }
        });
    }

    public static void UpdateTongTien() {
        tongtien = 0;
        for (int i = 0; i < HomeFragment.giohanglist.size(); i++) {
            tongtien += HomeFragment.giohanglist.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(tongtien) + " VND");
    }

    void ClickButton() {
        btntieptucmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int number = random.nextInt(1000000000);
                ngayHT = number + "";

                if (HomeFragment.giohanglist.size() > 0) {
                    addHD();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < HomeFragment.giohanglist.size(); i++) {
                                addCTHD(i);
                            }
                        }
                    }, 5000);

                    Toast.makeText(GioHangActivity.this, "Vui lòng chờ trong giây lát ....", Toast.LENGTH_LONG).show();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtThongbao.setVisibility(View.VISIBLE);
                            HomeFragment.giohanglist.clear();
                            UpdateTongTien();
                            adapterGioHang.notifyDataSetChanged();
                            Toast.makeText(GioHangActivity.this, "Hóa đơn của bạn đã được xử lý!", Toast.LENGTH_SHORT).show();
                        }
                    }, 7000);

                } else {
                    Toast.makeText(GioHangActivity.this, "Giỏ hàng không có sản phầm nào!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addHD() {
        String urlDH = "https://website1812.000webhostapp.com/Coffee/insertHD.php";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(Calendar.getInstance().getTime());
        final ProgressDialog progressDialog = new ProgressDialog(GioHangActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, urlDH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    progressDialog.dismiss();
                    Toast.makeText(GioHangActivity.this, "thanh toán thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplication(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences preferences = getApplication().getSharedPreferences("user_file", Context.MODE_PRIVATE);
                user = preferences.getString("gmail", "");
                params.put("Gmail", user);
                params.put("MaHD", ngayHT);
                params.put("Date", date);
                params.put("ThanhTien", String.valueOf(tongtien));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(request);
    }

    private void addCTHD(int i) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://website1812.000webhostapp.com/Coffee/insertCTHD.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")) {
                            Toast.makeText(GioHangActivity.this, "Xảy ra lỗi khi thanh toán!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GioHangActivity.this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                GioHang gioHang = HomeFragment.giohanglist.get(i);
                Map<String, String> params = new HashMap<>();
                SharedPreferences preferences = getApplication().getSharedPreferences("user_file", Context.MODE_PRIVATE);
                user = preferences.getString("gmail", "");
                params.put("Gmail", user);
                params.put("MaHD", ngayHT);
                params.put("MaSP", String.valueOf(gioHang.getIdsp()));
                params.put("TenSP", String.valueOf(gioHang.getTensp()));
                params.put("HinhAnhSP", String.valueOf(gioHang.getHinhsp()));
                params.put("TongTien", String.valueOf(gioHang.getGiasp()));
                params.put("SoLuong", String.valueOf(gioHang.getSoluongsp()));

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}