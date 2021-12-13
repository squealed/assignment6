import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
public class Graph implements GraphInterface<Town, Road> {
	HashMap<Town, LinkedHashSet<Town>> aj;
	HashMap<Town, LinkedHashSet<Road>> roadMap;
	HashSet<Road> roadSet;
	ArrayList<Town> vertexList;
	int[] distanceArray;
	Town[] previousVertexArray;
	Graph(){
		aj = new HashMap<Town, LinkedHashSet<Town>>();
		roadMap = new HashMap<Town, LinkedHashSet<Road>>();
		roadSet = new HashSet<Road>();
	}
	public Road getEdge(Town sourceVertex, Town destinationVertex) {
		if(aj.get(sourceVertex).contains(destinationVertex)) {
			for(Road road : roadSet) {
				if(road.connects(sourceVertex, destinationVertex)) {
					return road;
				}
			}
		}
		return null;
	}
	@Override
	public Road addEdge(Town source, Town destination, int w, String d) throws IllegalArgumentException {
		Road r1 = new Road(source, destination, w, d);
		if(!containsVertex(source) || !containsVertex(source)) {
			throw new IllegalArgumentException();
		}
		if(!roadMap.keySet().contains(source) || !roadMap.keySet().contains(destination)) {
			throw new IllegalArgumentException();
		}
		roadMap.get(source).add(r1);
		roadMap.get(destination).add(r1);
		aj.get(source).add(destination);
		aj.get(destination).add(source);
		roadSet.add(r1);
		return r1;
	}
	@Override
	public boolean addVertex(Town town) {
		if(!aj.keySet().contains(town)) {
			aj.put(town, town.getAdjacentSet());
			roadMap.put(town, new LinkedHashSet<Road>());
			return true;
		}
		return false;
	}
	@Override
	public boolean containsEdge(Town source, Town destination) {
		for(Road road : roadSet) {	
			 if (road.connects(source, destination)) return true;
		}
		return false;
	}
	@Override
	public boolean containsVertex(Town town) {
		return aj.keySet().contains(town);
	}
	@Override
	public Set<Road> edgeSet() {
		return roadSet;
	}
	public ArrayList<Town> getTowns() {
		return vertexList;
	}
	public ArrayList<String> getTownNames() {
		ArrayList<String> n1 = new ArrayList<String>();
		for(Town town1 : aj.keySet()) {
			n1.add(town1.toString());
		}
		return n1;
	}
	@Override
	public Set<Road> edgesOf(Town v1) throws NullPointerException {
		if(roadMap.keySet().contains(v1)) {
			return roadMap.get(v1);
		}
		throw new NullPointerException();
	}
	@Override
	public Road removeEdge(Town source, Town destination, int w, String d) {
		Road road = new Road(source, destination, w, d);
		if(roadSet.contains(road)) {
			roadSet.remove(road);
			roadMap.remove(source);
			roadMap.remove(destination);
			return road;
		}
		return null;
	}
	@Override
	public boolean removeVertex(Town town) {
		if(aj.keySet().contains(town)) {
			LinkedHashSet roads = roadMap.get(town);
			for(Town k1 : aj.keySet()) {
				aj.get(k1).removeAll(Collections.singleton(town));
				roadMap.get(k1).removeAll(roads);
			}
			aj.remove(town);
			roadMap.remove(town);
			return true;
		}
		return false;
	}
	@Override
	public Set<Town> vertexSet() {
		return aj.keySet();
	}
	@Override
	public ArrayList<String> shortestPath(Town source, Town destination) {
		dijkstraShortestPath(source);
		System.out.println();
		Town temp = destination;
		ArrayList<String> h1 = new ArrayList<String>();
		if(previousVertexArray[vertexList.indexOf(temp)] != null) {
			while(!temp.equals(source)) {
				h1.add(temp.toString());
				temp = previousVertexArray[vertexList.indexOf(temp)];
			}
		} else if (!source.equals(destination)) {
			throw new UnsupportedOperationException();
		}
		h1.add(source.toString());
		Collections.reverse(h1);
		return h1;
	}
	@Override
	public void dijkstraShortestPath(Town start) {
		int size = vertexSet().size();
		vertexList = new ArrayList<Town>();
		distanceArray = new int[size];
		previousVertexArray = new Town[size];
		Arrays.fill(distanceArray, Integer.MAX_VALUE);
		ArrayList<Town> u1 = new ArrayList<Town>();
		ArrayList<Town> u2 = new ArrayList<Town>();
		for (Town t1 : vertexSet()){
			vertexList.add(t1);
			u1.add(t1);
		}
		distanceArray[vertexList.indexOf(start)] = 0;
		Town s1;
		while(!u1.isEmpty()) {
			int smallIndex = vertexList.indexOf(u1.get(0));
			for(int i = 0; i < size; i++) {
				if(!u2.contains(vertexList.get(i)) && (distanceArray[i] < distanceArray[smallIndex])) {
					smallIndex = i;
				}
			}
			s1 = vertexList.get(smallIndex);
			Town hold;
			for(Town t : aj.get(s1)) {
				if(u1.contains(t)) {
					int index = vertexList.indexOf(t);
					Town holdingOld = previousVertexArray[index];
					if(vertexList.get(index) != start) {
						previousVertexArray[index] = s1;
					}
					int fromStart = 0;
					hold = t;
					while(!hold.equals(start)) {
						fromStart += getEdge(previousVertexArray[vertexList.indexOf(hold)], hold).getWeight();
						hold = previousVertexArray[ vertexList.indexOf(hold)];
					}
					if(fromStart < distanceArray[index]) {
						distanceArray[index] = fromStart;
						previousVertexArray[index] = s1;
					} else {
						previousVertexArray[index] = holdingOld;
					}
				}
			}
			u2.add(s1);
			u1.remove(s1);
		}		
	}
	@Override
	public String toString() {
		String s1 = "TOWNS\n";
		if(aj != null) {
			for(Town t : aj.keySet()) {
				s1 += t + ": " + aj.get(t) + "\n";
			}
			s1 += "\nROADS\n";
		}
		if(roadMap != null) {
			for(Town t : roadMap.keySet()) {
				s1 += t + ": " + roadMap.get(t) + "\n";
			}
		}
		return s1;
	}
}
