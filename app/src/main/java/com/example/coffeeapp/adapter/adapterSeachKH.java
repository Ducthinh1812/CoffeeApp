package com.example.coffeeapp.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import com.example.coffeeapp.activity.ForgotpassActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adapterSeachKH extends RecyclerView.Adapter<adapterSeachKH.SeachKHHolder> {

    String str_passnew, str_repassnew;
    Context context;
    ArrayList<TaiKhoan> TaiKhoanArrayList;
    private TextInputEditText edtpassnew, edtrepassnew;
    private Button btnHuyUpdate, btnLuuUpdate;

    public adapterSeachKH(Context context, ArrayList<TaiKhoan> TaiKhoanArrayList) {
        this.context = context;
        this.TaiKhoanArrayList = TaiKhoanArrayList;
    }

    @NonNull
    @Override
    public SeachKHHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tai_khoan, parent, false);
        return new SeachKHHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterSeachKH.SeachKHHolder holder, @SuppressLint("RecyclerView") int position) {
        TaiKhoan timKiem = TaiKhoanArrayList.get(position);

        holder.tvTenQA.setText(timKiem.getTenKH());
        Picasso.get().load(timKiem.getHinhAnhKH())
                .into(holder.imgQA);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View viewT = LayoutInflater.from(builder.getContext()).inflate(R.layout.dialog_doipass, null);
                builder.setView(viewT);
                Dialog dialog = builder.create();
                dialog.show();
                edtpassnew = viewT.findViewById(R.id.edtpassnew);
                edtrepassnew = viewT.findViewById(R.id.edtrepassnew);
                btnHuyUpdate = viewT.findViewById(R.id.btnHuyUpdate);
                btnLuuUpdate = viewT.findViewById(R.id.btnLuuUpdate);
                //
                btnHuyUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //
                btnLuuUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        str_passnew = edtpassnew.getText().toString().trim();
                        str_repassnew = edtrepassnew.getText().toString().trim();
                        String urlUpdateSp = "https://website1812.000webhostapp.com/Coffee/changepassnew.php";

                        if (!validatepass()) {
                            return;
                        } else {
                            final ProgressDialog progressDialog = new ProgressDialog(builder.getContext());
                            progressDialog.setMessage("Please Wait..");
                            progressDialog.show();
                            RequestQueue requestQueue = Volley.newRequestQueue(builder.getContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateSp, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    if (response.equalsIgnoreCase("Thay đổi mật khẩu thành công")) {
                                        Toast.makeText(builder.getContext(), response, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(builder.getContext(), response, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    Toast.makeText(builder.getContext(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("Gmail", timKiem.getGmail());
                                    params.put("passnew", str_passnew);
                                    params.put("repassnew", str_repassnew);
                                    return params;
                                }
                            };

                            requestQueue.add(stringRequest);
                            notifyItemChanged(position);
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

    }


    @Override
    public int getItemCount() {
        return TaiKhoanArrayList.size();
    }

    public class SeachKHHolder extends RecyclerView.ViewHolder {
        ImageView imgQA;
        TextView tvTenQA;
        CardView cardView;

        public SeachKHHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.CarviewTaikhoan);
            imgQA = (ImageView) itemView.findViewById(R.id.imgQA);
            tvTenQA = (TextView) itemView.findViewById(R.id.tvTenTK);
        }
    }

    public boolean validatepass() {
        if (str_passnew.equalsIgnoreCase("")) {
            edtpassnew.setError("Nhập mật khẩu của bạn");
            return false;
        } else if (str_repassnew.equalsIgnoreCase("")) {
            edtrepassnew.setError("Nhập mật khẩu của bạn");
            return false;
        } else if (str_passnew.length() < 6) {
            edtpassnew.setError("Nhập mật khẩu trên 6 kí tự.");
            return false;
        } else if (str_repassnew.length() < 6) {
            edtrepassnew.setError("Nhập mật khẩu trên 6 kí tự.");
            return false;
        } else {
            edtpassnew.setError(null);
            edtrepassnew.setError(null);
            return true;
        }
    }
}