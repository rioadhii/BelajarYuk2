package com.esaunggul.doayuk;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.refactor.lib.colordialog.ColorDialog;

public class JadwalActivity extends AppCompatActivity {
    private TextView labelActivity;
    private TextView LabelActivityDescription;

    private long mLastClickTime = 0;

    private TextView labelActivityDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();
        labelActivity = findViewById(R.id.labelActivity);
        LabelActivityDescription = findViewById(R.id.labelActivityDescription);
        Typeface pacificoFont = ResourcesCompat.getFont(this.getApplicationContext(), R.font.pacifico);
        labelActivity.setTypeface(pacificoFont);
        LabelActivityDescription.setTypeface(pacificoFont);
        try {
            Glide.with(this).load(R.drawable.jadwalujianlogo).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onBahasaIndonesiaClick(View view){
        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        ColorDialog dialog = new ColorDialog(this);
        dialog.setTitle("Senin");
        dialog.setContentText("Mata Pelajaran : Bahasa Indonesia\nTanggal : 1 April 2019\nJam : 09.00 WIB - 12.00 WIB");
        dialog.setColor("#90b06e");
        dialog.setNegativeListener("Tutup", new ColorDialog.OnNegativeListener() {
                    @Override
                    public void onClick(ColorDialog dialog) {
                        dialog.dismiss();
                    }}).show();
    }

    public void onIPAClick(View view){
        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        ColorDialog dialog = new ColorDialog(this);
        dialog.setTitle("Rabu");
        dialog.setContentText("Mata Pelajaran : Pengetahuan Alam\nTanggal : 3 April 2019\nJam : 09.00 WIB - 12.00 WIB");
        dialog.setColor("#79b3e4");
        dialog.setNegativeListener("Tutup", new ColorDialog.OnNegativeListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                dialog.dismiss();
            }}).show();
    }

    public void onMatematikaClick(View view){
        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        ColorDialog dialog = new ColorDialog(this);
        dialog.setTitle("Selasa");
        dialog.setContentText("Mata Pelajaran : Matematika\nTanggal : 2 April 2019\nJam : 09.00 WIB - 12.00 WIB");
        dialog.setColor("#e8bd4b");
        dialog.setNegativeListener("Tutup", new ColorDialog.OnNegativeListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                dialog.dismiss();
            }}).show();
    }

    public void onBahasaInggrisClick(View view){
        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        ColorDialog dialog = new ColorDialog(this);
        dialog.setTitle("Kamis");
        dialog.setContentText("Mata Pelajaran : Bahasa Inggris\nTanggal : 4 April 2019\nJam : 09.00 WIB - 12.00 WIB");
        dialog.setColor("#ed7861");
        dialog.setNegativeListener("Tutup", new ColorDialog.OnNegativeListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                dialog.dismiss();
            }}).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Jadwal Ujian");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
}
