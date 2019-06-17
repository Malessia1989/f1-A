package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.model.Adiacenza;
import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	
	public List<Author> getAutore(Map<Integer, Author> idMap) {

		final String sql = "SELECT * FROM author ";
		List<Author> result = new ArrayList<Author>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if (idMap.get(rs.getObject("id")) == null) {

					Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));

					result.add(autore);
					idMap.put(autore.getId(), autore);
				} else {
					result.add(idMap.get(rs.getInt("id")));
				}
			}

			return result;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<Adiacenza> getCoautori(){
		
		String sql="SELECT  c1.authorid id1, c2.authorid id2 " + 
				"FROM creator c1, creator c2 " + 
				"WHERE c1.eprintid = c2.eprintid AND c1.authorid > c2.authorid ";
		
		List<Adiacenza> coautori=new ArrayList<Adiacenza>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Author a1=new Author(rs.getInt("id1"));
				Author a2=new Author(rs.getInt("id2"));
				
				Adiacenza adj=new Adiacenza(a1, a2);
				coautori.add(adj);
			}
			conn.close();
			return coautori;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
			
		
		
	}
}