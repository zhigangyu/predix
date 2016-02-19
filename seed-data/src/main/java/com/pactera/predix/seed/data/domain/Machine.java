package com.pactera.predix.seed.data.domain;

import java.io.Serializable;
import java.util.Date;

public class Machine implements Serializable {

	private static final long serialVersionUID = 4557265459030359225L;

	private String id;
	private String name;
	private String type;
	private int duration;
	private Date start;
	private double efficiency;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}

}
