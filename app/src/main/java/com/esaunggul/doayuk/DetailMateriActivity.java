package com.esaunggul.doayuk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailMateriActivity extends AppCompatActivity {

    private String mata_pelajaran;
    private String kode_materi;
    private String judul_materi;
    private String topik;
    private String konten;
    private Integer thumbnail;
    private Integer cover;
    ProgressDialog loadingProgress;

    private TextView labelKategori;
    private TextView labelTopik;
    private TextView labelKonten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_materi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mata_pelajaran = extras.getString("PARAM_PELAJARAN");
        kode_materi = extras.getString("PARAM_KODE_MATERI");
        judul_materi = extras.getString("PARAM_JUDUL_MATERI");
        topik = extras.getString("PARAM_TOPIK");
        cover = extras.getInt("PARAM_COVER");
        thumbnail = extras.getInt("PARAM_THUMBNAIL");
        konten = extras.getString("PARAM_KONTEN");
        loadingProgress = new ProgressDialog(DetailMateriActivity.this);

        labelKategori = findViewById(R.id.kategori);
        labelTopik = findViewById(R.id.topik);
        labelKonten = findViewById(R.id.konten);

        labelKategori.setText(mata_pelajaran);
        labelTopik.setText(topik);
        loadingProgress.setMessage("Harap menunggu...");
        loadingProgress.show();

        try {
            Glide.with(this).load(thumbnail).into((ImageView) findViewById(R.id.photo));
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadingProgress.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Bundle extras = new Bundle();
                Intent intent = new Intent(this, MateriActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                extras.putString("PARAM_PELAJARAN", mata_pelajaran);
                intent.putExtras(extras);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Bundle extras = new Bundle();
        Intent intent = new Intent(this, MateriActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        extras.putString("PARAM_PELAJARAN", mata_pelajaran);
        intent.putExtras(extras);
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

        AppBarLayout.LayoutParams params =
                (AppBarLayout.LayoutParams) collapsingToolbar.getLayoutParams();

        params.setScrollFlags(0);

        collapsingToolbar.setLayoutParams(params);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                collapsingToolbar.setTitle(" ");
                isShow = false;
            }
        });
    }
}
