package com.angga.auth.dto;

public class RegisterRequest {
  private String username;
  private String password;
  private String email;
  private String nama;
  private String alamat;
  private String jenis_kelamin;

  public RegisterRequest() {
  }

  public RegisterRequest(String username, String password, String email, String nama, String alamat,
      String jenis_kelamin) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.nama = nama;
    this.alamat = alamat;
    this.jenis_kelamin = jenis_kelamin;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNama() {
    return nama;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public String getAlamat() {
    return alamat;
  }

  public void setAlamat(String alamat) {
    this.alamat = alamat;
  }

  public String getJenis_kelamin() {
    return jenis_kelamin;
  }

  public void setJenis_kelamin(String jenis_kelamin) {
    this.jenis_kelamin = jenis_kelamin;
  }
}