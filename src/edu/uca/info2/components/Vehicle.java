package edu.uca.info2.components;

import jpl.Atom;
import jpl.Compound;
import jpl.Term;

public class Vehicle {
    
        public static final int velocidad=20;
	
	private String vehicleId;
	private int autonomy;
	
	
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
            return new Compound(vehicleId, new Term[]{new Atom(vehicleId), new Atom(Integer.toString(autonomy)), new Atom(Integer.toString(velocidad))});
        }
                
}
