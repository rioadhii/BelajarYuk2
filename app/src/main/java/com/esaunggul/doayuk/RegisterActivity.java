package com.esaunggul.doayuk;

import android.app.Activity;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "RegisterActivity";
    private DatabaseReference mDatabase;

    private TextView labelAplikasi;
    private TextView labelAuthor;
    EditText txtName;
    EditText txtUsername;
    EditText txtPassword;
    Button btnRegister;
    private TextView btnLogin;
    ProgressDialog loadingProgress;
    SessionManager session;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_register);
        session = new SessionManager(getApplicationContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        labelAplikasi = findViewById(R.id.labelAplikasi);
        labelAuthor = findViewById(R.id.labelAuthor);
        Typeface arabianFont =ResourcesCompat.getFont(this.getApplicationContext(), R.font.arabianonenightstand);
        Typeface pacificoFont =ResourcesCompat.getFont(this.getApplicationContext(), R.font.pacifico);

        labelAplikasi.setTypeface(arabianFont);
        labelAuthor.setTypeface(pacificoFont);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnLogin = (TextView)findViewById(R.id.btnLogin);

        txtName = (EditText) findViewById(R.id.txtName);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        loadingProgress = new ProgressDialog(RegisterActivity.this);

        //nambahin method onClick, biar tombolnya bisa diklik
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    //fungsi signin untuk mengkonfirmasi data pengguna yang sudah mendaftar sebelumnya
    //fungsi ini untuk mendaftarkan data pengguna ke Firebase
    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }
        loadingProgress.setMessage("Harap menunggu...");
        loadingProgress.show();
        //showProgressDialog();
        String name = txtName.getText().toString();
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
                                Toast.makeText(RegisterActivity.this, "Register failed: " + message,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception ex){
                            String message = ex.getLocalizedMessage();
                            if (message.contains(":")) {
                                message = message.split(":")[1];
                            }
                            Toast.makeText(RegisterActivity.this, "Register failed: " + message,
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    //fungsi dipanggil ketika proses Authentikasi berhasil
    private void onAuthSuccess(FirebaseUser user) {
        String fullName = txtName.getText().toString();
        String userName = usernameFromEmail(txtUsername.getText().toString());

        // membuat User admin baru
        writeNewAdmin(user.getUid(), userName, user.getEmail(), fullName);

        // Go to MainActivity
        session.createLoginSession(userName, "Basic");
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /*
        ini fungsi buat bikin username dari email
            contoh email: abcdefg@mail.com
            maka username nya: abcdefg
     */

    //fungsi untuk memvalidasi EditText email dan password agar tak kosong dan sesuai format
    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(txtName.getText().toString())) {
            txtName.setError("Harap mengisi Nama!");
            result = false;
        } else {
            txtName.setError(null);
        }

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
    private void writeNewAdmin(String userId, String userName, String email, String fullName) {
        User user = new User(userName, email, fullName);

        mDatabase.child("user").child(userId).setValue(user);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnLogin) {
            Intent intent = new Intent(RegisterActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
        else if (i == R.id.btnRegister) {
            signUp();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadingProgress.hide();
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account);
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }
    }

    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        String userName = googleSignInAccount.getGivenName();
        session.createLoginSession(userName, "Google");
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (alreadyloggedAccount != null) {
            onLoggedIn(alreadyloggedAccount);
        } else {
            Log.d(TAG, "Not logged in");
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, Login.class);
        startActivity(intent);
        finish();
    }
}
