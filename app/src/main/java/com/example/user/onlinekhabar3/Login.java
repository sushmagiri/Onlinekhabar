package com.example.user.onlinekhabar3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Login extends AppCompatActivity {

    String response;
    ProgressDialog progressDialog;
    HttpClient client;
    String url = "http://192.168.1.28/loginregister/login.php";
    Button btnLogin, btnLinkToRegister;
     EditText inputName,inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        btnLogin= (Button) findViewById(R.id.btnLogin);
         inputName= (EditText) findViewById(R.id.login_name);
         inputPassword = (EditText) findViewById(R.id.login_password);
       /* btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sender = new Intent(MainActivity.this,news.class);
                Bundle b1 = new Bundle();
                b1.putString("name", email.getText().toString());
                b1.putString("email",password.getText().toString());
                sender.putExtras(b1);
                startActivity(sender);
                MainActivity.this.finish();
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/
        client = new DefaultHttpClient();

        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LongOperation().execute(url);

            }

        });
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivityClass.class);
                startActivity(i);
                finish();
            }
        });

    }
    private class LongOperation  extends AsyncTask<String, String,String> {
        private String error = null;

        protected void onPreExecute() {
            progressDialog = new ProgressDialog(Login.this);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("Please wait..");
            progressDialog.show();
        }

        // Call after onPreExecute method
        protected String doInBackground(String... urls) {

            HttpGet request = new HttpGet(urls[0].toString()+"?name="+inputName.getText().toString()+"&password="+inputPassword.getText().toString());
            HttpResponse httpResponse;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                httpResponse = client.execute(request);
                HttpEntity entity = httpResponse.getEntity();
                InputStream stream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));
                String line=null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (ClientProtocolException e) {
                error= Log.getStackTraceString(e);
            } catch (IOException e) {
                error=Log.getStackTraceString(e);;
            }
            response=stringBuilder.toString();
            return null;

        }
        protected void onPostExecute(String s) {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            if(error==null ){
//                Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
//                Toast.makeText(Login.this, response, Toast.LENGTH_LONG).show();
               switch (response){

                   case "login sucessfull":Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                       intent.putExtra("name",inputName.getText().toString());
                       startActivity(intent);
                       break;
                       default: Toast.makeText(Login.this,"Not Registered",Toast.LENGTH_LONG).show();
                           break;
               }

               }
            /*   if (response.equals("Please fill the name and password")){
                    Toast.makeText(Login.this,"Please fill details",Toast.LENGTH_LONG).show();

                }
               else if(response.equals("login sucessfull")){
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else {

                    Toast.makeText(Login.this,"Login not successful",Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(Login.this,"No internet connection", Toast.LENGTH_SHORT).show();
                Log.d("Error", error);
            }*/

        }
    }

}



