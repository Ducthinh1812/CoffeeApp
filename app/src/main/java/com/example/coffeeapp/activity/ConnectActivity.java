package com.example.coffeeapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConnectActivity extends AppCompatActivity {
    ImageButton imgRegister, imgLogin;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    String name, firstname, picture;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_connect);
        //findviewid
        loginButton = findViewById(R.id.login_button);
        imgLogin = findViewById(R.id.btnlogin);
        imgRegister = findViewById(R.id.btnregister);

        imgLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
            }
        });
        imgRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginFacebook();
    }
    public void loginFacebook() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final ProgressDialog progressDialog = new ProgressDialog(ConnectActivity.this);
                progressDialog.setMessage("Please Wait..");
                progressDialog.setCancelable(false);
                progressDialog.show();
                result();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 3000);

            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    private void result() {

        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                Log.e("ssss", graphResponse.getJSONObject().toString());
                try {
                    name = jsonObject.getString("name");
                    firstname = jsonObject.getString("first_name");
                    id = jsonObject.getInt("id");
                    picture = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
                    Log.e("", "" + name + firstname + picture + id);
                    InsertAccface( picture, name);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "name,email,first_name,picture.type(large)");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }

    private void InsertAccface( String picturef, String namef) {
        StringRequest request = new StringRequest(Request.Method.POST, "https://website1812.000webhostapp.com/Coffee/RegisterFB.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Đăng kí Thành Công")) {
                    startActivity(new Intent(ConnectActivity.this, MainActivity.class));
                    Toast.makeText(ConnectActivity.this, "đăng nhập thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), "xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("image", picturef);
//                params.put("id", String.valueOf(idf));
                params.put("username", namef);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(request);
    }
}