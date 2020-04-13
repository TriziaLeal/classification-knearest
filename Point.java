import java.util.ArrayList;
public class Point extends Coord{
	int classification;
	double euclidean;
	public Point(ArrayList<Double> coord, int classification){
		super(coord);
		this.classification = classification;
		this.euclidean = 0;
	}

}