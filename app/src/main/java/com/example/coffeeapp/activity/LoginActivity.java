package com.example.coffeeapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
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
import com.example.coffeeapp.MainActivity;
import com.example.coffeeapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    ImageButton imglogin;
    CheckBox checkBox;
    TextView tvregister, tvforgot;
    TextInputEditText edtGmail, edtMatKhau;
    String str_email, str_password, names;
    String urllogin = "https://website1812.000webhostapp.com/Coffee/Login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findviewid();
        //
        getpreferences();
        //
        ClickButtonID();
    }

    public void ClickButtonID() {
        imglogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                names = edtGmail.getText().toString();
                if (!validateemail() | !validatepass()) {
                    return;
                } else {
                    postlogin();
                }
            }
        });
        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        tvforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), ForgotpassActivity.class);
                startActivity(intent);
            }
        });
    }

    public void findviewid() {
        imglogin = findViewById(R.id.Imglogin);
        tvforgot = findViewById(R.id.tvforgotpass);
        tvregister = findViewById(R.id.tvregister);
        edtGmail = findViewById(R.id.edtemail);
        edtMatKhau = findViewById(R.id.edtpass);
        checkBox = findViewById(R.id.checkBox);
    }

    public void getpreferences() {
        SharedPreferences preferences = getSharedPreferences("user_file", MODE_PRIVATE);
        edtGmail.setText(preferences.getString("gmail", ""));
        edtMatKhau.setText(preferences.getString("matkhau", ""));
        checkBox.setChecked(preferences.getBoolean("remember", false));
    }

    public boolean validateemail() {
        String a = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (edtGmail.getText().toString().equals("")) {
            edtGmail.setError("Hãy nhập gmail của bạn.");
            return false;
        } else if (!names.matches(a)) {
            edtGmail.setError("Nhập đúng định dạng gmail.");
            return false;
        } else {
            edtGmail.setError(null);
            return true;
        }
    }

    private void postlogin() {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        str_email = edtGmail.getText().toString().trim();
        str_password = edtMatKhau.getText().toString().trim();
        StringRequest request = new StringRequest(Request.Method.POST, urllogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.equalsIgnoreCase("Đăng Nhập Thành Công")) {
                    remember(str_email, str_password, checkBox.isChecked());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("user", str_email);
                    startActivity(intent);
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
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Gmail", str_email);
                params.put("MatKhau", str_password);
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    private void remember(String strname, String strpass, boolean checked) {
        SharedPreferences preferences = getSharedPreferences("user_file", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!checked) {
            editor.clear();
        } else {
            editor.putString("gmail", strname);
            editor.putString("matkhau", strpass);
            editor.putBoolean("remember", checked);
        }
        editor.commit();
    }

    public boolean validatepass() {
        if (edtMatKhau.getText().toString().equals("")) {
            edtMatKhau.setError("Nhập mật khẩu của bạn");
            return false;
        } else {
            edtMatKhau.setError(null);
            return true;
        }
    }
}