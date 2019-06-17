package it.polito.tdp.formulaone.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {

	private SimpleWeightedGraph<Driver, DefaultWeightedEdge> grafo;
	private Map<Integer, Driver> idMap;
	private FormulaOneDAO dao ;
	
	public Model() {
		idMap = new HashMap<>();
		dao=new FormulaOneDAO();
		dao.getallDrivers(idMap);
	}

	public static List<Constructor> getAllConstructor() {
		FormulaOneDAO dao = new FormulaOneDAO();
		return dao.getAllConstructors();
	}

	public String getMigliorPilota(Constructor c) {

		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
			
		List<SquadrePiloti> squadra = dao.getCoppiePiloti(idMap, c);
		for(SquadrePiloti sp: squadra) {
			Graphs.addEdgeWithVertices(grafo, sp.getP1(), sp.getP2());			
			
			DefaultWeightedEdge edge = grafo.getEdge(sp.getP1(), sp.getP2());
			
			grafo.setEdgeWeight(edge, sp.getPeso());
		}
		System.out.println("Vertici: "+grafo.vertexSet().size()+" Archi: "+grafo.edgeSet().size());
		
		for(DefaultWeightedEdge edge:grafo.edgeSet()) {
			System.out.println(edge+"peso: " +grafo.getEdgeWeight(edge));
		}
		
		//cerco pilota che ha gareggiato piu volte con un costruttore
		
//		
//		select r.driverId,r.constructorId, count(*) as peso
//		from results r 
//		where r.constructorId=3
//		group by r.driverId
		
		
		String risultato="";
		double max=0;
		Driver migliore=null;
		
		for(Driver d: grafo.vertexSet()) {
			List<Driver> vicini = Graphs.neighborListOf(grafo, d);
			double somma=0;
			for(Driver d1: vicini) {
				DefaultWeightedEdge edge = grafo.getEdge(d, d1);
				somma+=grafo.getEdgeWeight(edge);
			}
			if(somma>max) {
				max=somma;
				migliore=d;
				risultato="L'ID del miglior pilota è: "+migliore.getDriverId()+  " con un numero di gare disputate pari a: "+max;
			}
		}
		return risultato;
	}

	public List<Constructor> getConstructor() {
		
		return dao.getAllConstructors();
	}
}
