package com.example.coffeeapp.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.coffeeapp.Model.SanPham;
import com.example.coffeeapp.R;
import com.example.coffeeapp.activity.ChiTietSanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adapterquanlySanPham extends RecyclerView.Adapter<adapterquanlySanPham.SanPhamHolder> {

    Context context;
    ArrayList<SanPham> SanPhamArrayList;
    private EditText editTENSP;
    private EditText editHASP;
    private EditText editGIABAN;
    private EditText editMOTASP;
    private Button btnHuyUpdate;
    private Button btnLuuUpdate;
    String tensp, hinhanhsp, giaban, mota, masp;
    String urlSp = "https://website1812.000webhostapp.com/Coffee/Sanpham.php";

    public adapterquanlySanPham(Context context, ArrayList<SanPham> SanPhamArrayList) {
        this.context = context;
        this.SanPhamArrayList = SanPhamArrayList;
    }

    @NonNull
    @Override
    public adapterquanlySanPham.SanPhamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quan_ly_san_pham, parent, false);
        return new adapterquanlySanPham.SanPhamHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterquanlySanPham.SanPhamHolder holder, @SuppressLint("RecyclerView") int position) {
        SanPham timKiem = SanPhamArrayList.get(position);

        holder.tvTenSP.setText(timKiem.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.GiaSP.setText("Giá: " + decimalFormat.format(timKiem.getGiaBan()) + " VND");
        Picasso.get().load(timKiem.getHinhanhSP())
                .into(holder.imgSP);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setMessage("Please Wait..");
                progressDialog.show();
                RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSp, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(v.getContext(), ChiTietSanPham.class);
                        intent.putExtra("data", SanPhamArrayList.get(position));
                        v.getContext().startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(v.getContext(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("TenSP", timKiem.getTenSP());
                        return params;
                    }
                };

                requestQueue.add(stringRequest);
                notifyItemChanged(position);
            }
        });
        holder.xoasp.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Delete");
            builder.setMessage("Bạn có muốn xóa " + timKiem.getTenSP() + " không?");
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://website1812.000webhostapp.com/Coffee/deleteSP.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject object = null;
                            try {
                                object = new JSONObject(response);
                                String check = object.getString("state");
                                if (check.equals("delete")) {
                                    delete(position);
                                    Toast.makeText(v.getContext(), "Xóa Thành Công.", Toast.LENGTH_SHORT).show();
                                } else if (check.equals("delete1")) {
                                    delete(position);
                                    Toast.makeText(v.getContext(), "Xóa Thành Công.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(v.getContext(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> deHashMap = new HashMap<>();
                            deHashMap.put("MaSP", String.valueOf(timKiem.getMaSP()));
                            return deHashMap;
                        }
                    };
                    RequestQueue requestQue = Volley.newRequestQueue(v.getContext());
                    requestQue.add(stringRequest);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        holder.suasp.setOnClickListener(v -> {
            View edit = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_sua_sp, null);
            editTENSP = edit.findViewById(R.id.editTENSP);
            editHASP = edit.findViewById(R.id.editHASP);
            editGIABAN = edit.findViewById(R.id.editGIABAN);
            editMOTASP = edit.findViewById(R.id.editMOTASP);
            btnHuyUpdate = edit.findViewById(R.id.btnHuyUpdate);
            btnLuuUpdate = edit.findViewById(R.id.btnLuuUpdate);
            editTENSP.setText(timKiem.getTenSP());
            editHASP.setText(timKiem.getHinhanhSP());
            editGIABAN.setText(String.valueOf(timKiem.getGiaBan()));
            editMOTASP.setText(timKiem.getAbout());
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Sửa Thông tin Sản Phẩm " + timKiem.getTenSP());
            builder.setView(edit);
            AlertDialog dialo = builder.create();
            btnLuuUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                    progressDialog.setMessage("Please Wait..");
                    tensp = editTENSP.getText().toString();
                    hinhanhsp = editHASP.getText().toString();
                    giaban = editGIABAN.getText().toString();
                    mota = editMOTASP.getText().toString();
                    masp = String.valueOf(timKiem.getMaSP());
                    if (!validateten() | !validateso() | !validatehinhanh() | !validatemota()) {
                        return;
                    } else {
                        progressDialog.show();
                        StringRequest request = new StringRequest(Request.Method.POST, "https://website1812.000webhostapp.com/Coffee/suasanpham.php", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                Toast.makeText(v.getContext(), response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(v.getContext(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("MaSP", masp);
                                params.put("TenSP", tensp);
                                params.put("HinhAnhSP", hinhanhsp);
                                params.put("GiaBan", giaban);
                                params.put("about", mota);
                                return params;

                            }
                        };
                        RequestQueue requestQue = Volley.newRequestQueue(v.getContext());
                        requestQue.add(request);
                    }
                }
            });
            btnHuyUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialo.dismiss();
                }
            });
            dialo.show();
        });
    }


    @Override
    public int getItemCount() {
        return SanPhamArrayList.size();
    }

    public class SanPhamHolder extends RecyclerView.ViewHolder {
        ImageView imgSP, xoasp, suasp;
        TextView tvTenSP, GiaSP;
        LinearLayout cardView;
        LinearLayout suaxoa;

        public SanPhamHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.CarviewqlSP);
            imgSP = (ImageView) itemView.findViewById(R.id.imgqlSP);
            tvTenSP = (TextView) itemView.findViewById(R.id.tvtenqlSP);
            GiaSP = (TextView) itemView.findViewById(R.id.tvGiaqlSP);
            xoasp = itemView.findViewById(R.id.imgXoaSP);
            suasp = itemView.findViewById(R.id.imgSuaSP);
            suaxoa = itemView.findViewById(R.id.suaxoa);
        }
    }

    public void delete(int item) {
        SanPhamArrayList.remove(item);
        notifyItemRemoved(item);
    }

    private void updatesp() {

    }

    public boolean validateten() {
        if (tensp.isEmpty()) {
            editTENSP.setError("Không được bỏ trống.");
            return false;
        } else {
            editTENSP.setError(null);
            return true;
        }
    }

    public boolean validatehinhanh() {
        if (hinhanhsp.isEmpty()) {
            editHASP.setError("Không được bỏ trống.");
            return false;
        } else {
            editHASP.setError(null);
            return true;
        }
    }

    public boolean validateso() {
        if (giaban.isEmpty()) {
            editGIABAN.setError("Không được bỏ trống.");
            return false;
        } else {
            editGIABAN.setError(null);
            return true;
        }
    }

    public boolean validatemota() {
        if (mota.isEmpty()) {
            editMOTASP.setError("Không được bỏ trống.");
            return false;
        } else {
            editMOTASP.setError(null);
            return true;
        }
    }
}
