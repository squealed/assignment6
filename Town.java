import java.util.ArrayList;
import java.util.LinkedHashSet;
public class Town implements Comparable<Town>{
	String person;
	LinkedHashSet<Town> nextTown;
	Town(Town placeholder){
		person = placeholder.getName();
		nextTown = placeholder.nextTown;
	}
	Town(String holder){
		this.person = holder;
		nextTown = new LinkedHashSet<Town>();
	}
	public String getName() { return person; }
	public LinkedHashSet getAdjacentSet() {
		return nextTown; 
		}
	@Override
	public int compareTo(Town o1) {
		return person.compareTo(o1.getName()); 
		}
	public boolean equals(Object o1) { 
		
		if (!(o1 instanceof Town)) {
			return false;
		}
		if(this.compareTo((Town) o1) == 0) {
			return true;
		}else {
			return false;
		}
	}
	public int hashCode() {
		return person.hashCode();
		}
	public String toString() { 
		return person; 
		}
}
