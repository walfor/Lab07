package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.List;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	private PowerOutageDAO podao;

	private int DURATA_ORE_MAX;
	private int DURATA_ANNI_MAX;

	private List<PowerOutages> soluzioneParziale;
	private List<PowerOutages> soluzioneOttimale;
	private PowerOutagesIdMap poweroutagesmapid;
	private NercIdMap nercidmap;
	private List<Nerc> listaNerc; // lista nerc che posso richiamare da test model.

	public Model() {

		podao = new PowerOutageDAO();

		poweroutagesmapid = new PowerOutagesIdMap();
		nercidmap = new NercIdMap();

		this.listaNerc = podao.getNercList(nercidmap);

	}

	public void start(Nerc nerc, int X, int Y) {
		this.DURATA_ANNI_MAX = X;
		this.DURATA_ORE_MAX = Y;

		this.soluzioneParziale = new ArrayList<PowerOutages>();
		this.soluzioneOttimale = new ArrayList<PowerOutages>();

		List<PowerOutages> listaPowerOutages = podao.getInformation(nerc, poweroutagesmapid, this.nercidmap);

		recursive(this.soluzioneParziale, nerc, listaPowerOutages);

		// System.out.println("Tot people effectd : " +
		// this.contaPersone(soluzioneOttimale));
		// System.out.println("Tot hours of outage: " +
		// this.contaOre(soluzioneOttimale));
		// System.out.println(soluzioneOttimale);
	}

	public void recursive(List<PowerOutages> soluzioneParziale, Nerc nerc, List<PowerOutages> listaPowerOutages) {

		// System.out.println(soluzioneParziale);

		if (contaPersone(soluzioneParziale) > contaPersone(soluzioneOttimale)) {

			soluzioneOttimale = new ArrayList<PowerOutages>(soluzioneParziale);

		}

		for (PowerOutages p : listaPowerOutages) {

			if (aggiuntaValida(p, soluzioneParziale, nerc) && !soluzioneParziale.contains(p)) {

				soluzioneParziale.add(p);
				recursive(soluzioneParziale, nerc, listaPowerOutages);
				soluzioneParziale.remove(p);

			}

		}

	}

	public List<PowerOutages> getSoluzioneOttimale() {
		return soluzioneOttimale;
	}

	private boolean aggiuntaValida(PowerOutages p, List<PowerOutages> soluzioneParziale, Nerc nerc) {

		if (soluzioneParziale.size() >= 2) {
			LocalDateTime date_old = soluzioneParziale.get(0).getDate_began();
			LocalDateTime date_young = soluzioneParziale.get(soluzioneParziale.size() - 1).getDate_finished();

			if ((getDurataAnni(date_young, date_old) + 1) > DURATA_ANNI_MAX) {
				return false;
			}

			if (!p.getDate_began().isAfter(soluzioneParziale.get(soluzioneParziale.size() - 1).getDate_began())) {
				return false;
			}

		}

		if (contaOre(soluzioneParziale)+p.getDurataOre() > DURATA_ORE_MAX)
			return false;

		return true;
	}

	public List<Nerc> getListaNerc() {
		return listaNerc;
	}

	public long getDurataAnni(LocalDateTime date_young, LocalDateTime date_old) {
		long durataAnni = date_old.until(date_young, ChronoUnit.YEARS);
		return durataAnni;
	}

	public int contaOre(List<PowerOutages> soluzioneParziale) {
		int hours = 0;
		for (PowerOutages p : soluzioneParziale) {
			hours += p.getDurataOre();
		}
		return hours;
	}

	public int contaPersone(List<PowerOutages> soluzione) {
		int count = 0;
		for (PowerOutages p : soluzione) {
			count += p.getCustomers_effected();
		}
		return count;
	}

	public String ListToString(List<PowerOutages> lista) {
		String result = "";
		for (PowerOutages p : lista) {
			result += p.toString() + "\n";
		}

		return result.substring(0, result.length() - 1);
	}
}
