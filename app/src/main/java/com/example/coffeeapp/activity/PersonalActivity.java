package com.example.coffeeapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.coffeeapp.Model.TaiKhoan;
import com.example.coffeeapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PersonalActivity extends AppCompatActivity {
    private TextView edtMangdung;
    private TextView edtEmailngdung;
    private TextView edtTenngdung;
    private TextView edtSdtngdung;
    private TextView edtDiachingd;
    private TextView edtnamsinhnd;
    ImageView imageView2;
    ArrayList<TaiKhoan> listKH;
    Button bntsua, btnxoa, btnluu;
    String Tenkh, Mail, Sdt, Diachi, NamSinh, mailcu, Hinhanh;
    EditText tenkh, mail, sdt, diachi, namsinh, hinhanh;
    TaiKhoan nv;
    String urlsuakh = "https://website1812.000webhostapp.com/Coffee/updatekh.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        edtMangdung = findViewById(R.id.edtMangdung);
        edtEmailngdung = findViewById(R.id.edtEmailngdung);
        edtTenngdung = findViewById(R.id.edtTenngdung);
        edtSdtngdung = findViewById(R.id.edtSdtngdung);
        edtDiachingd = findViewById(R.id.edtDiachingd);
        edtnamsinhnd = findViewById(R.id.edtnamsinhnd);
        imageView2 = findViewById(R.id.imageView2);
        bntsua = findViewById(R.id.btnsuatt);
        getperson();


        bntsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nv = listKH.get(0);
                View edit = LayoutInflater.from(PersonalActivity.this).inflate(R.layout.layout_suakh, null);
                mail = edit.findViewById(R.id.edtGmailKH);
                namsinh = edit.findViewById(R.id.NamsinhKH);
                sdt = edit.findViewById(R.id.edtSDTKH);
                tenkh = edit.findViewById(R.id.edtTenKH);
                diachi = edit.findViewById(R.id.edtDiachiKH);
                btnxoa = edit.findViewById(R.id.huythongtinkh);
                btnluu = edit.findViewById(R.id.suathongtinkh);
                hinhanh = edit.findViewById(R.id.edtlinkanh);
                //
                mail.setText(nv.getGmail());
                namsinh.setText(nv.getNamSinh());
                tenkh.setText(nv.getTenKH());
                sdt.setText(nv.getSDT());
                diachi.setText(nv.getDiaChi());
                hinhanh.setText(nv.getHinhAnhKH());
                //
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalActivity.this);
                builder.setTitle("Sửa Thông tin khách hàng " + nv.getTenKH());
                builder.setView(edit);
                AlertDialog dialo = builder.create();
                btnluu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateperson();
                        dialo.dismiss();
                    }
                });
                btnxoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialo.dismiss();
                    }
                });
                dialo.show();
            }
        });
    }

    private void getperson() {
        SharedPreferences preferences = PersonalActivity.this.getSharedPreferences("user_file", Context.MODE_PRIVATE);
        String user = preferences.getString("gmail", "");
        StringRequest request = new StringRequest(Request.Method.POST, "https://website1812.000webhostapp.com/Coffee/gettaikhoan.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    listKH = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("status");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (result.equals("thanh cong")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            listKH.add(new TaiKhoan(
                                    object.getString("MaKH"),
                                    object.getString("TenKH"),
                                    object.getString("HinhAnhKH"),
                                    object.getString("DiaChi"),
                                    object.getString("NamSinh"),
                                    object.getString("SDT"),
                                    object.getString("Gmail"),
                                    object.getString("MatKhau")

                            ));
                            TaiKhoan nv = listKH.get(0);
                            edtMangdung.setText(nv.getMaKH());
                            edtEmailngdung.setText(nv.getGmail());
                            edtTenngdung.setText(nv.getTenKH());
                            edtSdtngdung.setText(nv.getSDT());
                            edtDiachingd.setText(nv.getDiaChi());
                            edtnamsinhnd.setText(nv.getNamSinh());
                            Picasso.get().load(nv.getHinhAnhKH())
                                    .into(imageView2);
                        }
                    } else {
                        Toast.makeText(PersonalActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PersonalActivity.this, "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Gmail", user);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(PersonalActivity.this);
        requestQueue.add(request);
    }

    private void updateperson() {
        final ProgressDialog progressDialog = new ProgressDialog(PersonalActivity.this);
        progressDialog.setMessage("Please Wait..");
        Mail = mail.getText().toString();
        NamSinh = namsinh.getText().toString();
        Tenkh = tenkh.getText().toString();
        Sdt = sdt.getText().toString();
        Diachi = diachi.getText().toString();
        Hinhanh = hinhanh.getText().toString();
        mailcu = nv.getGmail();
        if (!validatename() | !validateemail() | !validatesdt() | !validadiachi() | !validahinhanh() | !validaNamSinh()) {
            return;
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, urlsuakh, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Toast.makeText(PersonalActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(PersonalActivity.this, "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Gmail", Mail);
                    params.put("TenKH", Tenkh);
                    params.put("DiaChi", Diachi);
                    params.put("HinhAnhKH", Hinhanh);
                    params.put("SDT", Sdt);
                    params.put("NamSinh", NamSinh);
                    params.put("mailcu", mailcu);
                    return params;

                }
            };
            RequestQueue requestQue = Volley.newRequestQueue(PersonalActivity.this);
            requestQue.add(request);
        }
    }

    public boolean validatename() {
        if (tenkh.getText().toString().equals("")) {
            tenkh.setError("Hãy nhập tên của bạn.");
            return false;
        } else {
            tenkh.setError(null);
            return true;
        }
    }

    public boolean validateemail() {
        String a = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (mail.getText().toString().equals("")) {
            mail.setError("Hãy nhập gmail của bạn.");
            return false;
        } else if (!Mail.matches(a)) {
            mail.setError("Nhập đúng định dạng gmail.");
            return false;
        } else {
            mail.setError(null);
            return true;
        }
    }

    public boolean validatesdt() {
        String a = "^0[0-9]{9}$";
        if (sdt.getText().toString().equals("")) {
            sdt.setError("Nhập số điện thoại của bạn");
            return false;
        } else if (!sdt.getText().toString().matches(a)) {
            sdt.setError("Hãy nhập đúng định dạng số điện thoại!");
            return false;
        } else {
            sdt.setError(null);
            return true;
        }
    }

    public boolean validadiachi() {
        if (diachi.getText().toString().equals("")) {
            diachi.setError("Hãy nhập địa chỉ của bạn.");
            return false;
        } else {
            diachi.setError(null);
            return true;
        }
    }

    public boolean validaNamSinh() {
        if (namsinh.getText().toString().equals("")) {
            namsinh.setError("Hãy nhập địa chỉ của bạn.");
            return false;
        } else {
            namsinh.setError(null);
            return true;
        }
    }

    public boolean validahinhanh() {
        if (hinhanh.getText().toString().equals("")) {
            hinhanh.setError("Hãy nhập Link ảnh của bạn.");
            return false;
        } else {
            hinhanh.setError(null);
            return true;
        }
    }
}