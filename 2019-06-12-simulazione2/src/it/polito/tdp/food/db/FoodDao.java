package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.ArchiePesi;


public class FoodDao {

	public List<Food> listAllFood(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getInt("portion_default"), 
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"),
							res.getDouble("calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiment(Map<Integer, Condiment> idMap){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					if(idMap.get(res.getInt("condiment_id")) ==null) {
					Condiment c	= new Condiment(res.getInt("condiment_id"), res.getInt("food_code"),
							res.getString("display_name"),res.getString("condiment_portion_size"),
							res.getDouble("condiment_calories"));
						
				list.add(c);
				idMap.put(c.getCondiment_id(), c);
					}else {
						list.add(idMap.get(res.getInt("condiment_id")));
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}

	public List<ArchiePesi> archiePesi(String calorieInput, Map<Integer, Condiment> idMap) {
		
		String sql="SELECT c1.condiment_id AS id1, c2.condiment_id AS id2, COUNT(*) AS peso " + 
				"				FROM food f1, food f2, condiment c1, condiment c2, food_condiment fc1, food_condiment fc2 " + 
				"				WHERE f1.food_code= f2.food_code  "+ 
				"				AND fc1.food_code=f1.food_code " + 
				"				AND fc2.food_code=f2.food_code " + 
				"				AND c1.food_code=fc1.condiment_food_code " + 
				"				AND c2.food_code= fc2.condiment_food_code " + 
				"				AND c1.condiment_calories< ? AND c2.condiment_calories < ?" + 
				"				AND c1.condiment_id < c2.condiment_id  " + 
				"				GROUP BY id1, id2 " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setDouble(1, Double.parseDouble(calorieInput));
			st.setDouble(2, Double.parseDouble(calorieInput));
			
			List<ArchiePesi> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
				
					Condiment c1=idMap.get(res.getInt("id1"));
					Condiment c2= idMap.get(res.getInt("id2"));
					
					double peso= res.getDouble("peso");
					
				
					
					if(c1 != null && c2!= null) {
						ArchiePesi atemp= new ArchiePesi(c1, c2, peso);
						list.add(atemp);
					}
					}
				catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
}
