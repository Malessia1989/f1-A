package it.polito.tdp.seriea.model;

import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultWeightedEdge;

public class MioGrafo extends AbstractBaseGraph<Integer, DefaultWeightedEdge> implements DirectedGraph<Integer, DefaultWeightedEdge>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected MioGrafo() {
		super(new ClassBasedEdgeFactory<Integer,DefaultWeightedEdge>(DefaultWeightedEdge.class), true, true);
		// TODO Auto-generated constructor stub
	}
	
}
