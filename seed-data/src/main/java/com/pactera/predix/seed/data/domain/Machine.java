package com.pactera.predix.seed.data.domain;

import java.io.Serializable;

public class Machine implements Serializable {

	private static final long serialVersionUID = 4557265459030359225L;

	private String latheCode;
	private int status;
	
	private String partNumber;
	private String procedure;
	

	public String getLatheCode() {
		return latheCode;
	}

	public void setLatheCode(String latheCode) {
		this.latheCode = latheCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getProcedure() {
		return procedure;
	}

	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}

}
