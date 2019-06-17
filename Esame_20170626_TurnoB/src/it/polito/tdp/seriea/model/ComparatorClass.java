package it.polito.tdp.seriea.model;

import java.util.Comparator;

public class ComparatorClass implements Comparator<Season> {



	@Override
	public int compare(Season a, Season b) {
		// TODO Auto-generated method stub
		return a.getSeason()-b.getSeason();
	}

}
