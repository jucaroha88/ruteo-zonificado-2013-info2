package edu.uca.info2.components;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.uca.info2.prolog.PrologWrapperException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import jpl.Atom;
import jpl.Compound;
import jpl.Term;

public class Vehicle {
    
        public static final String pl_compound_name = "auto";
    
        //public static final int velocidad=20;
        
        private int velocidad;
	
	private String vehicleId;
	private int autonomy;

        public int getVelocidad() {
            return velocidad;
        }

        public void setVelocidad(int velocidad) {
            this.velocidad = velocidad;
        }

	public String getVehicleId() {
		return vehicleId;
	}
	
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public int getAutonomy() {
		return autonomy;
	}
	public void setAutonomy(int autonomy) {
		this.autonomy = autonomy;
	}
        
        public Compound toPlCompoundTerm(){
            return new Compound("auto", new Term[]{new Atom(vehicleId),
                                                    new jpl.Integer(autonomy),
                                                    new jpl.Integer(velocidad)});
        }
         
        public static ArrayList<Vehicle> loadListFromJson(String jsonstr){
            Gson gson = new Gson();
            Type collectionType = new TypeToken<ArrayList<Vehicle>>() {
            }.getType();
            ArrayList<Vehicle> vehicles = gson.fromJson(jsonstr,collectionType);
            return vehicles;
        }
        
        public String toString(){
            return "vehicle("+vehicleId+","+autonomy+","+velocidad+")";
        }
}
