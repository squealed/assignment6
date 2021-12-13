public class Road implements Comparable<Road>{
	String person;
	Town uno, dos;
	int area;
	public Road(Town s, Town d, int w, String n) {
		area = w;
		uno = s;
		dos = d;
		this.person = n;
	}
	public Road(Town s, Town d, String n) {
		area = 1;
		uno = s;
		dos = d;
		this.person = n;
	}
	public String getName() {
		return person;
	}
	public Town getSource() {
		return uno;
	}
	public boolean contains(Town t1) {
		return (t1.equals(uno) || t1.equals(dos));
	}
	public boolean connects(Town t1, Town t2) {
		return (t1.equals(uno) || t1.equals(dos)) && (t2.equals(uno) || t2.equals(dos));
	}
	@Override
	public int compareTo(Road o1) {
		return person.compareTo(o1.getName());
	}
	public Town getDestination() {
		return dos;
	}
	public int getWeight() {
		return area;
	}
	@Override
	public boolean equals(Object r1) {
		
		if (!(r1 instanceof Road)) {
			return false;
		}
		Road road = (Road) r1;
		return road.getSource().equals(this.getSource()) ||
				road.getSource().equals(this.getDestination()) ||
				road.getDestination().equals(this.getSource()) ||
				road.getDestination().equals(this.getDestination());
	}
	@Override
	public String toString() {
		return person;
	}
	public boolean equals(Road o1) {
		return person.equals(o1.getName());
	}
}
