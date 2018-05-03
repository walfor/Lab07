package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	PowerOutageDAO podao;
	private List<Nerc> nerclist;

	private int DURATA_ORE_MAX;
	private int DURATA_ANNI_MAX;

	private List<PowerOutages> soluzioneParziale;
	private List<PowerOutages> soluzioneOttimale;
	private PowerOutagesMapId poweroutageid;

	public Model(int X, int Y) {
		this.DURATA_ANNI_MAX = X;
		this.DURATA_ORE_MAX = Y;

		podao = new PowerOutageDAO();
		nerclist = podao.getNercList();
		poweroutageid = new PowerOutagesMapId();

		for (Nerc nerc : this.nerclist) {
			// System.out.println(nerc);
			List<PowerOutages> listaBlack = podao.getInformation(nerc);
			// System.out.println(listaBlack.size());
			for (PowerOutages p : listaBlack) {
				this.poweroutageid.put(p);
				nerc.addPowerOutages(p);
			}
		}

		this.soluzioneParziale = new ArrayList<PowerOutages>();
		this.soluzioneOttimale = new ArrayList<PowerOutages>();
		Nerc nerc = this.nerclist.get(0);

		recursive(soluzioneParziale, nerc);

	}

	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public void recursive(List<PowerOutages> soluzioneParziale, Nerc nerc) {

		// System.out.println(soluzioneParziale);

		if (contaPersone(soluzioneParziale) > contaPersone(soluzioneOttimale)) {

			soluzioneOttimale = new ArrayList<PowerOutages>(soluzioneParziale);

			System.out.println(soluzioneOttimale);
		}

		for (PowerOutages p : this.poweroutageid.listaPowerOutages()) {

			if (aggiuntaValida(p, soluzioneParziale, nerc) && !soluzioneParziale.contains(p)) {

				soluzioneParziale.add(p);
				recursive(soluzioneParziale, nerc);
				soluzioneParziale.remove(p);

			}

		}

	}

	public List<PowerOutages> getSoluzioneOttimale() {
		return soluzioneOttimale;
	}

	private int contaPersone(List<PowerOutages> soluzione) {
		int count = 0;
		for (PowerOutages p : soluzione) {
			count += p.getCustomers_effected();
		}
		return count;
	}

	private boolean aggiuntaValida(PowerOutages p, List<PowerOutages> soluzioneParziale, Nerc nerc) {

		if (soluzioneParziale.size() == 0 || soluzioneParziale.size() == 1)
			return true;

		LocalDateTime date_old = soluzioneParziale.get(0).getDate_began();
		LocalDateTime date_young = soluzioneParziale.get(soluzioneParziale.size() - 1).getDate_finished();

		if (getDurataAnni(date_young, date_old) > DURATA_ANNI_MAX) {
			return false;
		}

		if (p.getDurataOre() > DURATA_ORE_MAX)
			return false;

		if (!nerc.equals(p.getNerc())) {
			return false;
		}

		return true;
	}

	public long getDurataAnni(LocalDateTime date_young, LocalDateTime date_old) {
		long durataAnni = date_old.until(date_young, ChronoUnit.YEARS);
		return durataAnni;
	}

}
