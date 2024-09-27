package com.habi.kpu;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailPemilih extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemilih);

        TextView tvNik = findViewById(R.id.tvNik);
        TextView tvNama = findViewById(R.id.tvNama);
        TextView tvNoHp = findViewById(R.id.tvNoHp);
        TextView tvJenisKelamin = findViewById(R.id.tvJenisKelamin);
        TextView tvTanggal = findViewById(R.id.tvTanggal);
        TextView tvAlamat = findViewById(R.id.tvAlamat);
        TextView tvLokasi = findViewById(R.id.tvLokasi);

        // Get data from intent
        String nik = getIntent().getStringExtra("NIK");
        String nama = getIntent().getStringExtra("NAMA");
        String noHp = getIntent().getStringExtra("NO_HP");
        String jenisKelamin = getIntent().getStringExtra("JENIS_KELAMIN");
        String tanggal = getIntent().getStringExtra("TANGGAL");
        String alamat = getIntent().getStringExtra("ALAMAT");
        String lokasi = getIntent().getStringExtra("LOKASI");

        // Set data to views
        tvNik.setText("NIK: " + nik);
        tvNama.setText("Nama: " + nama);
        tvNoHp.setText("No HP: " + noHp);
        tvJenisKelamin.setText("Jenis Kelamin: " + jenisKelamin);
        tvTanggal.setText("Tanggal: " + tanggal);
        tvAlamat.setText("Alamat: " + alamat);
        tvLokasi.setText("Lokasi: "+ lokasi);

    }
}
