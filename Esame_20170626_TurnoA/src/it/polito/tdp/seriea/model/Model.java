package it.polito.tdp.seriea.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private SimpleWeightedGraph<Team, DefaultWeightedEdge> grafo;


	public void popolaGrafo() {
		
		if(grafo==null) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		SerieADAO dao=new SerieADAO();
		dao.popolaGrafo(grafo);
		
		System.out.println(grafo.vertexSet());
		}
	}

	public SimpleWeightedGraph<Team, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public String calcolaConnessioni(Team squadraSelezionata) {
		
		List<Team> vicini=new LinkedList<>(Graphs.neighborListOf(grafo, squadraSelezionata));

		String result=" ";		
		
		Collections.sort(vicini, new Comparator<Team>() {

			@Override
			public int compare(Team o1, Team o2) {
				DefaultWeightedEdge arco1=grafo.getEdge(o1, squadraSelezionata);
				double peso1= grafo.getEdgeWeight(arco1);
				
				DefaultWeightedEdge arco2=grafo.getEdge(o2, squadraSelezionata);
				double peso2= grafo.getEdgeWeight(arco2);
				
				return (int) (peso2-peso1);
			}
		}); 
		
		for (Team t : vicini) {
			
			DefaultWeightedEdge  edge= grafo.getEdge(t, squadraSelezionata);
			double cnt=(int) grafo.getEdgeWeight(edge);
			result+= t.getTeam() + " "  +cnt+ "\n";
			
		}
		
		return result;
	}


}
