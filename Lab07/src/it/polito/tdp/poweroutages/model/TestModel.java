package it.polito.tdp.poweroutages.model;

public class TestModel {

	public static void main(String[] args) {
		int anni=2;
		int ore=3;
		Model model = new Model( anni, ore);
		
		System.out.println(model.getSoluzioneOttimale());
		//System.out.println(model.getNercList());
	}
}
