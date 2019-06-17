package it.polito.tdp.food.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	FoodDao dao;
	Map<Integer,Condiment> idMap;
	Graph<Condiment, DefaultWeightedEdge> grafo;
	
	public Model() {
		dao= new FoodDao();
		idMap= new HashMap<>();
		dao.listAllCondiment(idMap);
		
	}

	public boolean isValid(String calorieInput) {
		
		return calorieInput.matches("\\d+(\\.|\\,){0,1}\\d*");
	}
	public String creaGrafo(String calorieInput) {
		
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		List<ArchiePesi> adj= dao.archiePesi(calorieInput,idMap);
		for(ArchiePesi a: adj) {

				Graphs.addEdgeWithVertices(grafo, a.getC1(), a.getC2(), a.getPeso());

		}
		
		System.out.println("Grafo creato! ");
		System.out.println("vertici: " +grafo.vertexSet().size());
		System.out.println("archi: " +grafo.edgeSet().size());
		for(DefaultWeightedEdge edge: grafo.edgeSet()) {
		System.out.println(edge +" peso: "+grafo.getEdgeWeight(edge));
		}
		
		String risultato="";
		
		//double sum=0; è sbagliato metterlo fuori
		List<Condiment > allVertici= new LinkedList<>(grafo.vertexSet());
		System.out.println("dimensione lista: "+allVertici.size());
		Collections.sort(allVertici);
		
		for(Condiment c: allVertici) {
			List<Condiment> vicini= Graphs.neighborListOf(grafo, c);
			double sum=0;
			for(Condiment c2:vicini) {
				DefaultWeightedEdge edge= grafo.getEdge(c, c2);
				sum+=grafo.getEdgeWeight(edge);	
				
			}
			risultato+= "Ingrediente: "+ c.getCondiment_id()+ "-"
					+c.getDisplay_name()+ " Calorie: "+c.getCondiment_calories()+"\n contenuto in: "+sum +" cibi \n";
		}
		
		
		return risultato;
	}

	public Graph<Condiment, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public String getDieta(Condiment cInput) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
