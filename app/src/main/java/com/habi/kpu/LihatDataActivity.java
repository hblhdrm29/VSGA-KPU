package com.habi.kpu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LihatDataActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listView;
    private List<Pemilih> pemilihList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);

        listView = findViewById(R.id.listVieww);
        databaseHelper = new DatabaseHelper(this);
        tampilkanData();
    }

    private void tampilkanData() {
        try {
            pemilihList = databaseHelper.getAllPemilih();
            Log.d("LihatDataActivity", "Jumlah data: " + pemilihList.size());

            if (!pemilihList.isEmpty()) {
                ArrayAdapter<Pemilih> adapter = new ArrayAdapter<Pemilih>(
                        this,
                        android.R.layout.simple_list_item_2,
                        android.R.id.text1,
                        pemilihList
                ) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView text1 = view.findViewById(android.R.id.text1);
                        TextView text2 = view.findViewById(android.R.id.text2);

                        Pemilih pemilih = getItem(position);
                        if (pemilih != null) {
                            text1.setText(pemilih.getNama() != null ? pemilih.getNama() : "Nama tidak tersedia");
                            text2.setText(pemilih.getNik() != null ? pemilih.getNik() : "NIK tidak tersedia");
                        } else {
                            text1.setText("Data tidak tersedia");
                            text2.setText("");
                        }

                        view.setOnLongClickListener(v -> {
                            showDeleteConfirmation(pemilih);
                            return true;
                        });

                        view.setOnClickListener(v -> {
                            Intent intent = new Intent(LihatDataActivity.this, DetailPemilih.class);
                            intent.putExtra("NIK", pemilih.getNik());
                            intent.putExtra("NAMA", pemilih.getNama());
                            intent.putExtra("NO_HP", pemilih.getNoHp());
                            intent.putExtra("JENIS_KELAMIN", pemilih.getJenisKelamin());
                            intent.putExtra("TANGGAL", pemilih.getTanggal());
                            intent.putExtra("ALAMAT", pemilih.getAlamat());
                            intent.putExtra("LOKASI", pemilih.getLokasi());
                            startActivity(intent);
                        });

                        return view;
                    }
                };

                listView.setAdapter(adapter);
            } else {
                Toast.makeText(this, "Tidak ada data untuk ditampilkan", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("LihatDataActivity", "Error saat menampilkan data: " + e.getMessage());
            Toast.makeText(this, "Terjadi kesalahan saat memuat data", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteConfirmation(Pemilih pemilih) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hapus Data Pemilih");
        builder.setMessage("Apakah Anda yakin ingin menghapus data pemilih dengan NIK " + pemilih.getNik() + "?");
        builder.setPositiveButton("Ya", (dialog, which) -> {
            if (databaseHelper.deletePemilih(pemilih.getNik()) > 0) {
                Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                tampilkanData();

            } else {
                Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Tidak", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}
