package it.polito.tdp.newufosightings.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {
	
	private NewUfoSightingsDAO dao;
	private Map<String,State> idMap;
	private Graph<State,DefaultWeightedEdge> grafo;
	
	
	public Model() {
		
		dao= new NewUfoSightingsDAO();
		idMap=new HashMap<String,State>();
		dao.loadAllStates(idMap);
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

		
		
	}
	

	public boolean isValid(String anno) {
		if(!anno.matches("\\d{4}")) {
		return false;
	}
		Integer annoValido=Integer.parseInt(anno);
		return annoValido >= 1906 && annoValido <= 2014;

}

	public List<String> getAllShape(String anno) {
	
		return dao.getAllForme(anno);
	}
	
	public List<Confine> getConfini(){
		return dao.getConfini(idMap);
	}
	public String creaGrafo(String anno, String forma) {		

		Graphs.addAllVertices(grafo, idMap.values());
		
		List<Confine> confini=dao.getConfini(idMap);
		List<StatoePeso> sp = dao.getStatoePeso(anno, forma, idMap);
		
		for (Confine c : confini) {
			double peso1 = 0;
			double peso2 = 0;
		
			for (StatoePeso s : sp) {

				if (s.getS().equals(c.getS1())) {
					peso1 = s.getPeso();

				} else if (s.getS().equals(c.getS2())) {
					peso2 = s.getPeso();
				}
			
			}
//			DefaultWeightedEdge edge = grafo.addEdge(c.getS1(), c.getS2());	
//			grafo.setEdgeWeight(edge, peso1 + peso2);
			DefaultWeightedEdge edge= grafo.getEdge(c.getS1(), c.getS2());
			if(edge == null) {
				Graphs.addEdgeWithVertices(grafo, c.getS1(), c.getS2(), peso1+peso2);
			}else {
				grafo.setEdgeWeight(edge, peso1+peso2);
			}
						
		
		}
		String ris="";
		for(State st:grafo.vertexSet()) {
			int sum=0;
			List<State> vicini =Graphs.neighborListOf(grafo, st);
			
			for(State v: vicini) {
				DefaultWeightedEdge edge1=grafo.getEdge(st, v);
				sum+=(int) grafo.getEdgeWeight(edge1);
				

			}
			ris+="("+st.getId()+")" +" "+ st.getName() +" : somma dei pesi degli archi adiacenti: "+ sum+ "\n";
			
		}
		System.out.println(grafo.vertexSet().size() +" vertici"+ grafo.edgeSet().size() + " archi ");
		for(DefaultWeightedEdge edge: grafo.edgeSet()) {
			System.out.println(edge+ "peso: " +grafo.getEdgeWeight(edge));
			}
		return ris;
		
		
	
		
	}
}
