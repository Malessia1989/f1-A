package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries(Map<Integer,Country> idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(idMap.get(rs.getInt("ccode"))==null) {
					
				Country c=new Country(rs.getString("StateAbb"), rs.getInt("ccode") ,rs.getString("stateNme"));
				result.add(c);
				idMap.put(rs.getInt("ccode"), c);
			}
				else {
					result.add(idMap.get(rs.getInt("ccode")));
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

	public List<Border> getCountryPairs(int anno) {

		System.out.println("TODO -- BordersDAO -- getCountryPairs(int anno)");
		return new ArrayList<Border>();
	}

	public List<Border> getConfini(String annoInput, Map<Integer,Country> idMap ) {

		String sql="SELECT c.state1no s1,c.state2no s2 " + 
				"FROM contiguity c " + 
				"WHERE c.conttype=1 AND c.YEAR <= ? and c.state1no > c.state2no ";
		
		List<Border> confini=new LinkedList<Border>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, Integer.parseInt(annoInput));
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Border b= new Border(idMap.get(rs.getInt("s1")),idMap.get(rs.getInt("s2")));
				confini.add(b);
			}
			
			conn.close();
			return confini;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
		
	}
}
