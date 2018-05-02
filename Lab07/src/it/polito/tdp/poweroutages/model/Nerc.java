package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

public class Nerc {

	private int id;
	private String value;
	
	private List<PowerOutages> blackout = new ArrayList();

	public Nerc(int id, String value) {
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nerc other = (Nerc) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		builder.append(this.id);
		return builder.toString();
	}
	
	public void addPowerOutages(PowerOutages p) {
		if(!this.blackout.contains(p)) {
			this.blackout.add(p);
		}
	}
	
}
