package com.example.coffeeapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.coffeeapp.Model.GioHang;
import com.example.coffeeapp.Model.LoaiCafe;
import com.example.coffeeapp.Model.SanPham;
import com.example.coffeeapp.R;
import com.example.coffeeapp.adapter.adapterSanPham;
import com.example.coffeeapp.adapter.adapterSanPhamHome;
import com.example.coffeeapp.adapter.adapterTLcafe;
import com.example.coffeeapp.adapter.adapterTLcafe2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public static ArrayList<GioHang> giohanglist;
    ArrayList<SanPham> sanPhamArrayList;
    RecyclerView rclSP, rclmenu;
    ArrayList<LoaiCafe> TheloaiArrayList;
    adapterTLcafe2 adapterTL;
    adapterSanPhamHome adapterSanPhams;
    TextView viewall;
    String url = "https://website1812.000webhostapp.com/Coffee/getTheLoai.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rclmenu = view.findViewById(R.id.rclviewmenu);
        //menu
        rclmenu.setHasFixedSize(true);
        rclmenu.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        TheloaiArrayList = new ArrayList<>();
        adapterTL = new adapterTLcafe2(getActivity(), TheloaiArrayList);
        rclmenu.setAdapter(adapterTL);
        viewall = view.findViewById(R.id.tvViewall);
        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                MenuFragment llf = new MenuFragment();
                ft.replace(R.id.nav_host_fragment_activity_main, llf);
                ft.commit();
            }
        });
        getMenu();
        //sanpham
        sanPhamArrayList = new ArrayList<>();
        adapterSanPhams = new adapterSanPhamHome(getActivity(), sanPhamArrayList);
        rclSP = view.findViewById(R.id.rclviewSPselle);

        getSP("https://website1812.000webhostapp.com/Coffee/getsanpham.php");
        rclSP.setHasFixedSize(true);
        rclSP.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rclSP.setAdapter(adapterSanPhams);


        if (giohanglist != null) {

        } else {
            giohanglist = new ArrayList<>();
        }
    }

    void getSP(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                sanPhamArrayList.add(new SanPham(
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
                        adapterSanPhams.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getMenu() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                TheloaiArrayList.add(new LoaiCafe(
                                        object.getInt("MaTL"),
                                        object.getString("HinhAnhTL"),
                                        object.getString("TenTL")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapterTL.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Lỗi !", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}