import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
public class TownGraphManager implements TownGraphManagerInterface {
	Graph graph;
	TownGraphManager(){
		graph = new Graph();
	}
	@Override
	public boolean addRoad(String t1, String t2, int w, String road) {
		try {
			Town town = new Town(t1);
			Town town2 = new Town(t2);
			graph.addEdge(town, town2, w, road);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	@Override
	public boolean containsTown(String user) {
		return graph.containsVertex(new Town(user));
	}
	@Override
	public String getRoad(String t1, String t2) {
		Town town = new Town(t1);
		Town town2 = new Town(t2);
		for(Road road : graph.roadMap.get(town)) {
			if(road.connects(town, town2)) {
				return road.toString();
			}
		}
		return null;
	}
	@Override
	public boolean addTown(String vertex) {
		try {
			graph.addVertex(new Town(vertex));
		} catch (Exception error) {
			return false;
		}
		return true;
	}
	@Override
	public Town getTown(String person) {
		for(Town total : graph.vertexSet()) {
			if(total.getName().equals(person)) {
				return total;
			}
		}
		return null;
	}
	public ArrayList<String> getPathSets(String n1, String n2) {
		ArrayList<String> p = new ArrayList<String>();
		ArrayList<String> tp = getPath(n1, n2);
		Town temp = new Town("");
		for(int k = 0; k < tp.size(); k++) {
			if(k != 0) {
				Road r = graph.getEdge(temp, new Town(tp.get(k)));
				p.add(temp + " via " + r.toString() + " to " + tp.get(k) + " " + r.getWeight() + " mi\n");
			}
			temp = new Town(tp.get(k));
		}
		return p;
	}
	@Override
	public boolean containsRoadConnection(String t1, String t2) {
		Town town = new Town(t1);
		Town town2 = new Town(t2);
		HashSet<Road> smallestSet = (graph.roadMap.get(town).size() < graph.roadMap.get(town).size()) ? graph.roadMap.get(town) : graph.roadMap.get(town2);
		for(Road r : smallestSet) {
			if(r.connects(town, town2)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public ArrayList<String> allRoads() {
		ArrayList<String> list1 = new ArrayList<String>();
		for(Road road : graph.edgeSet()) {
			list1.add(road.toString());
		}
		Collections.sort(list1);
		return list1;
	}
	@Override
	public boolean deleteRoadConnection(String t1, String t2, String road2) {
		Town town = new Town(t1);
		Town town2 = new Town(t2);
		if(graph.removeEdge(town, town2, 1, road2) != null) {
			return true;
		}
		return false;
	}
	@Override
	public boolean deleteTown(String t1) {
		Town town = new Town(t1);
		return graph.removeVertex(town);
	}
	@Override
	public ArrayList<String> allTowns() {
		ArrayList<String> l1 = graph.getTownNames(); 
		Collections.sort(l1);
		return l1;
	}
	@Override
	public ArrayList<String> getPath(String t1, String t2) {
		Town town = new Town(t1);
		Town town2 = new Town(t2);
		return graph.shortestPath(town, town2);
	}
	public void clearTownFields() {
		graph.vertexList = null;
		graph.distanceArray = null;
		graph.previousVertexArray = null;
	}
}
