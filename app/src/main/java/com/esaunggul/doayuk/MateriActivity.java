package com.esaunggul.doayuk;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MateriActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MateriAdapter adapter;
    private List<MateriList> materiList;
    private String matapelajaran;
    private TextView labelActivity;
    private TextView labelActivityDescription;

    private FirebaseListAdapter<SubKategori> fbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        matapelajaran = extras.getString("PARAM_PELAJARAN");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();
        labelActivity = findViewById(R.id.labelActivity);
        labelActivityDescription = findViewById(R.id.labelActivityDescription);
        Typeface pacificoFont =ResourcesCompat.getFont(this.getApplicationContext(), R.font.pacifico);
        labelActivity.setTypeface(pacificoFont);
        labelActivityDescription.setTypeface(pacificoFont);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        materiList = new ArrayList<>();
        adapter = new MateriAdapter(this, materiList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        switch (matapelajaran){
                case "Bahasa Indonesia":

                    /*fbAdapter = new FirebaseListAdapter<SubKategori>(this, SubKategori.class,
                            R.layout.activity_materi, FirebaseDatabase.getInstance().getReference()) {
                        @Override
                        protected void populateView(View v, SubKategori model, int position) {
                            int[] covers = new int[]{
                                    R.drawable.lecturer,
                                    R.drawable.bahasaindonesia};

                            MateriList data = new MateriList(
                                    model.getKategori_name(),
                                    "Paragraf",
                                    "Bahasa Indonesia",
                                    "Ide pokok adalah masalah utama ",
                                    covers[1],
                                    covers[0],
                                    "BI001",
                                    "");

                            materiList.add(data);
                        }
                    };

                    adapter.notifyDataSetChanged();
*/
                    prepareMateriBahasaIndonesia();
                    //displayChatMessages();
                    break;
                case "Pengetahuan Alam":
                    prepareMateriPengetahuanAlam();
                    break;
                case "Bahasa Inggris":
                    prepareMateriBahasaInggris();
                    break;
                case "Matematika":
                    prepareMateriMatematika();
                    break;
                default:
                    break;
        }

        try {
            Glide.with(this).load(R.drawable.openbooksm).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                    collapsingToolbar.setTitle(matapelajaran);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void prepareMateriBahasaIndonesia() {
        int[] covers = new int[]{
                R.drawable.lecturer,
                R.drawable.bahasaindonesia};
        MateriList a = new MateriList(
                "Menentukan Ide Pokok Suatu Paragraf",
                "Paragraf",
                "Bahasa Indonesia",
                "Ide pokok adalah masalah utama yang dibahas dalam suatu paragraf. Nama lain ide pokok antara lain pokok pikiran, pikiran pokok, gagasan utama, gagasan pokok, dan pikiran utama. Lalu, bagaimana cara yang benar dalam menentukan ide pokok suatu paragraf?",
                covers[1],
                covers[0],
                "BI001",
                "<div style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Ide pokok adalah masalah utama yang dibahas dalam suatu paragraf. Nama lain ide pokok antara lain pokok pikiran, pikiran pokok, gagasan utama, gagasan pokok, dan pikiran utama. Cara sederhana untuk menentukan ide pokok bisa dengan mengajukan pertanyaan “paragraf tersebut membahas tentang apa?”.</span>\n" +
                        "</div>\n" +
                        "<br>\n" +
                        "<div style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Ide pokok sering muncul dalam soal-soal ujian. Ide pokok seolah menjadi menu wajib di soal ujian, baik jenjang SD sampai dengan SMA, atau bahkan di perkuliahan. Tidak sedikit siswa yang kesulitan menentukan ide pokok paragraf karena kurangnya teknik yang diajarkan oleh guru. Guru pun harus kreatif dan paham benar bagaimana cara menjelaskan ide pokok kepada siswa.</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Cara menentukan ide pokok</span></h3>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div class=\"MsoNormal\" style=\"text-align: justify;\">\n" +
                        "<p style=\"font-family: &quot;verdana&quot; , sans-serif;\">Ada beberapa cara menentukan ide pokok secara sederhana, yaitu :</p>\n" +
                        "<ol  style=\"font-family: &quot;verdana&quot; , sans-serif;\">\n" +
                        "  <li>\n" +
                        "  <span style=\"font-family: &quot;verdana&quot; , sans-serif; text-\t\tindent: -18pt;\">Mengambil kalimat utama</span>\n" +
                        "  </li>\n" +
                        "  <li><span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Mengurangi kata-kata dalam kalimat utama yang tidak perlu<o:p></o:p></span></li>\n" +
                        "  <li><span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Membalik kalimat utama atau menyimpulkan kalimat utama<o:p></o:p></span></li>\n" +
                        "    <li><span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Menyimpulkan isi paragraf<o:p></o:p></span></li>\n" +
                        "</ol>\n" +
                        "\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-family: &quot;verdana&quot; , sans-serif;\">\n" +
                        " Sudah paham kan tentang 5 langkah menemukan ide pokok dalam paragraf dengan mudah? Mulai sekarang, coba terapkan langkah-langkah ini dan cari ide pokok dari bacaanmu ya.\n" +
                        " </span> \n" +
                        "</div>\n" +
                        "\n" +
                        "\n");
        materiList.add(a);

        adapter.notifyDataSetChanged();
    }

    private void prepareMateriPengetahuanAlam() {
        int[] covers = new int[]{
                R.drawable.lecturer,
                R.drawable.ipa
        };
        MateriList a = new MateriList(
                "Hukum Archimedes",
                "Fisika",
                "Pengetahuan Alam",
                "Pada sebuah peristiwa Archimedes menemukan hukum yang disebut dengan Hukum Archimedes yang berbunyi “apabila sebuah benda, sebagian atau seluruhnya terbenam kedalam air, maka benda tersebut akan mendapat gaya tekan yang mengarah keatas yang besarnya sama dengan berat air yang dipindahkan oleh bagian benda yang terbenam tersebut”. Lalu apa sih hukim archimedes itu? yuk cari tau!",
                covers[1],
                covers[0],
                "PA001",
                "<div style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">\n" +
                        "Hukum Archimedes adalah sebuah hukum tentang prinsip pengapungan diatas zat cair. Ketika sebuah benda tercelup seluruhnya atau sebagian di dalam zat cair, zat cair akan memberikan gaya keatas (gaya apung) pada benda, dimana besarnya gaya keatas (gaya apung) sama dengan berat zat cair yang dipindahkan (Halliday, 1987). Pada prinsip Archimedes, sebuah benda akan mengapung didalam fluida jika massa jenis suatu benda lebih kecil daripada massa jenis zat cair (Jewwet, 2009).</span>\n" +
                        "<br><br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Hukum Archimedes adalah sebuah hukum mengenai prinsip pengapungan diatas benda cair. Hukum ini ditemukan oleh seorang ilmuwan yang bernama Archimedes. Beliau merupakan seorang astronom, fisikawan, matematikawan, dan juga Insinyur yang berkebangsaan Yunani.Lalu Bagaimana penerapan hukum Archimedes dalam keseharian?\n" +
                        "</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">1. Peristiwa benda Tenggelam</span></h3>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Peristiwa Tenggelam. Benda dikatakan tenggelam jika berada didasar zat cair.Sebuah benda akan tenggelam ke dalam suatu zat cair apabila gaya ke atas yang bekerja pada benda lebih kecil daripada berat benda.</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">2. Peristiwa benda Melayang</span></h3>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Peristiwa melayang benda dikatakan melayang jika seluruh benda tercelup kedalam zat cair, tetapi tidak menyentuh dasar zat cair. Sebuah benda akan melayang dalam zat cair apabila gaya ke atas yang bekerja pada benda sama dengan berat benda.</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">3. Peristiwa benda Terapung</span></h3>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Peristiwa terapung benda di katakan terapung jika sebagian benda tercelup didalam zat cair. Jika volume yang tercelup sebesar V, maka gaya keatas oleh zat cair yang disebabkan oleh volume benda yang tercelup sama dengan berat benda.</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Nah, itu tadi penjelasan singkat tentang Hukum Archimedes. Kamu sudah paham apa itu hukum Archimedes? terus semangat belajar yaaa. Amati juga kejadian-kejadian sains disekitarmu untuk menambah wawasan sainsmu\n" +
                        " </span> \n" +
                        "</div>\n" +
                        "\n" +
                        "\n");
        materiList.add(a);

        adapter.notifyDataSetChanged();
    }

    private void prepareMateriMatematika() {
        int[] covers = new int[]{
                R.drawable.lecturer,
                R.drawable.matematika
        };
        MateriList a = new MateriList(
                "Sistem Bilangan Komputer",
                "Sistem Bilangan",
                "Matematika",
                "Sistem Bilangan Komputer atau Number System adalah suatu cara untuk mewakili besaran dari suatu item fisik. Sistem Bilangan menggunakan suatu bilangan dasar atau basis (base / radix) yang tertentu. Yuk cari tahu apa aja sih sistem bilangan yang ada!",
                covers[1],
                covers[0],
                "MT001",
                "<div style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">\n" +
                        "Sistem Bilangan Komputer atau Number System adalah suatu cara untuk mewakili besaran dari suatu item fisik. Sistem Bilangan menggunakan suatu bilangan dasar atau basis (base / radix) yang tertentu. Dalam hubungannya dengan komputer, ada 4 Jenis Sistem Bilangan yang dikenal yaitu :</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">1. Desimal (Basis 10)</span></h3>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Desimal (Basis 10) adalah Sistem Bilangan yang paling umum digunakan dalam kehidupan sehari-hari. Sistem bilangan desimal menggunakan basis 10 dan menggunakan 10 macam simbol bilangan yaitu : 0, 1, 2, 3, 4, 5, 6, 7, 8 dan 9. Sistem bilangan desimal dapat berupa integer desimal (decimal integer) dan dapat juga berupa pecahan desimal (decimal fraction).</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">2. Biner (Basis 2)</span></h3>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Sistem bilangan biner atau sistem bilangan basis dua adalah sebuah sistem penulisan angka dengan menggunakan dua simbol yaitu 0 dan 1. Sistem bilangan biner modern ditemukan oleh Gottfried Wilhelm Leibniz pada abad ke-17. Sistem bilangan ini merupakan dasar dari semua sistem bilangan berbasis digital.</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">3. Oktal (Basis 8)</span></h3>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Oktal atau sistem bilangan basis 8 adalah sebuah sistem bilangan berbasis delapan. Simbol yang digunakan pada sistem ini adalah 0,1,2,3,4,5,6,7. Konversi Sistem Bilangan Oktal berasal dari Sistem bilangan biner yang dikelompokkan tiap tiga bit biner dari ujung paling kanan (LSB atau Least Significant Bit).</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">4. Hexadesimal (Basis 16)</span></h3>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Heksadesimal (Hexadecimal) (berasal dari kata hexa (enam) ditambah decimal (sepuluh)) adalah sebuah sistem bilangan berbasis 16 yang menggunakan 16 simbol. Simbol yang digunakan adalah 10 digit bilangan angka yaitu 0, 1, 2, 3, 4, 5, 6, 7, 8, dan 9 ditambah dengan 6 simbol huruf yaitu huruf A hingga F.</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-family: &quot;verdana&quot; , sans-serif;\">\n" +
                        " Sudah paham kan tentang sistem bilangan yang diterapkan dalam komputer? Perbanyak latihan juga untuk mempermudah pemahaman materi matematika kamu yaaa\n" +
                        " </span> \n" +
                        "</div>\n" +
                        "\n" +
                        "\n");
        materiList.add(a);
        adapter.notifyDataSetChanged();
    }

    private void prepareMateriBahasaInggris() {
        int[] covers = new int[]{
                R.drawable.lecturer,
                R.drawable.english
        };
        MateriList a = new MateriList(
                "Simple Present Tense",
                "Sentence",
                "Bahasa Inggris",
                "Simple present tense adalah suatu bentuk kata kerja untuk menyatakan fakta, kebiasaan, atau kejadian yang terjadi pada saat ini. Bentuk kata kerja ini paling sering digunakan dalam bahasa Inggris. Yul kita pelajari!",
                covers[1],
                covers[0],
                "EN001",
                "<div style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">\n" +
                        "Simple Present Tense adalah bentuk tense yang digunakan untuk menjelaskan sesuatu yang terjadi saat ini (present) pada suatu spesifik waktu. Karena terjadi pada waktu sekarang, maka pasti menggunakan kata kerja pertama (verb 1) dalam susunan kalimatnya.</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Subject Verb-Agreement</span></h3>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Meskipun formulanya terlihat mudah, banyak dari pengguna simple tense tidak teliti dengan subject-verb agreement pada kalimat yang mereka gunakan. Subject-verb agreement adalah ketentuan pemasangan subjek dan kata kerja yang mengikutinya. Untuk kalimat simple present tense, subject-verb agreement yang harus diikuti adalah sebagai berikut:</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Kalimat Positif</span></h3>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Untuk subjek jamak kata kerja tidak diikuti imbuhan s/es.</span>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Sedangkan subjek tunggal kata kerja diikuti imbuhan s/es.</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Kalimat Negatif</span></h3>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Untuk subjek jamak, do not atau don’t digunakan sebelum kata kerja.</span>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Sedangkan subjek tunggal, does not atau doesn’t digunakan sebelum kata kerja.</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Kalimat Interogatif</span></h3>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Untuk subjek jamak, kata do mengawali kalimat.</span>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Sedangkan subjek tunggal, does mengawali kalimat.</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Demikianlah penjelasan singkat mengenai simple present tense. Jenis tense ini merupakan jenis paling dasar dan paling sering digunakan dalam penulisan atau percakapan sehari-hari bahasa Inggris. Apabila kita mengerti  dan bisa mengaplikasikan materi simple present tense ini dengan baik, bisa dikatakan bahwa kita sudah menapakkan satu langkah kita dalam pintu sukses berbicara bahasa Inggris!\n" +
                        " </span> \n" +
                        "</div>\n" +
                        "\n" +
                        "\n");
        materiList.add(a);

        MateriList a1 = new MateriList(
                "Simple Past Tense",
                "Sentence",
                "Bahasa Inggris",
                "Simple past tense adalah kalimat tenses yang digunakan untuk menyatakan kejadian yang terjadi di masa lampau dan telah berakhir di masa lampau. Untuk mengenal mengenai konsep tense lebih lanjut, baca materi ini ya!",
                covers[1],
                covers[0],
                "EN002",
                "<div style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Simple Past tense adalah suatu bentuk tense yang menggambarkan suatu kejadian yang terjadi pada satu spesifik waktu di masa lampau. Kata kerja yang digunakan pada tense ini harus berupa kata kerja kedua (verb 2). Serupa dengan simple present tense, bentuk ini merupakan salah satu tense yang paling dasar dan sering digunakan pada saat penulisan atau percakapan dalam bahasa Inggris.</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Rumus Simple Past Tense</span></h3>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Tense ini terdiri dari gabungan aspek simple (pada satu spesifik waktu) dan bingkai waktu past (masa lampau).</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Kalimat Positif</span></h3>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Subject + verb 2 + object</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Kalimat Negatif</span></h3>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Subject + did not + infinitive verb + object\n" +
                        "</span>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Subject + was/were + not + adjective/adverb</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Kalimat Interogatif</span></h3>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\"> Did + subject + infinitive verb + object?</span>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Was/were + subject + adjective/adverb?</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<h3 style=\"text-align: justify;\">\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Kata Kerja Kedua</span></h3>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Pada dasarnya, simple past tense memiliki struktur yang sama dengan simple present tense. Hal yang membedakan keduanya adalah waktu dan kata kerja yang digunakan. Bentuk kata kerja yang digunakan pada simple past tense harus berupa bentuk kata kerja kedua atau kata kerja masa lampau, yang terdiri dari dua jenis sebagai berikut:</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\"><b>Regular Verbs</b>. Ditambahkan imbuhan d/ed pada akhir kata kerja dasar.</span>\n" +
                        "<br>\n" +
                        "<span style=\"font-family: &quot;verdana&quot; , sans-serif;\"><b>Irregular Verbs</b>. Arti irregular di sini adalah kata kerja tidak mengikuti formula baku d/ed dan mempunyai bentuk kedua sendiri</span>\n" +
                        "</div>\n" +
                        "\n" +
                        "<br>\n" +
                        "<div class=\"MsoNormal\" style=\"text-align: justify;\"><span style=\"font-family: &quot;verdana&quot; , sans-serif;\">Pada umumnya, penggunaan simple past tense sama dengan penggunaan simple present tense. Yang membedakan hanyalah bahwa aksi atau peristiwa terkait sudah terjadi di masa lampau dan tidak terjadi lagi pada masa sekarang. Hal inilah yang harus selalu diingat pada saat menggunakan bentuk tense ini!\n" +
                        " </span> \n" +
                        "</div>\n" +
                        "\n" +
                        "\n");
        materiList.add(a1);

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}