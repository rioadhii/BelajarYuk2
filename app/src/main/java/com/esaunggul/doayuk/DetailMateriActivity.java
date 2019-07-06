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
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DetailMateriActivity extends AppCompatActivity {

    ProgressDialog loadingProgress;

    private String mata_pelajaran;
    private String kategori;
    private String judul;
    private Integer thumbnail;
    private Integer subKategori;
    private TextView labelKategori;
    private TextView labelJudul;

    private TextView labelArab;
    private TextView labelLatin;
    private TextView labelArti;

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
        thumbnail = extras.getInt("PARAM_THUMBNAIL");

        subKategori = extras.getInt("PARAM_SUBKATEGORI");
        kategori = extras.getString("PARAM_KATEGORI");
        judul = extras.getString("PARAM_JUDUL");

        Log.e("SUB KATEGORI" ,""+ subKategori);

        loadingProgress = new ProgressDialog(DetailMateriActivity.this);

        labelKategori = findViewById(R.id.kategori);
        labelJudul = findViewById(R.id.judul);

        labelArab = findViewById(R.id.txtArab);
        labelLatin = findViewById(R.id.txtLatin);
        labelArti = findViewById(R.id.txtArti);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("doa_detail");

        Query queryRef = ref.orderByChild("sub_kategori_id").equalTo(subKategori);

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Your Logic here
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    DetailDoa mModel = eventSnapshot.getValue(DetailDoa.class);

                    labelKategori.setText(kategori);
                    labelJudul.setText(judul);

                    labelArab.setText(mModel.getArab());
                    labelLatin.setText(mModel.getLatin());
                    labelArti.setText(mModel.getArti());

                    Log.e("DATA" ,""+ mModel.getArab());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
