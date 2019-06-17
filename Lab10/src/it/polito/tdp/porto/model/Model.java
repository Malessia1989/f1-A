package it.polito.tdp.porto.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	SimpleGraph<Author, DefaultEdge> grafo;
	PortoDAO dao;
	Map<Integer, Author> idMap;
	
	public Model() {
		grafo=new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);
		dao=new PortoDAO();
		idMap= new HashMap<Integer, Author>();
	}
	

	public List<Author> getAutori() {
		
		return dao.getAutore(idMap);
	}
	
	public void creaGrafo() {
		
		dao.getAutore(idMap);
		
		Graphs.addAllVertices(grafo, idMap.values());
		
		List<Adiacenza> adj=dao.getCoautori();
		
		for(Adiacenza a: adj) {
			
			Author a1=idMap.get(a.getA1().getId());
			Author a2=idMap.get(a.getA2().getId());
			
			Graphs.addEdgeWithVertices(grafo, a1, a2);
		}
		
		
		System.out.println("Grafo creato: " +grafo.vertexSet().size() +" vertici " +grafo.edgeSet().size()+ " archi" );
		
		
	}
	
	public String getCoautori(Author a){
		
		String result="";
		List <Author> vicini=Graphs.neighborListOf(grafo, a);
		
		for(Author a1 :vicini) {
			result+= a1.getId()+"-"+a1.getFirstname()+" " +a1.getLastname()+"\n";
			
		}
		return result;
		
	} 

}
