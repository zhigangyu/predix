package com.pactera.predix.seed.data.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Lathe implements Serializable {

	private static final long serialVersionUID = 136780493247786437L;


	private int id;

	private String latheName;

	private String latheType;
	
	private int status;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLatheName() {
		return latheName;
	}

	public void setLatheName(String latheName) {
		this.latheName = latheName;
	}

	public String getLatheType() {
		return latheType;
	}

	public void setLatheType(String latheType) {
		this.latheType = latheType;
	}

}
