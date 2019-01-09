package com.zennow.zennow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private Button newAccount;
    private TextView forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=(EditText) this.findViewById(R.id.email);
        password=(EditText) this.findViewById(R.id.password);
        login=(Button) this.findViewById(R.id.login);
        newAccount=(Button) this.findViewById(R.id.register);
        forget=(TextView)this.findViewById(R.id.forget);

        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Registre.class));
                finish();
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Forget.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Home.class));
                finish();
            }
        });
    }

    class LoginTask extends AsyncTask<String, String, String> {
        private String success;
        private String erreurMSG;
        private ProgressDialog pDialog;
        private Config config=Config.GetInstance();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Login In progress..."/*getResources().getString(R.string.CreationMembre)*/);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            // Building Parameters
            HashMap<String, String> data = new HashMap<>();
            data.put("username", "");

            // getting JSON Object
            try
            {
                JSONObject jsone = new JSONObject(new SendData(config.url_login,data).sendPostRequest());
                // check for success tag
                try {
                    success = jsone.getString(config.SuccessResponse);
                    erreurMSG=jsone.getString("message");
                    if (success.equals("1")) {
                    }
                } catch (Exception e) {e.printStackTrace();}
            } catch (Exception e) {e.printStackTrace();}

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            try{
                if(success.equals("2"))
                {

                }
            }
            catch(Exception e){e.printStackTrace();}
        }
    }

}
