package com.example.coffeeapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.coffeeapp.Model.SanPham;
import com.example.coffeeapp.R;
import com.example.coffeeapp.adapter.adapterSanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    RecyclerView rclDStop;
    ArrayList<SanPham> FavoriteArrayList;
    adapterSanPham adapterHoaDon;
    String url = "https://website1812.000webhostapp.com/Coffee/TopFavorite.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rclDStop = view.findViewById(R.id.rclfavorite);
        rclDStop.setHasFixedSize(true);
        rclDStop.setLayoutManager(new LinearLayoutManager(getContext()));
        FavoriteArrayList = new ArrayList<>();
        adapterHoaDon = new adapterSanPham(getActivity(), FavoriteArrayList);
        rclDStop.setAdapter(adapterHoaDon);
        getPK();
    }

    private void getPK() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                        adapterHoaDon.notifyDataSetChanged();
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