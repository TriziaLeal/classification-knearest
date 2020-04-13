import java.util.ArrayList;

public class Coord{
	ArrayList<Double> coord;
	public Coord(ArrayList<Double> coord){
		this.coord = new ArrayList<Double>();
		for (Double co: coord){
			this.coord.add(co);
		}

	}
}