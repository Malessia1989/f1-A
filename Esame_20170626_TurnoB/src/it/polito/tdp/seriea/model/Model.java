package it.polito.tdp.seriea.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private SimpleWeightedGraph<Season, ArcoPersonalizzato> grafo;
	
	public void creaGrafo() {
		if(grafo == null) {
			grafo= new SimpleWeightedGraph<>(ArcoPersonalizzato.class);
			SerieADAO dao= new SerieADAO();
			dao.creaGrafo(grafo);
			
		}
		
	}

	public List <Season> getSeason() {
		SerieADAO dao= new SerieADAO();
		return dao.listSeasons();
	}

	public String elencoStagioni(Season stagione) {
		
		creaGrafo();
		List<Season> vicini= new LinkedList<>(Graphs.neighborListOf(grafo, stagione));
		Collections.sort(vicini, new ComparatorClass());
		
		String result="";
		for(Season stemp: vicini) {
			ArcoPersonalizzato arco=grafo.getEdge(stagione, stemp);
			int peso=arco.getPeso();
			
			result+=stemp.getSeason() +" " +peso +"\n";
		}
		return result;
	}

}
