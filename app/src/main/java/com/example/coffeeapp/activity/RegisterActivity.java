package com.example.coffeeapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.coffeeapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    ImageButton imgregister;
    TextView tvlogin;
    String cemail;
    TextInputEditText edtname, edtsdt, edtgmail, edtpass;
    String urllogin = "https://website1812.000webhostapp.com/Coffee/registerKH.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        imgregister = findViewById(R.id.Imgregister);
        tvlogin = findViewById(R.id.tvlogin);
        edtname = findViewById(R.id.edtnameDK);
        edtsdt = findViewById(R.id.edtsdtDK);
        edtgmail = findViewById(R.id.edtgmailDK);
        edtpass = findViewById(R.id.edtmatkhauDK);
        //
        imgregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cemail = edtgmail.getText().toString();

                if (!validatename() | !validatesdt() | !validateemail() | !validatepass()) {
                    return;
                } else {
                    postregister();
                }
            }
        });
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void postregister() {
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        String str_name = edtname.getText().toString().trim();
        String str_sdt = edtsdt.getText().toString().trim();
        String str_email = edtgmail.getText().toString().trim();
        String str_password = edtpass.getText().toString().trim();
        StringRequest request = new StringRequest(Request.Method.POST, urllogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.equalsIgnoreCase("Đăng kí Thành Công")) {
                    edtname.setText("");
                    edtsdt.setText("");
                    edtgmail.setText("");
                    edtpass.setText("");
                    Toast.makeText(getApplication(), response, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplication(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("TenKH", str_name);
                params.put("SDT", str_sdt);
                params.put("Gmail", str_email);
                params.put("MatKhau", str_password);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(request);
    }

    public boolean validatename() {
        if (edtname.getText().toString().equals("")) {
            edtname.setError("Hãy nhập tên của bạn.");
            return false;
        } else {
            edtname.setError(null);
            return true;
        }
    }

    public boolean validateemail() {
        String a = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (edtgmail.getText().toString().equals("")) {
            edtgmail.setError("Hãy nhập gmail của bạn.");
            return false;
        } else if (!cemail.matches(a)) {
            edtgmail.setError("Nhập đúng định dạng gmail.");
            return false;
        } else {
            edtgmail.setError(null);
            return true;
        }
    }

    public boolean validatepass() {
        if (edtpass.getText().toString().equals("")) {
            edtpass.setError("Nhập mật khẩu của bạn");
            return false;
        } else if (edtpass.length() < 6) {
            edtpass.setError("Nhập mật khẩu trên 6 kí tự.");
            return false;
        } else {
            edtpass.setError(null);
            return true;
        }
    }

    public boolean validatesdt() {
        String a = "^0[0-9]{9}$";
        if (edtsdt.getText().toString().equals("")) {
            edtsdt.setError("Nhập số điện thoại của bạn");
            return false;
        } else if (!edtsdt.getText().toString().matches(a)) {
            edtsdt.setError("Hãy nhập đúng định dạng số điện thoại!");
            return false;
        } else {
            edtsdt.setError(null);
            return true;
        }
    }
}