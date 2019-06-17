package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private SimpleGraph<Country, DefaultEdge> grafo;
	private Map<Integer, Country> idMap;
	
	public Model() {
		grafo=new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		idMap= new HashMap<Integer, Country>();
	}

	public boolean isValid(String annoInput) {
		if(annoInput.matches("\\d{4}")) 
		return true;
		
		int annoValido= Integer.parseInt(annoInput);
		return annoValido >= 1816 && annoValido <=2016;
	}

	public String creaGrafo(String annoInput) {
		
		BordersDAO dao= new BordersDAO();
		dao.loadAllCountries(idMap);
		
		List<Border> border=dao.getConfini(annoInput, idMap);
		
		for(Border b: border) {

//				grafo.addVertex(b.getStato1());
//				grafo.addVertex(b.getStato2());
//				grafo.addEdge(b.getStato1(), b.getStato2());
				
			Graphs.addEdgeWithVertices(grafo, b.getStato1(), b.getStato2());
			}
		
		
		//Graphs.addAllVertices(grafo, idMap.values());
		
		
		System.out.println( grafo.vertexSet().size()+" "+ grafo.edgeSet().size());
	
		String risultato="";
		 for(Country c: grafo.vertexSet()) {
			 risultato+=c.getNome() +" "+ grafo.degreeOf(c)+ "\n";
		 }
		ConnectivityInspector<Country, DefaultEdge> inspector=new ConnectivityInspector<Country, DefaultEdge>(grafo);
		risultato+= "Componente connessa: "+inspector.connectedSets().size();
		
		return risultato;
	}

	public List<Country> getCountry() {
		BordersDAO dao=new BordersDAO();
		return dao.loadAllCountries(idMap);
	}

	public String calcolaTuttiVicini(Country c) {
		
		List<Country> tuttiVicini=new LinkedList<Country>();
		BreadthFirstIterator<Country, DefaultEdge> it = new BreadthFirstIterator<>(this.grafo,c);
		
		while(it.hasNext()) {
			tuttiVicini.add(it.next());
		}
		
		String risultato="";
		for(Country c1 :tuttiVicini) {
			risultato+= " "+c1.getNome()+"\n";
		
		}
		return risultato;
	}


	

}
