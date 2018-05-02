package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	PowerOutageDAO podao;
	private List<Nerc> nerclist;
	private int step;
	private int DURATA_ORE_MAX;
	private int DURATA_ANNI_MAX;
	private int ottimo = 0;

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
			for (PowerOutages p : listaBlack) {
				this.poweroutageid.put(p);
				nerc.addPowerOutages(p);
			}
		}

		step = 0;
		this.soluzioneParziale = new ArrayList<PowerOutages>();
		this.soluzioneOttimale = new ArrayList<PowerOutages>();

		recursive(step, soluzioneParziale);

	}

	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public void recursive(int step, List<PowerOutages> soluzioneParziale) {

		 System.out.println(soluzioneParziale);

		if (contaPersone(soluzioneParziale) > contaPersone(soluzioneOttimale)) {

			soluzioneOttimale = new ArrayList<PowerOutages>(soluzioneParziale);
			// ottimo = contaPersone(soluzioneOttimale);

		}

		for (PowerOutages p : this.poweroutageid.listaPowerOutages()) {

			if (aggiuntaValida(p, soluzioneParziale) && !soluzioneParziale.contains(p)) {

				soluzioneParziale.add(p);
				recursive(step + 1, soluzioneParziale);
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

	private boolean aggiuntaValida(PowerOutages p, List<PowerOutages> soluzioneParziale) {

		if (soluzioneParziale.size() == 0 || soluzioneParziale.size() == 1)
			return true;

		LocalDateTime date_old = soluzioneParziale.get(0).getDate_began();
		LocalDateTime date_young = soluzioneParziale.get(soluzioneParziale.size() - 1).getDate_finished();

		if (getDurataAnni(date_young, date_old) > DURATA_ANNI_MAX) {
			return false;
		}

		if (p.getDurataOre() > DURATA_ORE_MAX)
			return false;

		return true;
	}

	public long getDurataAnni(LocalDateTime date_young, LocalDateTime date_old) {
		long durataAnni = date_old.until(date_young, ChronoUnit.YEARS);
		return durataAnni;
	}

}
