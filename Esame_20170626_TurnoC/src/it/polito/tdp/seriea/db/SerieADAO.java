package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.seriea.model.MioGrafo;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {

	public List<Season> listSeasons() {
		String sql = "SELECT season, description FROM seasons";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Season(res.getInt("season"), res.getString("description")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Team> listTeams() {
		String sql = "SELECT team FROM teams";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Team(res.getString("team")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void creaGrafo(MioGrafo grafo) {

		String sql="select fthg as goal1, ftag as goal2, count(*) as peso\r\n " + 
				"from matches\r\n " + 
				"group by goal1, goal2 ";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				int goal1=res.getInt("goal1");
				int goal2=res.getInt("goal2");
				
				
				if(!grafo.containsVertex(goal1)) {
					grafo.addVertex(goal1);
				}
				if(!grafo.containsVertex(goal2)) {
					grafo.addVertex(goal2);
				}
				// orientato: creo e aggiungo l'arco
				//grafo setta il peso
				if(!grafo.containsEdge(goal1,goal2)) {
					DefaultWeightedEdge edge=grafo.addEdge(goal1, goal2);
					grafo.setEdgeWeight(edge,res.getInt("peso"));
				}
				
			}

			conn.close();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
	}

	public Map<String,Integer> getPartiteGoalSelezionato(Integer goalSelezionato) {

		String sql="select m1.FTHG goalHT, m1.FTAG goalAT , count(*) peso\r\n " + 
		"				from matches m1\r\n " + 
		"				where m1.FTHG= ? " + 
		"				group by goalHT, goalAT ";
		
		Map<String ,Integer> map= new HashMap<String,Integer>();
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,goalSelezionato);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				int goal1=res.getInt("goal1");
				int goal2=res.getInt("goal2");
				int peso=res.getInt("peso");
				
				map.put(""+goal1+":" +goal2 ,peso);
				
			}

			conn.close();
		
		} catch (SQLException e) {
			throw new RuntimeException("ERRORE DB");
		}
		return map;
	}

}
