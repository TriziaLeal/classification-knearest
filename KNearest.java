import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class KNearest{
	ArrayList<Point> dataset;
	ArrayList<Coord> input;
	int k;
	public KNearest(){
		this.dataset = new ArrayList<Point>();
		this.input = new ArrayList<Coord>();
	}

	double euclidean(Coord data, Coord input){
		double euclidean = 0;
		for (int i = 0; i < data.coord.size(); i++){

			euclidean+=Math.pow(data.coord.get(i)-input.coord.get(i),2);
		}
		return Math.sqrt(euclidean);
	}
	void performKNearest(){
		String dataset = readFile("training2.txt");
		convertTraining(dataset);
		ArrayList<Point> KNearest;
		int classification;

		String input = readFile("input2.txt");
		convertInput(input);

		while(!this.input.isEmpty()){
			Coord toBeRemove = this.input.remove(0);
			for(Point d: this.dataset){
				d.euclidean = euclidean(d,toBeRemove);
			}
			
			sortData();
			KNearest = getKnearest();
			Point newData = new Point(toBeRemove.coord,0);
			this.dataset.add(newData);			
			System.out.print(toStringNeighbors(newData,KNearest));
			writeFile(toStringNeighbors(newData,KNearest));
			newData.classification = getClassification(KNearest);
			String s = "Classification of " + newData.coord + ": " + newData.classification + "\n";
			writeFile(s);			
			System.out.println("Classification of " + newData.coord + ": " + newData.classification);


		}
	}

	void sortData(){
		Point min;
		for (int i = 0; i < this.dataset.size(); i++){
			for(int j = 0; j < this.dataset.size()-1; j++){
				if (this.dataset.get(j+1).euclidean < this.dataset.get(j).euclidean){
					min = this.dataset.get(j+1);
					this.dataset.set(j+1,this.dataset.get(j));
					this.dataset.set(j,min);
				}
			}
		}
	}

	ArrayList<Point> getKnearest(){
		ArrayList<Point> KNearest = new ArrayList<Point>();
		for (int i = 0; i < this.k; i++){
			KNearest.add(this.dataset.get(i));
		}return KNearest;
	}

	int getClassification(ArrayList<Point> nearest){
		int max = 0;
		int classification = -1;
		int temp;
		int count;
		int i;
		while(!nearest.isEmpty()){
			count = 0;
			i = 0;
			temp = nearest.get(0).classification;
			while(i < nearest.size()){
				if (nearest.get(i).classification == temp){
					count++;
					nearest.remove(i);

				}
				else i++;
			}	
			if (max < count){ 
				max = count;
				classification = temp;
			}
		}

		return classification;

	}


	String readFile(String filename){
		StringBuffer stringBuffer = new StringBuffer();
		try {
			File file = new File(filename);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
		} catch (IOException e) {
			e.getMessage();
		}
		return stringBuffer.toString();
	}

	public void writeFile(String newText){
		FileWriter fw = null;
		BufferedWriter bw = null;
		//creates file
		try{    
        	fw=new FileWriter("output.txt",true);
        	bw = new BufferedWriter(fw);
        	bw.write(newText);    
        	// fw.close();    
        }catch(Exception e){System.out.println(e);
        } finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {System.out.println(ex);
			}

	}	
		}
	void convertTraining(String s){
		String[] string = s.split("\n");
		ArrayList<Double> coord;
		int c;
		this.k = Integer.parseInt(string[0]);

		for (int j = 1; j < string.length; j++){
			coord = new ArrayList<Double>();
			String[] elements = string[j].split(" ");
			for(int i = 0; i < elements.length-1; i++){
				coord.add(Double.parseDouble(elements[i]));
			}
			c = Integer.parseInt(elements[elements.length-1]);
			Point coordinates = new Point(coord,c);
			this.dataset.add(coordinates);
		}
	}

	void printDataSet(ArrayList<Point> dataset){
		System.out.println();
		System.out.println("printDataSet");
		System.out.println(k);
		for (Point data: dataset){
			System.out.print(data.coord + " ");
			System.out.print(data.classification + " ");
			System.out.println(data.euclidean);

		}
	}

	void convertInput(String s){
		String[] string = s.split("\n");
		ArrayList<Double> coord;
		
		for(String str: string){
			coord = new ArrayList<Double>();
			String[] elements = str.split(" ");
			for (String el: elements){
				coord.add(Double.parseDouble(el));
			}
			Coord input = new Coord(coord);
			this.input.add(input);
		}		
	}

	String toStringNeighbors(Coord toBeClassified, ArrayList<Point> KNearest){
		String stringKnearest= "Nearest Neighbors of";
		stringKnearest += toBeClassified.coord + ": ";
		for (Point k: KNearest){
			stringKnearest += k.coord + " ";
		}stringKnearest += "\n";
		return stringKnearest;
	}

	void printInput(){
		System.out.println("Print Input");
		for(Coord c: this.input){
			System.out.println(c.coord + " ");
		}

	}

}