package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.sun.javafx.geom.Edge;

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

	public void popolaGrafo(SimpleWeightedGraph<Team, DefaultWeightedEdge> grafo) {
		String sql = "select hometeam as ht, awayteam as at,  (count(*))*2 as peso\r\n" + "from matches\r\n"
				+ "group by ht,at";

		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {

				Team t1 = new Team(res.getString("ht"));
				Team t2 = new Team(res.getString("at"));

				if (!grafo.containsVertex(t1)) {
					grafo.addVertex(t1);
				}
				if (!grafo.containsVertex(t2)) {
					grafo.addVertex(t2);
				}

				if (!grafo.containsEdge(t1, t2) && !grafo.containsEdge(t2, t1)) {
					DefaultWeightedEdge edge = grafo.addEdge(t1, t2);
					grafo.setEdgeWeight(edge, res.getInt("peso"));
				}
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException("Errore DB");

		}

	}
}
