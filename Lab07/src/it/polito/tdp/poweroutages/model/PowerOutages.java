package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PowerOutages {
	private int id;
	private Nerc nerc;
	private int customers_effected;
	private LocalDateTime date_began;
	private LocalDateTime date_finished;
	private long durataOre;
	private long durataAnni;

	public PowerOutages(int id, Nerc nerc, int customers_effected, LocalDateTime date_began,
			LocalDateTime date_finished) {
		super();
		this.id = id;
		this.nerc = nerc;
		this.customers_effected = customers_effected;
		this.date_began = date_began;
		this.date_finished = date_finished;
		durataOre = Duration.between(date_began, date_finished).toHours();
		durataAnni = date_finished.until(date_began, ChronoUnit.YEARS);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + customers_effected;
		result = prime * result + ((date_began == null) ? 0 : date_began.hashCode());
		result = prime * result + ((date_finished == null) ? 0 : date_finished.hashCode());
		result = prime * result + (int) (durataAnni ^ (durataAnni >>> 32));
		result = prime * result + (int) (durataOre ^ (durataOre >>> 32));
		result = prime * result + id;
		result = prime * result + ((nerc == null) ? 0 : nerc.hashCode());
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
		PowerOutages other = (PowerOutages) obj;
		if (customers_effected != other.customers_effected)
			return false;
		if (date_began == null) {
			if (other.date_began != null)
				return false;
		} else if (!date_began.equals(other.date_began))
			return false;
		if (date_finished == null) {
			if (other.date_finished != null)
				return false;
		} else if (!date_finished.equals(other.date_finished))
			return false;
		if (durataAnni != other.durataAnni)
			return false;
		if (durataOre != other.durataOre)
			return false;
		if (id != other.id)
			return false;
		if (nerc == null) {
			if (other.nerc != null)
				return false;
		} else if (!nerc.equals(other.nerc))
			return false;
		return true;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public int getCustomers_effected() {
		return customers_effected;
	}

	public LocalDateTime getDate_began() {
		return date_began;
	}

	public LocalDateTime getDate_finished() {
		return date_finished;
	}

	public long getDurataOre() {
		return durataOre;
	}

	public Integer getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PowerOutages [customers_effected=" + customers_effected + ", date_began=" + date_began+", date_finished=" + date_finished + ", durataOre=" + durataOre + "] \n";
	}

}
