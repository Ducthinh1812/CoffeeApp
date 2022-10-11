package com.example.coffeeapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.coffeeapp.Model.SanPham;
import com.example.coffeeapp.Model.TaiKhoan;
import com.example.coffeeapp.R;
import com.example.coffeeapp.activity.ForgotpassActivity;
import com.example.coffeeapp.adapter.adapterSanPham;
import com.example.coffeeapp.adapter.adapterSeachKH;
import com.example.coffeeapp.adapter.adapterSeachSP;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SeachFragment extends Fragment {
    ImageButton imgbtseach;
    TextInputEditText email;
    RecyclerView rclTimKiem;
    ArrayList<SanPham> SanPhamArrayList;
    adapterSeachSP adapterseachSP;
    String urlTimKiem = "https://website1812.000webhostapp.com/Coffee/SeachSP.php";
    String timKiem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seach, container, false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgbtseach = view.findViewById(R.id.imgseachSP);
        email = view.findViewById(R.id.edtseachSP);
        rclTimKiem = view.findViewById(R.id.rclTimKiemSP);
        SanPhamArrayList = new ArrayList<>();
        adapterseachSP = new adapterSeachSP(getContext(), SanPhamArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rclTimKiem.setLayoutManager(linearLayoutManager);
        rclTimKiem.setAdapter(adapterseachSP);
        imgbtseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SanPhamArrayList.clear();
                SeachSP();
            }
        });
    }

    private void SeachSP() {
        timKiem = email.getText().toString().trim();
        if (!validate()) {
            return;
        } else {
            postSeach();
        }
    }

    private void postSeach() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                            adapterseachSP.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Không tìm thấy sản nào phù hợp.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("TenSP", timKiem);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public boolean validate() {
        if (timKiem.equalsIgnoreCase("")) {
            email.setError("Nhập để tìm kiếm.");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
}