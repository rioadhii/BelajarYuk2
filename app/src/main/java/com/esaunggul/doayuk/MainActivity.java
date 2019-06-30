package com.esaunggul.doayuk;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.SystemClock;
import com.google.android.material.snackbar.Snackbar;
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
    private long mLastClickTime = 0;

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
        labelAplikasi = findViewById(R.id.labelAplikasi);
        //labelUser = findViewById(R.id.labelUser);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.ic_smile);
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
            session.logoutUser();
        }
        return true;
    }

    public void onBahasaIndonesiaClick(View view){
        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        // do your magic here
        Intent intent = new Intent(view.getContext(), MateriActivity.class);
        Bundle extras = new Bundle();
        extras.putString("PARAM_PELAJARAN", "Bahasa Indonesia");
        intent.putExtras(extras);
        view.getContext().startActivity(intent);
    }

    public void onMatematikaClick(View view){
        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        // do your magic here
        Intent intent = new Intent(view.getContext(), MateriActivity.class);
        Bundle extras = new Bundle();
        extras.putString("PARAM_PELAJARAN", "Matematika");
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

    public void onIPAClick(View view){
        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        // do your magic here
        Intent intent = new Intent(view.getContext(), MateriActivity.class);
        Bundle extras = new Bundle();
        extras.putString("PARAM_PELAJARAN", "Pengetahuan Alam");
        intent.putExtras(extras);
        view.getContext().startActivity(intent);
    }

    public void onBahasaInggrisClick(View view){
        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        // do your magic here
        Intent intent = new Intent(view.getContext(), MateriActivity.class);
        Bundle extras = new Bundle();
        extras.putString("PARAM_PELAJARAN", "Bahasa Inggris");
        intent.putExtras(extras);
        view.getContext().startActivity(intent);
    }
}
