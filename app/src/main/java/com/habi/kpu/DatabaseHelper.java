package com.habi.kpu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "kpu.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_PEMILIH = "pemilih";
    private static final String KEY_ID = "id";
    private static final String KEY_NIK = "nik";
    private static final String KEY_NAMA = "nama";
    private static final String KEY_NO_HP = "no_hp";
    private static final String KEY_JENIS_KELAMIN = "jenis_kelamin";
    private static final String KEY_TANGGAL = "tanggal";
    private static final String KEY_ALAMAT = "alamat";
    private static final String KEY_LOKASI = "lokasi";

    private String lastError = "";

    public String getLastError() {
        return lastError;
    }
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DatabaseHelper", "Creating table...");
        String CREATE_TABLE = "CREATE TABLE " + TABLE_PEMILIH + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NIK + " TEXT,"
                + KEY_NAMA + " TEXT,"
                + KEY_NO_HP + " TEXT,"
                + KEY_JENIS_KELAMIN + " TEXT,"
                + KEY_TANGGAL + " TEXT,"
                + KEY_ALAMAT + " TEXT,"
                + KEY_LOKASI + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
        Log.d("DatabaseHelper", "Table created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEMILIH);
        onCreate(db);
    }

    public long addPemilih(Pemilih pemilih) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NIK, pemilih.getNik());
        values.put(KEY_NAMA, pemilih.getNama());
        values.put(KEY_NO_HP, pemilih.getNoHp());
        values.put(KEY_JENIS_KELAMIN, pemilih.getJenisKelamin());
        values.put(KEY_TANGGAL, pemilih.getTanggal());
        values.put(KEY_ALAMAT, pemilih.getAlamat());
        values.put(KEY_LOKASI, pemilih.getLokasi());

        long result;
        try {
            result = db.insert(TABLE_PEMILIH, null, values);
            Log.d("DatabaseHelper", "Hasil insert: " + result); // Mencatat hasil
            return result;
        } catch (SQLiteException e) {
            lastError = "Gagal menyimpan data: " + e.getMessage();
            Log.e("DatabaseHelper", lastError);
            return -1;
        } finally {
            db.close();
        }
    }



    public List<Pemilih> getAllPemilih() {
        List<Pemilih> pemilihList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PEMILIH, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int nikIndex = cursor.getColumnIndex(KEY_NIK);
                int namaIndex = cursor.getColumnIndex(KEY_NAMA);
                int noHpIndex = cursor.getColumnIndex(KEY_NO_HP);
                int jenisKelaminIndex = cursor.getColumnIndex(KEY_JENIS_KELAMIN);
                int tanggalIndex = cursor.getColumnIndex(KEY_TANGGAL);
                int alamatIndex = cursor.getColumnIndex(KEY_ALAMAT);
                int lokasiIndex = cursor.getColumnIndex(KEY_LOKASI);

                // Cek jika semua kolom yang diperlukan ada
                if (nikIndex != -1 && namaIndex != -1) {
                    String nik = cursor.getString(nikIndex);
                    String nama = cursor.getString(namaIndex);
                    String noHp = (noHpIndex != -1) ? cursor.getString(noHpIndex) : null;
                    String jenisKelamin = (jenisKelaminIndex != -1) ? cursor.getString(jenisKelaminIndex) : null;
                    String tanggal = (tanggalIndex != -1) ? cursor.getString(tanggalIndex) : null;
                    String alamat = (alamatIndex != -1) ? cursor.getString(alamatIndex) : null;
                    String lokasi = (lokasiIndex != -1) ? cursor.getString(lokasiIndex) : null;

                    Pemilih pemilih = new Pemilih(nik, nama, noHp, jenisKelamin, tanggal, alamat, lokasi);
                    pemilihList.add(pemilih);
                } else {
                    Log.e("DatabaseHelper", "Kolom tidak ditemukan");
                }
            } while (cursor.moveToNext());
        }

        cursor.close(); // Menutup cursor
        db.close(); // Menutup database setelah query
        return pemilihList; // Mengembalikan list pemilih
    }
    public int deletePemilih(String nik) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_PEMILIH, KEY_NIK + " = ?", new String[] { nik });
        db.close();
        return rowsDeleted;
    }
}

