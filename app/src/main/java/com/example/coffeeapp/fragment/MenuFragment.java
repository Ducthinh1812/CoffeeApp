package com.example.coffeeapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.example.coffeeapp.Model.LoaiCafe;
import com.example.coffeeapp.Model.TaiKhoan;
import com.example.coffeeapp.R;
import com.example.coffeeapp.adapter.adapterSeachKH;
import com.example.coffeeapp.adapter.adapterTLcafe;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuFragment extends Fragment {
    RecyclerView rclDSTL;
    ArrayList<LoaiCafe> TheloaiArrayList;
    adapterTLcafe adapterTL;
    String url = "https://website1812.000webhostapp.com/Coffee/getTheLoai.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rclDSTL = view.findViewById(R.id.rclviewTL);
        rclDSTL.setHasFixedSize(true);
        rclDSTL.setLayoutManager(new LinearLayoutManager(getContext()));
        TheloaiArrayList = new ArrayList<>();
        adapterTL = new adapterTLcafe(getActivity(), TheloaiArrayList);
        rclDSTL.setAdapter(adapterTL);
        getPK();
    }

    private void getPK() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Log.e("aaaaaaa", String.valueOf(response));
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