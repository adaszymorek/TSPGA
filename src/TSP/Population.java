package TSP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Population {
	ArrayList<ArrayList<Integer>> solutions = new ArrayList<ArrayList<Integer>>();
	int numberOfCities;
	int numberOfSolutions;
	double fitness[];
	double probability[]; // 
	double bestFitness;
	double recordDistance = 10000;
	ArrayList<Integer> bestEver;
	public Population( int numberOfCities, int numberOfSolutions, boolean isStart) 
	{
		this.numberOfCities = numberOfCities;
		this.numberOfSolutions = numberOfSolutions;
		this.fitness = new double[numberOfSolutions];
		this.probability = new double[numberOfSolutions];
		if(isStart == true) setStartPopulation();
		else setSpecificPopulation();
	}
	private void setStartPopulation()
	{
		// this method is setting population with sequences of randomly sorted numbers that correspond to cities.
		for(int i = 0; i < numberOfSolutions; i++)
		{
			solutions.add(new ArrayList<Integer>()); 
			for(int j = 0; j < numberOfCities; j++) solutions.get(i).add(j);
			Collections.shuffle(solutions.get(i));
		}
	}
	private void setSpecificPopulation() {
		for(int i = 0; i < numberOfSolutions; i++)
		{
			solutions.add(new ArrayList<Integer>()); 
			for(int j = 0; j < numberOfCities; j++) solutions.get(i).add(j);
		}
	}
	void mutation(List<Integer> order, double mutationRate) {
		Random rand = new Random();
		for(int i = 0; i < numberOfCities; i++) {
			if(rand.nextDouble() < mutationRate) {
				int indexA = rand.nextInt(numberOfCities);
				int indexB = rand.nextInt(numberOfCities);
				Collections.swap(order, indexA, indexB);
			}
		}
	}
	 void calculateFitness(CitiesGrid cities) {
		for(int i = 0; i < numberOfSolutions; i++) {
			double d = cities.calculateDistance(solutions.get(i));
			if(d < recordDistance) {
				recordDistance = d;
				bestEver = solutions.get(i);
				cities.setDistance(d);
				cities.setOrder(bestEver);
			}
			fitness[i] = 1/d;
		}
	}
	 void normalizeFitness() {
		double sum = 0;
		for(int i = 0; i < fitness.length; i++) {
			sum += fitness[i];
		}for(int i = 0; i < fitness.length; i++) {
			fitness[i] = fitness[i]/sum;
		}
	}
	 ArrayList<Integer> pickOne(ArrayList<ArrayList<Integer>> population, double[] probability){
		 int index = 0;
		 Random rand = new Random();
		 double r = rand.nextDouble();
		 while(r > 0 && index < probability.length) {
			 r = r - probability[index];
			 index++;
		 }
		 index--;
		 return population.get(index);
	 }
	 void rouletteSelection() {
		 ArrayList<ArrayList<Integer>> newPopulation = new ArrayList<ArrayList<Integer>>();
		 for(int i = 0; i < numberOfSolutions; i++) {
			 ArrayList<Integer> order = pickOne(solutions, fitness);
			 mutation(order, 0.1);
			 newPopulation.add(order);
		 }
		 Collections.copy(solutions, newPopulation);
	}
	 public ArrayList<ArrayList<Integer>> getSolutions() {
		return solutions;
	}
	public void add(ArrayList<Integer> solution) {
		solutions.add(solution);
	}
	 List<ArrayList<Integer>> selectParents(){
		List<ArrayList<Integer>> parents = new ArrayList<ArrayList<Integer>>(2);
		ArrayList<ArrayList<Integer>> population2 = solutions;
		// get two random parents
				Random random = new Random();
				for(int i = 0; i < numberOfSolutions; i++) { 
					int random1 = random.nextInt(population2.size());
					ArrayList<Integer> parent = population2.get(random1);
					parents.add(parent);
					population2.remove(random1);
				}
		return parents;
}
	/* void evolution(CitiesGrid cities, String crossoverType) {
		for(int i = 0; i < 100; i++) {

			List<ArrayList<Integer>> parents = selectParents();
			List<ArrayList<Integer>> population = solutions;
			for(int j = 0; j < (int)numberOfSolutions/2; j++) // did not check is number of solutions an odd number
			{
				List<ArrayList<Integer>> children;
				switch (crossoverType) {
				case "PMX": children = PMXcrossoverPopulation(parents.get(j), parents.get(j+1));
				case "CX": children = CXcrossoverPopulation(parents.get(j), parents.get(j+1));
				case "OX": children = OXcrossoverPopulation(parents.get(j), parents.get(j+1));
				}
				population.add(children.get(0));
				population.add(children.get(1));
			}
			Collections.copy(solutions, population);
		}
	}*/
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
			void setSolutions(ArrayList<ArrayList<Integer>> population) {
				Collections.copy(solutions, population);
			}
}
