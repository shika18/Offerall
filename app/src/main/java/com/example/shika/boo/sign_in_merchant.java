package com.example.shika.boo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import models.Place;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class sign_in_merchant extends AppCompatActivity {

    private EditText ET_password,ET_email ;
    RequestQueue requestQueue ;
    StringRequest request ;
    AlertDialog alertDialog;
    Place placeClass ;
    SharedPreferences sharedPreferences ;
    private  String merchantLoginURL = "http://gp.sendiancrm.com/offerall/loginPlace.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

     /* sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getBoolean("logged in",false)) {
            Intent too = new Intent(this, Merchant_Home.class);
            startActivity(too);
        }*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_merchant);
        ET_email = (EditText) findViewById(R.id.useremail);
        ET_password = (EditText) findViewById(R.id.userpassword);
        requestQueue = Volley.newRequestQueue(this) ;
        alertDialog = new AlertDialog.Builder(this).create();

    }

    public  Boolean validate(){

        boolean valid = true ;

        if (ET_email.getText().toString().matches("")){
            ET_email.setError("Enter Valid Email Address!");
            valid = false ;
        }

        if (ET_password.getText().toString().matches("")){ // || ET_password.length()<8
            ET_password.setError("Enter Valid Password!");
            valid = false ;
        }

        return valid ;
    }

    public void regerster(View view) {
        Intent intent = new Intent(getApplicationContext(), Merchant_signup.class);
        startActivity(intent);
    }

    public void login(View view) {

        if (validate()){
            String Email = ET_email.getText().toString() ;
            String Password = ET_password.getText().toString() ;
            loginDetails(Email,Password);
        }

    }

    public void loginDetails(final String email , final String password) {

        request = new StringRequest(Request.Method.POST, merchantLoginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                   JSONObject jsonObject = new JSONObject(response);

                 //if (jsonObject.names().get(0).equals("success")){
                  //Toast.makeText(getApplicationContext(), ""+jsonObject.get("success"),
                   //Toast.LENGTH_LONG).show();else

                 if (jsonObject.names().get(0).equals("error"))
                    {
                        Toast.makeText(getApplicationContext(), ""+jsonObject.get("error"),
                                Toast.LENGTH_LONG).show();
                    }

                        JSONArray places = jsonObject.getJSONArray("place");
                        JSONObject place = places.getJSONObject(0);
                        placeClass = new Place();

                        placeClass.setId(Integer.parseInt((String) place.getString("Place_ID")));
                        placeClass.setPlaceName((String) place.getString("PLaceName"));
                        placeClass.setPlacePhoto((String) place.getString("Place_LogoPhoto"));
                        placeClass.setPlaceEmail((String) place.getString("PlaceEmail"));
                        placeClass.setPlacePassword((String) place.getString("PlacePassword"));
                        placeClass.setPlaceCategoryId(Integer.parseInt((String) place.getString("Category_id")));
                        //placeClass.setPlaceRating(Float.parseFloat((String) place.getString("PlaceRate")));
                        //placeClass.setApprove(Boolean.parseBoolean((String) place.getString("approve")));
                        PlaceSesionStart(placeClass);

                     if(sharedPreferences!=null) {

                         Intent intent = new Intent(getApplicationContext(), SecondHome.class);
                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         startActivity(intent);

                        alertDialog.setMessage("Welcome:  "+sharedPreferences.getString("PName",null));
                        alertDialog.show();

                    }
                    else{
                         Toast.makeText(getApplicationContext(), "sharedPreferences=null", Toast.LENGTH_SHORT).show();
                        alertDialog.setMessage("Wrong UserName Or Password");
                        alertDialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong",Toast.LENGTH_LONG).show();
                alertDialog.setMessage("حدث خطأ بالاتصال بالشبكه؟" +"\n"+"يجب عليك فتح النت؟");
                alertDialog.show();
                error.printStackTrace();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("email", email);
                hashMap.put("password", password);
                return hashMap;
            }

        };
        requestQueue.add(request);
    }

public  void PlaceSesionStart(Place place)
 {

     sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
     SharedPreferences.Editor editor = sharedPreferences.edit();
     editor.putString("PName",place.getPlaceName());
     editor.putInt("PID",place.getId());
     editor.putString("PEmail",place.getPlaceEmail());
     editor.putString("PPassword",place.getPlacePassword());
     editor.putString("Pphoto",place.getPlacePhoto());
     editor.putInt("PcategoryId",place.getPlaceCategoryId());
     editor.putBoolean("Papprove",place.getApprove());
     editor.putFloat("Prating",place.getPlaceRating());
     editor.putBoolean("place logged in",true);
     editor.commit();

 }


    public void forgotpassword(View view) {
        Intent too = new Intent(this,forgotpasswordmerchant.class);
        startActivity(too);
    }
}
