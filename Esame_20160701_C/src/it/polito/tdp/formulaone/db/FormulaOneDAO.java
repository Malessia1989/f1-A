package it.polito.tdp.formulaone.db;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.formulaone.model.Circuit;
import it.polito.tdp.formulaone.model.Constructor;
import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.Season;
import it.polito.tdp.formulaone.model.SquadrePiloti;


public class FormulaOneDAO {

	public List<Integer> getAllYearsOfRace() {
		
		String sql = "SELECT year FROM races ORDER BY year" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Integer> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(rs.getInt("year"));
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public List<Season> getAllSeasons() {
		
		String sql = "SELECT year, url FROM seasons ORDER BY year" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Season> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(new Season(Year.of(rs.getInt("year")), rs.getString("url"))) ;
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Circuit> getAllCircuits() {

		String sql = "SELECT circuitId, name FROM circuits ORDER BY name";

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Circuit> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Circuit(rs.getInt("circuitId"), rs.getString("name")));
			}

			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
	public List<Constructor> getAllConstructors() {

		String sql = "SELECT constructorId, name FROM constructors ORDER BY name";

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			List<Constructor> constructors = new ArrayList<>();
			while (rs.next()) {
				constructors.add(new Constructor(rs.getInt("constructorId"), rs.getString("name")));
			}

			conn.close();
			return constructors;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}


	public static void main(String[] args) {
		FormulaOneDAO dao = new FormulaOneDAO() ;
		
		List<Integer> years = dao.getAllYearsOfRace() ;
		System.out.println(years);
		
		List<Season> seasons = dao.getAllSeasons() ;
		System.out.println(seasons);

		
		List<Circuit> circuits = dao.getAllCircuits();
		System.out.println(circuits);

		List<Constructor> constructors = dao.getAllConstructors();
		System.out.println(constructors);
		
	}


	public List<SquadrePiloti> getCoppiePiloti(Map<Integer, Driver> idMap, Constructor c) {
		final String sql=	"select r1.driverId as idP1, r2.driverId as idP2, count(*) as cnt " + 
							"from results as r1, results as r2 " + 
							"where r1.raceId = r2.raceId " + 
							"and r1.constructorId=r2.constructorId " + 
							"and r1.constructorId=? " + 
							"and r1.driverId > r2.driverId " + 
							"group by idP1,idP2 ";
		
		List<SquadrePiloti> pilotiComuni = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, c.getConstructorId());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				
				Driver d1 = idMap.get(rs.getInt("idP1"));
				Driver d2 = idMap.get(rs.getInt("idP2"));
				
				if(d1==null || d2==null) {
					throw new RuntimeException();
				}
				
				SquadrePiloti sp = new SquadrePiloti(d1, d2, rs.getDouble("cnt"));
				pilotiComuni.add(sp);
			}

			conn.close();
			return pilotiComuni;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
		
	}

	public List<Driver> getallDrivers(Map<Integer, Driver> idMap) {
		final String sql=	"select d.driverId as id " + 
							"from drivers as d " + 
							"order by d.driverId";
		
		List<Driver> drivers = new LinkedList<>();
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				if(!idMap.containsKey(rs.getInt("id"))){
					Driver d = new Driver(rs.getInt("id"));
					idMap.put(rs.getInt("id"), d);
					drivers.add(d);
				}else {
					drivers.add(idMap.get(rs.getInt("id")));
				}
			}

			conn.close();
			return drivers;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Query Error");
		}
	}
	
}
