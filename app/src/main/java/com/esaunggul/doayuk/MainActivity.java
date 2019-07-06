package com.esaunggul.doayuk;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.SystemClock;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    SessionManager session;
    TextView labelAplikasi;
    String loginMethod = "";
    private GoogleSignInClient googleSignInClient;
    private long mLastClickTime = 0;
    public static final String GOOGLE_ACCOUNT = "google_account";

    //TextView labelUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(getApplicationContext());

        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        String username = user.get(SessionManager.KEY_USERNAME);
        String loginMethods = user.get(SessionManager.LOGIN_METHOD);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        labelAplikasi = findViewById(R.id.labelAplikasi);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(session.isFirstAccess()) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content) , "Assalamu'alaikum " + username + ", mari kita perbanyak do'a", Snackbar.LENGTH_LONG);

            snackbar.show();
            session.setIsFirstAccessFalse();
        }



        Typeface arabianFont =ResourcesCompat.getFont(this.getApplicationContext(), R.font.arabianonenightstand);
        //labelUser.setText("Hai, " + username);
        //labelUser.setTypeface(pacificoFont);
        labelAplikasi.setTypeface(arabianFont);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sub_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //On Succesfull signout we navigate the user back to LoginActivity
                    session.logoutUser();
                }
            });
            session.logoutUser();
        }
        return true;
    }

    public void onSehariHariClick(View view){
        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        // do your magic here
        Intent intent = new Intent(view.getContext(), MateriActivity.class);
        Bundle extras = new Bundle();
        extras.putString("PARAM_PELAJARAN", "SehariHari");
        intent.putExtras(extras);
        view.getContext().startActivity(intent);
    }

    public void onHajiClick(View view){
        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        // do your magic here
        Intent intent = new Intent(view.getContext(), MateriActivity.class);
        Bundle extras = new Bundle();
        extras.putString("PARAM_PELAJARAN", "Haji");
        intent.putExtras(extras);
        view.getContext().startActivity(intent);
    }

    public void onJadwalClick(View view){
        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        // do your magic here
        Intent intent = new Intent(view.getContext(), JadwalActivity.class);
        view.getContext().startActivity(intent);
    }

    public void onSuratPendekClick(View view){
        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        // do your magic here
        Intent intent = new Intent(view.getContext(), MateriActivity.class);
        Bundle extras = new Bundle();
        extras.putString("PARAM_PELAJARAN", "SuratPendek");
        intent.putExtras(extras);
        view.getContext().startActivity(intent);
    }

    public void onSunnahClick(View view){
        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        // do your magic here
        Intent intent = new Intent(view.getContext(), MateriActivity.class);
        Bundle extras = new Bundle();
        extras.putString("PARAM_PELAJARAN", "Sunnah");
        intent.putExtras(extras);
        view.getContext().startActivity(intent);
    }
}
