package edu.uca.info2.serializer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.uca.info2.components.Area;
import edu.uca.info2.components.Vehicle;
import edu.uca.info2.components.Zone;
import edu.uca.info2.components.ZoneRestriction;

/**
 * 
 * Esta clase crea los archivos de input.
 * 
 * 
 * @author Alejandro
 *
 */
public class SerializerExample {
		
	private static String serializeArea() {
		
		List <Area> listAreas = new ArrayList<Area>();
		
		Area a = new Area(250042803);
		listAreas.add(a);
		
		a = new Area(312457840);
		listAreas.add(a);
		
		a = new Area(314446085);
		listAreas.add(a);

		a = new Area(330747283);
		listAreas.add(a);

		a = new Area(692261833);
		listAreas.add(a);

		a = new Area(1440628204);
		listAreas.add(a);
		
		Gson gson = new Gson();
		String json = gson.toJson(listAreas);
		
		System.out.println("Serialized Area: " + json);
		
		return json;
	}
	
/*	private static void deSerializeArea(String json) {
		
		Gson gson = new Gson();		
		Type collectionType = new TypeToken<ArrayList<Area>>(){}.getType();
		List <Area> listAreas = gson.fromJson(json, collectionType);

		System.out.println("De-serialized Area: " + listAreas); 
		
	}
*/	
	private static String serializeZone() {
		
		List<Zone> zones = new ArrayList<Zone>();
		Zone z;
		
		z = new Zone();
		z.setX1(250042889);
		z.setX2(250054645);
		z.setY1(314446095);
		z.setY2(319483759);
		z.setZoneId("Zone 1");
		
		zones.add(z);
		
		z = new Zone();
		z.setX1(314446095);
		z.setX2(319483759);
		z.setY1(1439400386);
		z.setY2(593976739);
		z.setZoneId("Zone 2");

		zones.add(z);		
		
		z = new Zone();
		z.setX1(324389658);
		z.setX2(314446095);
		z.setY1(324806907);
		z.setY2(1439400386);
		z.setZoneId("Zone 3");
		
		z = new Zone();
		z.setX1(1439400386);
		z.setX2(593976739);
		z.setY1(59712800);
		z.setY2(613343696);
		z.setZoneId("Zone 4");
		
		zones.add(z);		
		
		Gson gson = new Gson();
		String json = gson.toJson(zones);
		
		System.out.println("Serialized zones: " + json);
		
		return json;
	}

	private static String serializeRestrictions() {

		ZoneRestriction r;
		List <ZoneRestriction> restrictions = new ArrayList<ZoneRestriction>();

		r = new ZoneRestriction();
		r.setZoneId("Zone 1");
		r.setStartTime(6);
		r.setEndTime(22);
		
		restrictions.add(r);

		r = new ZoneRestriction();		
		r.setZoneId("Zone 2");
		r.setStartTime(8);
		r.setEndTime(12);

		r = new ZoneRestriction();		
		r.setZoneId("Zone 3");
		r.setStartTime(8);
		r.setEndTime(16);
		
		r = new ZoneRestriction();		
		r.setZoneId("Zone 4");
		r.setStartTime(9);
		r.setEndTime(20);
		
		restrictions.add(r);		
						
		Gson gson = new Gson();
		String json = gson.toJson(restrictions);
		
		System.out.println("Serialized restrictions: " + json);
		
		return json;
	}

/*	private static List<Zone> deserializeZones(String zones) {
		
		Gson gson = new Gson();		
		Type collectionType = new TypeToken<ArrayList<Zone>>(){}.getType();
		
		List <Zone> zonesList = gson.fromJson(zones, collectionType);

		System.out.println("De-serialized zones: " + zonesList); 
		
		return zonesList;

	}
*/
	private static <T> List<T> deserializeList(String zones) {
		
		Gson gson = new Gson();		
		Type collectionType = new TypeToken<ArrayList<T>>(){}.getType();
		
		List <T> objectList = gson.fromJson(zones, collectionType);

		System.out.println("De-serialized object list: " + objectList); 
		
		return objectList;

	}
	
/*	private static List<ZoneRestriction> deserializeRestrictions(String restrictions) {
		
		Gson gson = new Gson();		
		Type collectionType = new TypeToken<ArrayList<ZoneRestriction>>(){}.getType();
		
		List <ZoneRestriction> zonesList = gson.fromJson(restrictions, collectionType);

		System.out.println("De-serialized Restrictions: " + zonesList); 
		
		return zonesList;

	}
*/	
	private static String serializeVehicles() {
		
		List<Vehicle>vehiclesList = new ArrayList<Vehicle>();
		
		Vehicle v;
		
		v=  new Vehicle();
		v.setVehicleId("V-1");
		v.setAutonomy(400);

		vehiclesList.add(v);
		
		v=  new Vehicle();
		v.setVehicleId("V-2");
		v.setAutonomy(800);

		vehiclesList.add(v);		
		
		v=  new Vehicle();
		v.setVehicleId("V-3");
		v.setAutonomy(800);	
		
		vehiclesList.add(v);		
		
		Gson gson = new Gson();
		String json = gson.toJson(vehiclesList);
		
		System.out.println("Serialized Vehicles: " + json);
		
		return json;
	}
	
	public static void main(String[] args) {

		String json = serializeArea();
		saveToFile("areas.json", json);
	
		deserializeList(json);//deSerializeArea(json);
		
		String zones = serializeZone();
		saveToFile("zones.json", zones);
				
		String restrictions = serializeRestrictions();
		saveToFile("restrictions.json", restrictions);
		
		//List<ZoneRestriction> restrictionsList = deserializeList(restrictions); //deserializeRestrictions(restrictions);
		
		//List<Zone> zonesList = deserializeList(zones);//deserializeZones(zones); 
			
		String vehicles = serializeVehicles();
		saveToFile("vehicles.json", vehicles);
	}
	
	private static void saveToFile(String fileName, String content) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			out.write(content);
			out.close();
		} catch (IOException e) {
		}
	}	

/*	private static void setRestrictions(List<Zone> zonesList,
			List<ZoneRestriction> restrictionsList) {
		
		for (Zone z : zonesList) {
			for (ZoneRestriction r : restrictionsList) {
				if (r.getZoneId()!= null && r.getZoneId().equalsIgnoreCase(z.getZoneId())) {
					z.setRestriction(r);
					break;
				}
			}
		}
	}
*/


}
