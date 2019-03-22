package com.mobile.azrinurvani.crudbiodata.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class ResponseGetBiodata{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("data_biodata")
	private List<DataBiodata> dataBiodata;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataBiodata(List<DataBiodata> dataBiodata){
		this.dataBiodata = dataBiodata;
	}

	public List<DataBiodata> getDataBiodata(){
		return dataBiodata;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}