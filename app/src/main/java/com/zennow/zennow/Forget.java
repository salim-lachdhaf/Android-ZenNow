package com.zennow.zennow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Forget extends AppCompatActivity {
    private EditText email;
    private Button send;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        intialeToolBar();
        email=(EditText) this.findViewById(R.id.email);
        send=(Button) this.findViewById(R.id.send);
    }

   void intialeToolBar(){
       toolbar=(Toolbar)this.findViewById(R.id.toolbar);
       toolbar.setTitle("");
       setSupportActionBar(toolbar);
       getSupportActionBar().setHomeButtonEnabled(true);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left);
       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),Login.class));
               finish();
           }
       });
   }
}
