package com.pactera.predix.seed.data.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LatheEvent implements Serializable {

	private static final long serialVersionUID = -9056051739768901114L;

	private String category;
	private String lable;
	private String value;
	private String latheCode;

	public String getLatheCode() {
		return latheCode;
	}

	public void setLatheCode(String latheCode) {
		this.latheCode = latheCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
