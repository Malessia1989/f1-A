package it.polito.tdp.newufosightings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.newufosightings.model.Confine;
import it.polito.tdp.newufosightings.model.Sighting;
import it.polito.tdp.newufosightings.model.State;
import it.polito.tdp.newufosightings.model.StatoePeso;

public class NewUfoSightingsDAO {

	public List<Sighting> loadAllSightings() {
		String sql = "SELECT * FROM sighting";
		List<Sighting> list = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new Sighting(res.getInt("id"), res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), res.getString("state"), res.getString("country"), res.getString("shape"),
						res.getInt("duration"), res.getString("duration_hm"), res.getString("comments"),
						res.getDate("date_posted").toLocalDate(), res.getDouble("latitude"),
						res.getDouble("longitude")));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

		return list;
	}

	public List<State> loadAllStates(Map<String, State> idMap) {
		String sql = "SELECT * FROM state";
		List<State> result = new ArrayList<State>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(idMap.get(rs.getString("id"))==null) {
				State state = new State(rs.getString("id"), rs.getString("Name"), rs.getString("Capital"),
						rs.getDouble("Lat"), rs.getDouble("Lng"), rs.getInt("Area"), rs.getInt("Population"),
						rs.getString("Neighbors"));
				result.add(state);
				idMap.put(state.getId(), state);
				}else {
					result.add(idMap.get(rs.getString("id")));
				}
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<String> getAllForme(String anno) {
		String sql="select s.shape forma " + 
				"from sighting s " + 
				"where year(s.datetime)= ? " + 
				"group by s.shape ";
		
		List<String> forme= new LinkedList<>(); 
		
		Connection conn = ConnectDB.getConnection();
		try {			
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, Integer.parseInt(anno));
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String f=rs.getString("forma");
				forme.add(f);
			}

			conn.close();
			return forme;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

	}

	public List<Confine> getConfini(Map<String, State> idMap) {
		
		String sql="select n.state1 state1 , n.state2 state2 " + 
				"from neighbor n " + 
				"where n.state1 > n.state2 " ;
		
		List<Confine> confini= new LinkedList<>();
		
		Connection conn = ConnectDB.getConnection();
		try {			
			PreparedStatement st = conn.prepareStatement(sql);
		
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				State s1= idMap.get(rs.getString("state1"));
				State s2=idMap.get(rs.getString("state2"));
				
				if(s1 == null && s2 == null) {
					throw new RuntimeException("problema con GetConfine!");
				}
				Confine c= new Confine(s1, s2);
				confini.add(c);
				}

			conn.close();
			return confini;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	
	}
	
	public List<StatoePeso> getStatoePeso(String anno, String forma, Map<String, State> idMap){
		
		
		String sql="select st.id state, count(*) as peso " + 
				"from state st, sighting si " + 
				"where st.id=si.state  " + 
				"and year(si.datetime)=? " + 
				"and si.shape=? " + 
				"group by st.id ";	
		
		List<StatoePeso> result= new ArrayList<>();
		
		Connection conn = ConnectDB.getConnection();
		try {			
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, Integer.parseInt(anno));
			st.setString(2, forma);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				State s1=idMap.get(rs.getString("state"));
				double peso=rs.getDouble("peso");
				
				if(s1!= null) {
				StatoePeso sp=new StatoePeso(s1, peso);
				result.add(sp);
				}
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	
	}

}
