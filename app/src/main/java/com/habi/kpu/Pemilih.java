package com.habi.kpu;

public class Pemilih {
    private String nik;
    private String nama;
    private String noHp;
    private String jenisKelamin;
    private String tanggal;
    private String alamat;
    private String lokasi;

    // Constructor
    public Pemilih(String nik, String nama, String noHp, String jenisKelamin, String tanggal, String alamat, String lokasi) {
        this.nik = nik;
        this.nama = nama;
        this.noHp = noHp;
        this.jenisKelamin = jenisKelamin;
        this.tanggal = tanggal;
        this.alamat = alamat;
        this.lokasi = lokasi;
    }

    // Getter methods
    public String getNik() { return nik; }
    public String getNama() { return nama; }
    public String getNoHp() { return noHp; }
    public String getJenisKelamin() { return jenisKelamin; }
    public String getTanggal() { return tanggal; }
    public String getAlamat() { return alamat; }
    public String getLokasi() { return lokasi; }
}
