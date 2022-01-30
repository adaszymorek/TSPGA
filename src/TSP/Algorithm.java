package TSP;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Algorithm {
	int numberOfCities;
	int numberOfSolutions;
	Population startPopulation;
	int numberOfPopulations = 4000;
	double bestDistance[] = new double[numberOfPopulations];
	
	public Algorithm(int numberOfCities, int numberOfSolutions) {
		this.numberOfCities = numberOfCities;
		this.numberOfSolutions = numberOfSolutions;
		CitiesGrid cities = new CitiesGrid(numberOfCities);
		startPopulation = new Population(numberOfCities, numberOfSolutions, true);
		for(int i = 0; i < numberOfPopulations; i++) {
			startPopulation.calculateFitness(cities);
			startPopulation.normalizeFitness();
			startPopulation.rouletteSelection();
			System.out.println(i + "\t"+startPopulation.recordDistance);
			bestDistance[i] = startPopulation.recordDistance;
			evolution(cities, "PMX");
		}
		saveToFile();
	}
	void evolution(CitiesGrid cities, String crossoverType) {

			List<ArrayList<Integer>> parents = startPopulation.selectParents();
			ArrayList<ArrayList<Integer>> population = startPopulation.getSolutions();
			for(int j = 0; j < (int)numberOfSolutions/2; j++) // did not check is number of solutions an odd number
			{
				List<ArrayList<Integer>> children;
				switch (crossoverType) {
				case "PMX" : children = PMXcrossoverPopulation(parents.get(j), parents.get(j+1));
				case "CX": children = CXcrossoverPopulation(parents.get(j), parents.get(j+1));
				case "OX": children = OXcrossoverPopulation(parents.get(j), parents.get(j+1));
				default: children = PMXcrossoverPopulation(parents.get(j), parents.get(j+1));
				}
				population.add(children.get(0));
				population.add(children.get(1));
			}
			startPopulation.setSolutions(population);
		}
	
		private void saveToFile() {
			FileWriter fw = null;
			String sFitness[] = new String[bestDistance.length];
			try {
				fw = new FileWriter("plik.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				for (int i = 0; i < bestDistance.length; i++) {
					sFitness[i] = String.valueOf(bestDistance[i]);  
					bw.write(sFitness[i]);
					bw.newLine();
				}
				bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	//PMX 
			private ArrayList<ArrayList<Integer>> PMXcrossoverPopulation(List<Integer> parent1, List<Integer> parent2)
			{
				Random random = new Random();
				int breakpoint = random.nextInt(numberOfCities);
				ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();
				
				List<Integer> parent1Genome = new ArrayList<>(parent1);
			    List<Integer> parent2Genome = new ArrayList<>(parent2);
				
			    //child 1
			    for (int i = 0; i < breakpoint; i++) {
			        int newVal;
			        newVal = parent2Genome.get(i);
			        Collections.swap(parent1Genome, parent1Genome.indexOf(newVal), i);
			    }
			    children.add(new ArrayList<>(parent1Genome));
			    parent1Genome = parent1;
			    
			    //child 2
			    for (int i = breakpoint; i < numberOfCities; i++) {
			        int newVal;
			        newVal = parent1Genome.get(i);
			        Collections.swap(parent2Genome, parent2Genome.indexOf(newVal), i);
			    }
			    children.add(new ArrayList<>(parent2Genome));
			    
			    return children;
			}
			//OX
			private ArrayList<ArrayList<Integer>>  OXcrossoverPopulation(List<Integer> parent1, List<Integer> parent2)
			{
				
				Random random = new Random();
				int number1 = random.nextInt(numberOfCities - 1);
				 int number2 = random.nextInt(numberOfCities);
				 int start = Math.min(number1, number2);
				 int end = Math.max(number1, number2);
				
				ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();
				ArrayList<Integer> child1 = new ArrayList<Integer>();
				ArrayList<Integer> child2 = new ArrayList<Integer>();
				
				child1.addAll(parent1.subList(start, end));
				child2.addAll(parent2.subList(start, end));
				
				int currentCityIndex = 0;
				int currentCityInTour1 = 0;
				int currentCityInTour2 = 0;
				for (int i = 0; i < numberOfCities ; i++) 
				{
					currentCityIndex = (end + i) % numberOfCities;
					currentCityInTour1 = parent1.get(currentCityIndex);
					currentCityInTour2 = parent2.get(currentCityIndex);
					if (!child1.contains(currentCityInTour2)) 
					{
						child1.add(currentCityInTour2);
					}
					if (!child2.contains(currentCityInTour1)) 
					{
						child2.add(currentCityInTour1);
					}
				}
				Collections.rotate(child1, start);
				Collections.rotate(child2, start);
				children.add(child1);
				children.add(child2);
				return children;
			}
			//CX
			private ArrayList<ArrayList<Integer>> CXcrossoverPopulation(List<Integer> parent1, List<Integer> parent2)
				{
					Random random = new Random();
					int idx = random.nextInt(numberOfCities);
					ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();
					int cycle = 1;
					
					ArrayList<Integer> child1 = new ArrayList<Integer>(parent1);			
					ArrayList<Integer> child2 = new ArrayList<Integer>(parent2);
					
					final Set<Integer> visitedIndices = new HashSet<Integer>(numberOfCities);
					final List<Integer> indices = new ArrayList<Integer>(numberOfCities);
					while (visitedIndices.size() < numberOfCities) {
				           indices.add(idx);
				           
				           int item = parent2.get(idx);
				           idx = parent1.indexOf(item);
				            
				           while (idx != indices.get(0)) {	              
				               indices.add(idx);	   
				               item = parent2.get(idx);	                
				               idx = parent1.indexOf(item);
				           }
				           if (cycle++ % 2 != 0) {
				              for (int i : indices) {
				                   Integer tmp = child1.get(i);
				                    child1.set(i, child2.get(i));
				                    child2.set(i, tmp);
				                }
				            }
				            visitedIndices.addAll(indices);
				            // find next starting index: last one + 1 until we find an unvisited index
				            idx = (indices.get(0) + 1) % numberOfCities;
				            while (visitedIndices.contains(idx) && visitedIndices.size() < numberOfCities) {
				                idx++;
				                if (idx >= numberOfCities) {
				                    idx = 0;
				                }
				            }
				            indices.clear();
					    }
					    children.add(child1);
					    children.add(child2);
					    
			    return children;
			}
}
