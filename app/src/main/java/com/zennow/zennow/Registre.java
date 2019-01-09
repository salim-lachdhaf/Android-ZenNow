package com.zennow.zennow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registre extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText phone;
    private EditText name;
    private Button login;
    private Button newAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);
        email=(EditText) this.findViewById(R.id.email);
        password=(EditText) this.findViewById(R.id.password);
        login=(Button) this.findViewById(R.id.login);
        newAccount=(Button) this.findViewById(R.id.register);
        phone=(EditText) this.findViewById(R.id.phone);
        name=(EditText) this.findViewById(R.id.name);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
    }
}
