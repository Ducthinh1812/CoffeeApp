package com.example.coffeeapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.coffeeapp.Model.TaiKhoan;
import com.example.coffeeapp.R;
import com.example.coffeeapp.adapter.adapterSeachKH;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ForgotpassActivity extends AppCompatActivity {
    ImageButton imgbtseach;
    TextInputEditText email;
    TextView cancel;
    RecyclerView rclTimKiem;
    ArrayList<TaiKhoan> TaiKhoanArrayList;
    adapterSeachKH adapterSeach;
    String urlTimKiem = "https://website1812.000webhostapp.com/Coffee/fogotpass.php";
    String timKiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);
        findviewid();
        TaiKhoanArrayList = new ArrayList<>();
        adapterSeach = new adapterSeachKH(getApplicationContext(), TaiKhoanArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rclTimKiem.setLayoutManager(linearLayoutManager);
        rclTimKiem.setAdapter(adapterSeach);
        //
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotpassActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        //
        imgbtseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaiKhoanArrayList.clear();
                SeachTK();
            }
        });
    }

    private void SeachTK() {
        timKiem = email.getText().toString().trim();
        if (!validateemail()) {
            return;
        } else {
            postforgotpass();
        }
    }

    private void postforgotpass() {
        final ProgressDialog progressDialog = new ProgressDialog(ForgotpassActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlTimKiem, new Response.Listener<String>() {
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
                            TaiKhoanArrayList.add(new TaiKhoan(
                                    object.getString("MaKH"),
                                    object.getString("TenKH"),
                                    object.getString("HinhAnhKH"),
                                    object.getString("DiaChi"),
                                    object.getString("NamSinh"),
                                    object.getString("SDT"),
                                    object.getString("Gmail"),
                                    object.getString("MatKhau")
                            ));
                            adapterSeach.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Không tìm thấy tài khoản của bạn.", Toast.LENGTH_SHORT).show();
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
                params.put("Gmail", timKiem);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public boolean validateemail() {
        String a = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (timKiem.equalsIgnoreCase("")) {
            email.setError("Hãy nhập gmail của bạn.");
            return false;
        } else if (!timKiem.matches(a)) {
            email.setError("Nhập đúng định dạng gmail.");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    public void findviewid() {
        imgbtseach = findViewById(R.id.imgseachfg);
        email = findViewById(R.id.edtseachfg);
        cancel = findViewById(R.id.cancelfg);
        rclTimKiem = findViewById(R.id.rclTimKiemTK);
    }
}