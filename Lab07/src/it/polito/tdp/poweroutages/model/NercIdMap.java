package it.polito.tdp.poweroutages.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class NercIdMap {
	private Map<Integer, Nerc> nercidmap = new HashMap<Integer, Nerc>();

	
	
	public Nerc get(Nerc nerc) {
		
		if(this.nercidmap.containsKey(nerc.getId())) {
			return this.nercidmap.get(nerc.getId());
		}else { // se non preseente nella mappa lo aggiungo
			this.nercidmap.put(nerc.getId(), nerc);
			return nerc;
		}
	}
	
	
	public void put(Nerc nerc) {
		this.nercidmap.put(nerc.getId(), nerc);
		
	}
	
	
	
	
	public Collection<Nerc> listaNerc(){
		return this.nercidmap.values();
	}
}
