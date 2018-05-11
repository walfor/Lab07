package it.polito.tdp.poweroutages.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.NercIdMap;
import it.polito.tdp.poweroutages.model.PowerOutages;
import it.polito.tdp.poweroutages.model.PowerOutagesIdMap;

public class PowerOutageDAO {

	public List <Nerc> getNercList(NercIdMap nercidmap) {
		
		ArrayList<Nerc> listaNerc = new ArrayList<Nerc>();

		String sql = "SELECT id, value FROM nerc";
		

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				listaNerc.add(nercidmap.get(n));
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return listaNerc;
	}

	public List <PowerOutages> getInformation(Nerc nerc,PowerOutagesIdMap poweroutagesidmap,NercIdMap nercIdMap) {

		List<PowerOutages> powerOutages= new ArrayList<PowerOutages>();
		String sql = "SELECT id,customers_affected,date_event_began,date_event_finished FROM poweroutages  WHERE nerc_id= ? ORDER BY date_event_began,date_event_finished";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, nerc.getId());
			ResultSet res = st.executeQuery();
			while (res.next()) {

				int id = res.getInt("id");
				int customers_affected = res.getInt("customers_affected");
				LocalDateTime date_event_began = res.getTimestamp("date_event_began").toLocalDateTime();
				LocalDateTime date_event_finished = res.getTimestamp("date_event_finished").toLocalDateTime();
				
				
				
				
				 PowerOutages p=poweroutagesidmap.get( new PowerOutages(id, nerc, customers_affected, date_event_began, date_event_finished) );
				
				nercIdMap.get(nerc).addPowerOutages(p);
				
				powerOutages.add(p);
				
				

			}

			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return powerOutages;

	}
}
