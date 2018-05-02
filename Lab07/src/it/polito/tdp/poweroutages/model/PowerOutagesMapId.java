package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PowerOutagesMapId {
	
	public PowerOutagesMapId(){
		powerOutagesMapId= new HashMap<Integer, PowerOutages>();
	}
	private Map<Integer,PowerOutages> powerOutagesMapId;

	public Map<Integer, PowerOutages> getPowerOutagesMapId() {
		return powerOutagesMapId;
	}

	
	public  PowerOutages getPowerOutagesFromNerc(PowerOutages powerOutages) {
		
		if(this.powerOutagesMapId.get(powerOutages.getId())==null) {
			this.powerOutagesMapId.put(powerOutages.getId(), powerOutages);
			
			return powerOutages;
		}
		
		return this.powerOutagesMapId.get(powerOutages.getId());
		
		
	}
	
	public void put(PowerOutages p) {
			this.powerOutagesMapId.put(p.getId(), p);
		}

	public List<PowerOutages> listaPowerOutages(){

		 List<PowerOutages> listaPowerOutages= new ArrayList<PowerOutages>();
		for(PowerOutages p :this.powerOutagesMapId.values()) {
			listaPowerOutages.add(p);
		}
		
		return listaPowerOutages;
	}
	
	
	
	
	
	
	

}
