package com.example.coffeeapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.coffeeapp.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class ChangepassFragment extends AppCompatActivity {
    private TextInputEditText edtpasscu;
    private TextInputEditText edtpassmoi;
    private TextInputEditText edtnhaplaipass;
    private Button btnluuMK;
    private Button btnhuydoimk;
    String matkhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_changepass);
        edtpasscu = findViewById(R.id.edtpasscu);
        edtpassmoi = findViewById(R.id.edtpassmoi);
        edtnhaplaipass = findViewById(R.id.edtnhaplaipass);
        btnluuMK = findViewById(R.id.btnluuMK);
        btnhuydoimk = findViewById(R.id.btnhuydoimk);
        btnhuydoimk.setOnClickListener((v) -> {
            onBackPressed();
        });
        btnluuMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changepass();
            }
        });

    }

    private void changepass() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("user_file", Context.MODE_PRIVATE);
        String user = preferences.getString("gmail", "");
        matkhau = preferences.getString("matkhau", "");
        final ProgressDialog progressDialog = new ProgressDialog(ChangepassFragment.this);
        progressDialog.setMessage("Please Wait..");
        if (!validatepass() | !validatepass1()) {
            return;
        } else {
            progressDialog.show();
            String str_passcu = edtpasscu.getText().toString().trim();
            String str_passmoi = edtpassmoi.getText().toString().trim();
            String str_passnhaplai = edtnhaplaipass.getText().toString().trim();
            StringRequest request = new StringRequest(Request.Method.POST, "https://website1812.000webhostapp.com/Coffee/changepassnew.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    if (response.equalsIgnoreCase("Thay đổi mật khẩu thành công")) {
                        edtpasscu.setText("");
                        edtpassmoi.setText("");
                        edtnhaplaipass.setText("");
//
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Gmail", user);
                    params.put("passnew", str_passmoi);
                    params.put("repassnew", str_passnhaplai);
                    return params;

                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        }
    }

    public boolean validatepass() {
        if (edtpasscu.getText().toString().equals("")) {
            edtpasscu.setError("Nhập mật khẩu của bạn");
            return false;
        } else if (edtpassmoi.getText().toString().equals("")) {
            edtpassmoi.setError("Nhập mật khẩu của bạn");
            return false;
        } else if (edtnhaplaipass.getText().toString().equals("")) {
            edtnhaplaipass.setError("Nhập mật khẩu của bạn");
            return false;
        } else if (edtpassmoi.length() < 8) {
            edtpassmoi.setError("Nhập mật khẩu trên 8 kí tự.");
            return false;
        } else {
            edtpasscu.setError(null);
            edtpassmoi.setError(null);
            edtnhaplaipass.setError(null);
            return true;
        }
    }

    public boolean validatepass1() {
        if (!edtpasscu.getText().toString().equals(matkhau)) {
            edtpasscu.setError("Không trùng khớp với mật khẩu cũ.");
            return false;
        } else {
            edtpasscu.setError(null);
            return true;
        }
    }
}
