package com.pactera.predix.seed.data.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LatheEventValue implements Serializable {

	private static final long serialVersionUID = 8786664600454709844L;

	private String lable;
	private String value;

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
