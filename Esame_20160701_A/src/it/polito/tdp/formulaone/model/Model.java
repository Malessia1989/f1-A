package it.polito.tdp.formulaone.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	SimpleDirectedWeightedGraph<Driver, DefaultWeightedEdge> grafo;
	FormulaOneDAO dao;
	Map<Integer,Driver> idMap;
	
	public Model() {
		grafo=new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		dao=new FormulaOneDAO();
		idMap=new HashMap<>();
		dao.getAllDrivers(idMap);

	}
	public List<Integer> getSeason() {
		
		return dao.getAllYearsOfRace();
	}
	
	public String creaGrafo(int anno) {	
		
		for(Vittoria v:dao.getVittorie(anno, idMap) ){
			
			grafo.addVertex(v.getD1());
			grafo.addVertex(v.getD2());
			
			DefaultWeightedEdge	edge= grafo.addEdge(v.getD1(), v.getD2());
			grafo.setEdgeWeight(edge, v.getPeso());
			
			
		}
		
		System.out.println("vertici :" +grafo.vertexSet().size());
		System.out.println("archi :" +grafo.edgeSet().size());
		for(DefaultWeightedEdge edge: grafo.edgeSet())
		{
			System.out.println(edge +" peso: " +grafo.getEdgeWeight(edge));
		}
		
		String risultato="";
		double max=0;
		Driver migliorPilota=null;
		for(Driver d: grafo.vertexSet()) {
			double risultatoPilota=0;
			for(DefaultWeightedEdge edge: grafo.outgoingEdgesOf(d)) {
				risultatoPilota+=grafo.getEdgeWeight(edge);
			}
			for(DefaultWeightedEdge edge: grafo.incomingEdgesOf(d)) {
				risultatoPilota-=grafo.getEdgeWeight(edge);
			}
			if(risultatoPilota > max) {
				max=risultatoPilota;
				migliorPilota=d;
				risultato="Il miglior pilota nell'anno selezionato e: "+d.getDriverId()+" con un risultato di: "+max;
			}
		}
		if(migliorPilota == null) {
			new RuntimeException("Miglior pilota non trovato!");
		}
		return risultato;
	
	}


}
