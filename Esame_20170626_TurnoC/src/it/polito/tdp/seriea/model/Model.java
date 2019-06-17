package it.polito.tdp.seriea.model;

import java.util.Map.Entry;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	//private SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> grafo; 
	
	private MioGrafo grafo;
	
	public void popolaGrafo() {
		if(grafo == null) {
			grafo= new MioGrafo();
			SerieADAO dao=new SerieADAO();
			dao.creaGrafo(grafo);
		}
		
	}

	public MioGrafo getGrafo() {
		return grafo;
	}

	public String getElencoRisultati(Integer goalSelezionato) {
		
		SerieADAO dao=new SerieADAO();
		//Map <String,Integer> map= new HashMap()<String,Integer>(dao.getPartiteGoalSelezionato(numGoalSelezionato));
		String result="";
		for(Entry<String, Integer> entry: dao.getPartiteGoalSelezionato(goalSelezionato).entrySet()) {
			result+=entry.getKey() +"num partite corrispondenti: " +entry.getValue() +"\n";
			
		}
		return result;
	
	
}
}
