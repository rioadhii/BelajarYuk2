package com.esaunggul.doayuk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LoginActivity";
    private DatabaseReference mDatabase;

    private TextView labelAplikasi;
    private TextView labelAuthor;
    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    private Button btnRegister;
    ProgressDialog loadingProgress;
    SessionManager session;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_login);
        session = new SessionManager(getApplicationContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        labelAplikasi = findViewById(R.id.labelAplikasi);
        labelAuthor = findViewById(R.id.labelAuthor);
        Typeface arabianFont =ResourcesCompat.getFont(this.getApplicationContext(), R.font.arabianonenightstand);
        Typeface pacificoFont =ResourcesCompat.getFont(this.getApplicationContext(), R.font.pacifico);

        labelAplikasi.setTypeface(arabianFont);
        labelAuthor.setTypeface(pacificoFont);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        loadingProgress = new ProgressDialog(Login.this);

        //nambahin method onClick, biar tombolnya bisa diklik
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        /*btnLogin.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }

    //fungsi signin untuk mengkonfirmasi data pengguna yang sudah mendaftar sebelumnya
    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        loadingProgress.setMessage("Harap menunggu...");
        loadingProgress.show();
        String email = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        loadingProgress.dismiss();
                        try {
                            if (task.isSuccessful()) {
                                onAuthSuccess(task.getResult().getUser());
                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText(Login.this, "Login Failed: " + message,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception ex){
                            String message = ex.getLocalizedMessage();
                            if (message.contains(":")) {
                                message = message.split(":")[1];
                            }
                            Toast.makeText(Login.this, "Login Failed: " + message,
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    //fungsi ini untuk mendaftarkan data pengguna ke Firebase
    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }
        loadingProgress.setMessage("Harap menunggu...");
        loadingProgress.show();
        //showProgressDialog();
        String email = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        loadingProgress.dismiss();
                        try{
                            if (task.isSuccessful()) {
                                onAuthSuccess(task.getResult().getUser());
                            } else {
                                String message = task.getException().getMessage();
                                AuthResult result = task.getResult();
                                Toast.makeText(Login.this, "Register failed: " + message,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception ex){
                            String message = ex.getLocalizedMessage();
                            if (message.contains(":")) {
                                message = message.split(":")[1];
                            }
                            Toast.makeText(Login.this, "Register failed: " + message,
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    //fungsi dipanggil ketika proses Authentikasi berhasil
    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // membuat User admin baru
        writeNewAdmin(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        session.createLoginSession(username);
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /*
        ini fungsi buat bikin username dari email
            contoh email: abcdefg@mail.com
            maka username nya: abcdefg
     */
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    //fungsi untuk memvalidasi EditText email dan password agar tak kosong dan sesuai format
    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(txtUsername.getText().toString())) {
            txtUsername.setError("Harap mengisi Email!");
            result = false;
        } else {
            txtUsername.setError(null);
        }

        if (TextUtils.isEmpty(txtPassword.getText().toString())) {
            txtPassword.setError("Harap mengisi password!");
            result = false;
        }
        else if(txtPassword.getText().toString().length() < 6){
            txtPassword.setError("Min 6 karakter!");
            result = false;
        }
        else {
            txtPassword.setError(null);
        }

        return result;
    }

    // menulis ke Database
    private void writeNewAdmin(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("user").child(userId).setValue(user);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnLogin) {
            signIn();
        } else if (i == R.id.btnRegister) {
            signUp();
        }
    }
}
