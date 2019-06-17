package it.polito.tdp.seriea.model;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.graph.DefaultWeightedEdge;

public class ArcoPersonalizzato extends DefaultWeightedEdge{
	
		List<Team> teamComuni; 
		int peso = 0;
	
		public void updatePeso(Team teamInComune) {
			
			if( teamComuni== null) {
				teamComuni= new LinkedList<Team>();
			}
			if (!teamComuni.contains(teamInComune)) {
				teamComuni.add(teamInComune);
				peso++;
			}
	
		}
	
		public int getPeso() {
			return this.peso=peso;
		}
	
	}
