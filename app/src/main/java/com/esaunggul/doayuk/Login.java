package com.esaunggul.doayuk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private TextView labelAplikasi;
    private TextView labelAuthor;
    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    ProgressDialog loadingProgress;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new SessionManager(getApplicationContext());

        labelAplikasi = findViewById(R.id.labelAplikasi);
        labelAuthor = findViewById(R.id.labelAuthor);
        Typeface pacificoFont =ResourcesCompat.getFont(this.getApplicationContext(), R.font.pacifico);
        labelAplikasi.setTypeface(pacificoFont);
        labelAuthor.setTypeface(pacificoFont);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        loadingProgress = new ProgressDialog(Login.this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(txtUsername.getText())){
                    txtUsername.setError( "Harap mengisi username!" );
                }
                else if (TextUtils.isEmpty(txtPassword.getText())){
                    txtPassword.setError( "Harap mengisi password!" );
                }
                else{
                    loadingProgress.setMessage("Harap menunggu...");
                    loadingProgress.show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            String username = txtUsername.getText().toString();
                            String password = txtPassword.getText().toString();
                            if (password.equals("123456")){
                                session.createLoginSession(username);
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(Login.this, "Username atau Password anda salah.",
                                        Toast.LENGTH_LONG).show();
                            }
                            loadingProgress.dismiss();
                            //whatever you want just you have to launch overhere.
                        }
                    }, 1000);//just mention the time when you want to launch your action
                }
            }
        });
    }
}
