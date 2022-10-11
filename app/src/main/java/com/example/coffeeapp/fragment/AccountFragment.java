package com.example.coffeeapp.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.coffeeapp.MainActivity;
import com.example.coffeeapp.Model.TaiKhoan;
import com.example.coffeeapp.R;
import com.example.coffeeapp.activity.ChiTietHDActivity;
import com.example.coffeeapp.activity.HoaDonActivity;
import com.example.coffeeapp.activity.LienheActivity;
import com.example.coffeeapp.activity.PersonalActivity;
import com.example.coffeeapp.activity.QuanlySPActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountFragment extends Fragment {
    LinearLayout logout, linneaxemHD, lineaperson, lienhe, lineaquanlysp, lineachangepass;
    TextView hoten;
    ArrayList<TaiKhoan> TaiKhoanArrayList;
    TaiKhoan kh;
    ImageView anh;
    public static String tenkh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logout = view.findViewById(R.id.logout);
        hoten = view.findViewById(R.id.tvTenDN);
        lineaquanlysp = view.findViewById(R.id.linquanlyspp);
        lienhe = view.findViewById(R.id.lienhe);
        linneaxemHD = view.findViewById(R.id.hoadon2);
        anh = view.findViewById(R.id.imgavata);
        lineaperson = view.findViewById(R.id.lineapersonal);
        //
        setTenKH();
        capquyen();
        lineaquanlysp.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), QuanlySPActivity.class);
            startActivity(intent);
        });
        lineachangepass = view.findViewById(R.id.changepass);
        lineachangepass.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangepassFragment.class);
            startActivity(intent);
        });
        lineaperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonalActivity.class);
//                intent.putExtra("MaKH",kh.getMaKH());
//                intent.putExtra("TenKH",kh.getTenKH());
//                intent.putExtra("DiaChi",kh.getDiaChi());
//                intent.putExtra("NamSinh",kh.getNamSinh());
//                intent.putExtra("SDT",kh.getSDT());
//                intent.putExtra("Gmail",kh.getGmail());
                startActivity(intent);
            }
        });
        linneaxemHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HoaDonActivity.class);
                startActivity(intent);
            }
        });
        lienhe.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LienheActivity.class);
            startActivity(intent);
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Đăng Xuất.");
                builder.setMessage("Bạn Có Muốn Thoát Ứng Dụng Không ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    private void capquyen() {
        SharedPreferences preferences = getActivity().getSharedPreferences("user_file", Context.MODE_PRIVATE);
        String user = preferences.getString("gmail", "");
        StringRequest request = new StringRequest(Request.Method.POST, "https://website1812.000webhostapp.com/Coffee/capquyenapp.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Xin chào Admin")) {
                    lineaquanlysp.setVisibility(View.VISIBLE);

                } else {
                    lineaquanlysp.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Gmail", user);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    private void setTenKH() {
        SharedPreferences preferences = getActivity().getSharedPreferences("user_file", Context.MODE_PRIVATE);
        String user = preferences.getString("gmail", "");
        String matkhau = preferences.getString("matkhau", "");
        StringRequest request = new StringRequest(Request.Method.POST, "https://website1812.000webhostapp.com/Coffee/gettaikhoan.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    TaiKhoanArrayList = new ArrayList<>();
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
                            kh = TaiKhoanArrayList.get(0);
                            Log.e("kkkkkkkk", kh.getGmail());
                            hoten.setText(kh.getTenKH());
                            Picasso.get().load(kh.getHinhAnhKH())
                                    .into(anh);
//                            Picasso.get().load(kh.getHinhAnhKH())
//                                    .into(MainActivity.imginfor);
                            tenkh = kh.getTenKH();
                        }
                    } else {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Gmail", user);
                params.put("MatKhau", matkhau);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
}