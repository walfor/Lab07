package it.polito.tdp.poweroutages.model;


import java.util.HashMap;

import java.util.Map;

public class PowerOutagesIdMap {
	private Map<Integer, PowerOutages> powerOutagesMapId;

	public PowerOutagesIdMap() {
		powerOutagesMapId = new HashMap<Integer, PowerOutages>();
	}

	public PowerOutages get(PowerOutages powerOutages) {

		if (this.powerOutagesMapId.get(powerOutages.getId()) == null) {
			this.powerOutagesMapId.put(powerOutages.getId(), powerOutages);

			return powerOutages;
		}

		return this.powerOutagesMapId.get(powerOutages.getId());

	}

	public void put(PowerOutages p) {
		this.powerOutagesMapId.put(p.getId(), p);
	}

	

}
