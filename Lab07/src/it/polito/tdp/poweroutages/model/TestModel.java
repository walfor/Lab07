package it.polito.tdp.poweroutages.model;

public class TestModel {

	public static void main(String[] args) {
		int anni=4;
		int ore=200;
		Model model = new Model( );
		
		Nerc nerc = model.getListaNerc().get(3);
		
		model.start(nerc,anni, ore);
		
		//System.out.println(model.getSoluzioneOttimale());
		//System.out.println(model.getNercList());
	}
}
