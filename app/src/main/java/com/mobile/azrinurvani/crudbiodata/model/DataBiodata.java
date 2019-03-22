package com.mobile.azrinurvani.crudbiodata.model;


import com.google.gson.annotations.SerializedName;


public class DataBiodata {

	@SerializedName("jekel")
	private String jekel;

	@SerializedName("nama")
	private String nama;

	@SerializedName("id")
	private String id;

	@SerializedName("hobi")
	private String hobi;

	@SerializedName("alamat")
	private String alamat;

	public void setJekel(String jekel){
		this.jekel = jekel;
	}

	public String getJekel(){
		return jekel;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setHobi(String hobi){
		this.hobi = hobi;
	}

	public String getHobi(){
		return hobi;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}
}