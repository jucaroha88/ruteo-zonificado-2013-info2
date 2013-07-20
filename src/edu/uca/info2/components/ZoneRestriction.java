package edu.uca.info2.components;


/**
 * 
 * @author Alejandro
 *
 *
 * 
 * Restricciones de horario que se aplican a las Zonas. 
 * 
 * El rango de horas definido por startTime y endTime es el rango de horas
 * en el que se puede laburar.
 * 
 *
 */
public class ZoneRestriction {

	private String zoneId;
	
	// hora en la que se puede iniciar a trabajar.
	private int startTime;
	// hora en la que se debe terminar de trabajar.
	private int endTime;
		
	public String getZoneId() {
		return zoneId;
	}
	
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	
	public String toString() {
		return " Zone Restriction " + getZoneId() + " start time " + getStartTime() + " end time " + getEndTime();
		
	}
}
