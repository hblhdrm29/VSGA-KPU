package com.habi.kpu;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnInformasi, btnFormEntri, btnLihatData, btnKeluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInformasi = findViewById(R.id.btnInformasi);
        btnFormEntri = findViewById(R.id.btnFormEntri);
        btnLihatData = findViewById(R.id.btnLihatData);
        btnKeluar = findViewById(R.id.btnKeluar);


        btnInformasi.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Informasi Pemilihan");
            builder.setMessage("Ini adalah informasi deskripsi pemilihan dan blabla...");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
            // Buka halaman informasi
        });
        btnInformasi.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Informasi ");
            builder.setMessage("Ini adalah informasi deskripsi pemilihan dan blabla...");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            builder.show().setOnDismissListener(dismissed -> {
                Intent intent = new Intent(MainActivity.this, Informasi.class);
                startActivity(intent);
            });
        });



        btnFormEntri.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FormEntryActivity.class);
            startActivity(intent);
        });

        btnLihatData.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LihatDataActivity.class);
            startActivity(intent);
        });

        btnKeluar.setOnClickListener(v -> {
            finish();  // Keluar dari aplikasi
        });
    }
}
