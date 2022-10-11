package com.example.coffeeapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.coffeeapp.Model.SanPham;
import com.example.coffeeapp.R;
import com.example.coffeeapp.adapter.adapterSanPham;
import com.example.coffeeapp.adapter.adapterquanlySanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuanlySPActivity extends AppCompatActivity {
    EditText editTENSPT;
    EditText editSOLUONGNHAPT;
    EditText editHASPT;
    EditText editGIABANT;
    EditText editMOTASPT;
    EditText editMaLoaiT;
    EditText editSizesp;

    Button btnHuyThem;
    Button btnLuuThem;

    RecyclerView rclDsp;
    ArrayList<SanPham> FavoriteArrayList;
    adapterquanlySanPham adapterSanPham;
    FloatingActionButton fab;
    String url = "https://website1812.000webhostapp.com/Coffee/getsanpham.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_spactivity);
        rclDsp = findViewById(R.id.listsp);
        fab = findViewById(R.id.floatingActionButton);
        rclDsp.setHasFixedSize(true);
        rclDsp.setLayoutManager(new LinearLayoutManager(QuanlySPActivity.this));
        FavoriteArrayList = new ArrayList<>();
        adapterSanPham = new adapterquanlySanPham(QuanlySPActivity.this, FavoriteArrayList);
        rclDsp.setAdapter(adapterSanPham);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themSP();
            }
        });
        getPK();
    }

    private void getPK() {
        RequestQueue requestQueue = Volley.newRequestQueue(QuanlySPActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                FavoriteArrayList.add(new SanPham(
                                        object.getInt("MaSP"),
                                        object.getInt("MaTL"),
                                        object.getString("HinhAnhSP"),
                                        object.getString("TenSP"),
                                        object.getInt("SoLuong"),
                                        object.getString("Size"),
                                        object.getInt("GiaBan"),
                                        object.getString("about")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapterSanPham.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QuanlySPActivity.this, "Lỗi !", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void themSP() {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuanlySPActivity.this);
        View viewT = LayoutInflater.from(builder.getContext()).inflate(R.layout.dialog_them_sp, null);
        builder.setView(viewT);
        Dialog dialog = builder.create();
        dialog.show();


        editTENSPT = (EditText) viewT.findViewById(R.id.editTENSPT);
        editSOLUONGNHAPT = (EditText) viewT.findViewById(R.id.editSOLUONGNHAPT);
        editHASPT = (EditText) viewT.findViewById(R.id.editHASPT);
        editGIABANT = (EditText) viewT.findViewById(R.id.editGIABANT);
        editMOTASPT = (EditText) viewT.findViewById(R.id.editMOTASPT);
        editMaLoaiT = (EditText) viewT.findViewById(R.id.editMaLoaiT);
        btnHuyThem = (Button) viewT.findViewById(R.id.btnHuyThem);
        btnLuuThem = (Button) viewT.findViewById(R.id.btnLuuThem);
        editSizesp = viewT.findViewById(R.id.editSizesp);

        btnLuuThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_tensp = editTENSPT.getText().toString().trim();
                String str_soluongnhap = editSOLUONGNHAPT.getText().toString().trim();
                String str_hinhanhsp = editHASPT.getText().toString().trim();
                String str_motasp = editMOTASPT.getText().toString().trim();
                String str_sizesp = editSizesp.getText().toString().trim();
                String str_giaban = editGIABANT.getText().toString().trim();
                String str_matl = editMaLoaiT.getText().toString().trim();
                String urlUpdateSp = "https://website1812.000webhostapp.com/Coffee/themsp.php";
                if (str_tensp.equalsIgnoreCase("") || str_soluongnhap.equalsIgnoreCase("")
                        || str_hinhanhsp.equalsIgnoreCase("") || str_motasp.equalsIgnoreCase("")
                        || str_giaban.equalsIgnoreCase("")) {
                    Toast.makeText(builder.getContext(), "Hãy nhập hết tất cả các trường trên form!", Toast.LENGTH_SHORT).show();
                }

                RequestQueue requestQueue = Volley.newRequestQueue(builder.getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateSp, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("Thêm Thành Công")) {
                            Toast.makeText(builder.getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(builder.getContext(), " Thêm không Thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(builder.getContext(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("TenSP", str_tensp);
                        params.put("HinhAnhSP", str_hinhanhsp);
                        params.put("about", str_motasp);
                        params.put("SoLuong", str_soluongnhap);
                        params.put("GiaBan", str_giaban);
                        params.put("MaTL", str_matl);
                        params.put("Size", str_sizesp);
                        return params;
                    }
                };

                requestQueue.add(stringRequest);
                dialog.dismiss();
            }
        });

        btnHuyThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}