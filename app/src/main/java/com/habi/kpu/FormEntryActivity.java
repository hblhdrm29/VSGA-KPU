package com.habi.kpu;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;

public class FormEntryActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    EditText etNIK, etNama, etNoHp, etTanggal, etAlamat;
    RadioGroup rgJenisKelamin;
    Button btnCekLokasi, btnSubmit;
    TextView tvLokasi;
    String jenisKelamin;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_entry);

        databaseHelper = new DatabaseHelper(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        etNIK = findViewById(R.id.etNIK);
        etNama = findViewById(R.id.etNama);
        etNoHp = findViewById(R.id.etNoHp);
        rgJenisKelamin = findViewById(R.id.rgJenisKelamin);
        etTanggal = findViewById(R.id.etTanggal);
        etAlamat = findViewById(R.id.etAlamat);
        btnCekLokasi = findViewById(R.id.btnCekLokasi);
        tvLokasi = findViewById(R.id.etLokasi);
        btnSubmit = findViewById(R.id.btnSubmit);

        rgJenisKelamin.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = findViewById(checkedId);
            jenisKelamin = rb.getText().toString();
        });

        etTanggal.setOnClickListener(v -> showDatePickerDialog());

        btnCekLokasi.setOnClickListener(v -> cekLokasi());

        btnSubmit.setOnClickListener(v -> {
            String nik = etNIK.getText().toString();
            String nama = etNama.getText().toString();
            String noHp = etNoHp.getText().toString();
            String tanggal = etTanggal.getText().toString();
            String alamat = etAlamat.getText().toString();
            String lokasi = tvLokasi.getText().toString();

            if (nik.isEmpty() || nama.isEmpty() || noHp.isEmpty() || tanggal.isEmpty() || alamat.isEmpty() || lokasi.isEmpty()) {
                Toast.makeText(this, "Harap lengkapi semua field", Toast.LENGTH_SHORT).show();
                return;

            }

            Pemilih pemilih = new Pemilih(nik, nama, noHp, jenisKelamin, tanggal, alamat, lokasi);
            long result = databaseHelper.addPemilih(pemilih);

            if (result > 0) {
                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            etTanggal.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    // Implementasi DatePickerDialog di sini

    private void cekLokasi() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        String lokasiText = "Lat: " + latitude + ", Lon: " + longitude;
                        tvLokasi.setText(lokasiText);
                        Toast.makeText(this, "Lokasi berhasil diambil", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Tidak dapat mengambil lokasi", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cekLokasi();
            } else {
                Toast.makeText(this, "Izin lokasi ditolak", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
