package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.seriea.model.ArcoPersonalizzato;
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

	public void creaGrafo(SimpleWeightedGraph<Season, ArcoPersonalizzato> grafo) {
		
			String sql="select m1.Season s1, m1.HomeTeam ht1, m1.AwayTeam at1, m2.Season s2, m2.HomeTeam ht2, m2.AwayTeam at2\r\n" + 
					"from matches m1, matches m2\r\n" + 
					"where m1.Season > m2.Season and\r\n" + 
					"		(m1.HomeTeam=m2.HomeTeam or\r\n" + 
					"			m1.HomeTeam=m2.AwayTeam or\r\n" + 
					"				m1.AwayTeam=m2.HomeTeam or\r\n" + 
					"					m1.AwayTeam=m2.AwayTeam)";
			
			Connection conn = DBConnect.getConnection();

			try {
				PreparedStatement st = conn.prepareStatement(sql);
				ResultSet res = st.executeQuery();

				while (res.next()) {
					
					//vertici
					Season s1=new Season(res.getInt("s1"));
					Season s2=new Season(res.getInt("s2"));
					
					//peso
					Team ht1=new Team(res.getString("ht1"));
					Team at1=new Team(res.getString("at1"));
					Team ht2=new Team(res.getString("ht2"));
					Team at2=new Team(res.getString("at2"));
					Team teamInComune=null;
					
					if(ht1.equals(ht2)) {
						teamInComune=ht1;
					}
					if(at1.equals(at2)) {
						teamInComune=at1;
					}
					if(ht1.equals(at2)) {
						teamInComune=ht1;
					}
					if(at1.equals(ht2)) {
						teamInComune=at1;
					}
					
					if(!grafo.containsVertex(s1)) {
						grafo.addVertex(s1);
					}
					if(!grafo.containsVertex(s2)) {
						grafo.addVertex(s2);
					}
					//grafo non orientato: doppio controllo
					if(!grafo.containsEdge(s1,s2) && !grafo.containsEdge(s2, s1)) {
						ArcoPersonalizzato arco =grafo.addEdge(s1, s2);
						arco.updatePeso(teamInComune);
					}
					if(grafo.containsEdge(s1,s2)) {
						grafo.getEdge(s1, s2).updatePeso(teamInComune);
					}
					if(grafo.containsEdge(s2,s1)) {
						grafo.getEdge(s2, s1).updatePeso(teamInComune);
					}
					
					
					
				}

				conn.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		
	}

}
