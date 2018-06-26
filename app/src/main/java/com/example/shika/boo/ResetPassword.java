package com.example.shika.boo;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ResetPassword extends AppCompatActivity {
    private static final String forgotpasswordurl = "http://gp.sendiancrm.com/offerall/Resetpassword.php";
    EditText Verficationcode;
    AlertDialog alertDialog;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Verficationcode = (EditText) findViewById(R.id.passwordcode);
        alertDialog= new AlertDialog.Builder(this).create();
        Intent intent=getIntent();
        Bundle extras = intent.getExtras();
        email= extras.getString("email");
        email= extras.getString("email");

    }

    public void submit(View view) {
        forgotpassword(email,Verficationcode.getText().toString());
    }
    public void forgotpassword(final String email , final String Verficationcode) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, forgotpasswordurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Token Passed")){
                            Intent too = new Intent(getApplicationContext(),newpassword.class);
                            too.putExtra("email",email);
                            startActivity(too);
                        }
                        else {
                            alertDialog.setMessage(response);
                            alertDialog.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",email);
                params.put("verficationcode",Verficationcode);


                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
