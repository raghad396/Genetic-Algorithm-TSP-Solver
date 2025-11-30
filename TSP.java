package tepe;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


//C:\\Users\\AHMED ABUSNAYMA\\Desktop\\att48.txt
public class TSP {
	public static boolean all(int[] a, int[]b) {
		int aCount=0;
		boolean ch1=false;
		for(int i=0; i<a.length;i++) {
			for(int j=0; j<b.length;j++) {
				if(a[i]==b[j]) {
					aCount++;
				}
			}
		}
		if(aCount==a.length) {
			ch1=true;
		}
		int bCount=0;
		boolean ch2=false;
		for(int i=0; i<b.length;i++) {
			for(int j=0; j<a.length;j++) {
				if(b[i]==a[j]) {
					bCount++;
				}
			}
		}
		if(bCount==b.length) {
			ch2=true;
		}
		
		return (ch1&&ch2);
	}
	
	public static int index2(int[] ratio, int value) {
    	for (int i=0; i<ratio.length;i++) {
    		if (ratio[i]==value) {
    			return i;
    		}
    	}
    	return -1;
    }
	
	private static int count(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int lineCount = 0;

        while (reader.readLine() != null) {
            lineCount++;
        }

        reader.close();

        return lineCount-7;
    }
	
	private static double[][] readCoordinates(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().equals("NODE_COORD_SECTION")) {
                break;
            }
        }

        double[][] coordinates = new double[count(filePath)][2];
        int index = 0;

        while ((line = reader.readLine()) != null) {
            if (line.trim().equals("EOF")) {
                break;
            }

            String[] parts = line.trim().split("\\s+");
            coordinates[index][0] = Double.parseDouble(parts[1]); 
            coordinates[index][1] = Double.parseDouble(parts[2]); 

            index++;
        }

        reader.close();

        return coordinates;
    }
    

    public static double[] distancecalc (double[][] coordinates, int[][] paths) { //fitness
    	double[] distances=new double[paths.length];
    	
    	double x1;
    	double y1;
    	double x2;
    	double y2;
    	for(int i=0; i<paths.length; i++) {
    		double dis=0;
    		for(int j=0; j<paths[0].length; j++) {
    		 x1 = coordinates[paths[i][j]][0];
             y1 = coordinates[paths[i][j]][1];
             
             x2 = coordinates[paths[i][(j + 1) % paths[0].length]][0];
             y2 = coordinates[paths[i][(j + 1) % paths[0].length]][1];


             double dx = x2 - x1;
             double dy = y2 - y1;
             double segmentDistance = Math.sqrt(dx * dx + dy * dy);

             dis += segmentDistance;
    		}
    		distances[i]=dis;
    	}
    	
    	return distances;
    }
    
    
    public static int best(double[] distances) { //best solution
    	double min= 999999999;
    	int index=0;
    	for(int i=0; i<distances.length;i++) {
    		if(distances[i]<=min) {
    			min=distances[i];
    			index=i;
    		}
    	}
    		return index;
    	
    
    	    }
    
    public static int index(double[] ratio, double value) {
    	for (int i=0; i<ratio.length;i++) {
    		if (ratio[i]==value) {
    			return i;
    		}
    	}
    	return -1;
    }
    
  //RANK SELECTION
    public static int parent2(double[] distances) {
    	double[] sorteddis0=new double[distances.length];
    	double[] sorteddis=new double[distances.length];
    	double[] ratio= new double[distances.length];
    		for(int i=0; i<distances.length;i++) {
    			sorteddis0[i]=distances[i];
    		}
    		Arrays.sort(sorteddis0);
    		   
    	        int j=sorteddis.length - 1;

    	        for (int i = 0; i < sorteddis0.length; i++) {
    	            sorteddis[j] = sorteddis0[i];
    	            j--;
    	        }
    		
    		for(int i=0; i<sorteddis.length;i++) {
    			ratio[i]=i+1;
    			 
    		}
    		double sum=0;
    		for(int i=0; i<ratio.length; i++) {
    			sum=sum+ratio[i];
    		}
    		
    		for(int i=0; i<ratio.length;i++) {
    			ratio[i]=ratio[i]/sum;
    			
    		}
    		
    		Random random = new Random();
            double randomValue = random.nextDouble();
            double cumulativeWeight = 0;
            
            for (int i = 0; i < distances.length; i++) {
                cumulativeWeight=cumulativeWeight+ ratio[i];
                if (randomValue <= cumulativeWeight) {
                    return index(distances, sorteddis[i]);
                }
            }
            
            return -1;
        }

    
    
    //ROULLETE SELECTION
    public static int parent1(double[] distances) {
        double[] ratio = new double[distances.length];
        double[] sortedRatio = new double[ratio.length];
        double sum = 0;

        for (int i=0; i<ratio.length; i++) {
            sum+=distances[i];
        }

        for (int i=0; i<ratio.length; i++) {
            ratio[i]=distances[i]/sum;
        }

        for (int i=0; i<ratio.length; i++) {
            sortedRatio[i]=ratio[i];
        }

        Arrays.sort(sortedRatio);
        
        Random random=new Random();
        double randomValue=random.nextDouble();
        double cumulativeWeight=0;

        for (int i=0; i<distances.length; i++) {
            cumulativeWeight+= sortedRatio[i];

            if (randomValue<=cumulativeWeight) {
                return index(ratio, sortedRatio[i]);
            }
        }
        
        return -1;
    }

    
  //Insert mutation function
    public static int[] mutation1(int[] child) {
    	Random random=new Random();
    	int r1=0;
    	int r2=0;
    		
    	 while(r1==r2) {
    	 r1=random.nextInt(child.length);
    	 r2=random.nextInt(child.length);
    	 
    	 }
    	
    	 int swap;
    	 if (r1>r2) {
    		 swap=r2;
    		 r2=r1;
    		 r1=swap;
    	 }
    	 for(int i=r2; i>r1+1; i--) { 
    		 int s=0;
    		 s=child[i];
    		
   		 child[i]=child[i-1];
    		 child[i-1]=s;
    		 
    	 }
    	
    	    
    	return child;
    }
    
  //RANDOM SLIDE MUTATION METHOD:
    public static int[] mutation2 (int[] child) {
    	Random random=new Random();
    	int r1=0;
    	int r2=0;
    	
    	int check=0;
    	 while((r1==r2) || (check<=2)) {
    	 r1=random.nextInt(child.length);
    	 r2=random.nextInt(child.length);
    	 check=r1-r2;
    	 check=Math.abs(check);
    	 }
    	 int swap=0;
    	 if (r1>r2) {
    		 swap=r2;
    		 r2=r1;
    		 r1=swap;
    	 }
    	
    	 int s1=0;
    	 int s2=0;
    	 
    	 s1=child[r1];
    	 child[r1]=child[r2-1];
    	 child[r2-1]=s1;
    	 
    	 s2=child[r2];
    	 child[r2]=child[r1+1];
    	 child[r1+1]=s2;
    	 
    	
    	return child;
    }
    
    public static int[] mutation(int[] child) {
    	Random random =new Random();
    	double r=random.nextDouble();
    	if(r<0.05) {
    		System.out.println("mutation");
    		double r1=random.nextDouble();
    		if(r1<0.5) {
    			child=mutation1(child);
    			return child;
    			
    		}
    		else {child=mutation2(child);
    		return child;}
    	}
    	return child;
    }
    
    //crossover function that takes the parents as a parameter and return a child
    public static int[] child1(int[] p1, int[] p2) {
		int[] child=new int[p1.length];
		int length=p1.length;
		int n=0;
		

		for(int i=0; i<length;i++) {
			if(p1[i]!=p2[i]) {
				n=i;
				length=i;
				break;
			}
		}
		for(int i=0; i<p2.length;i++) {
			child[i]=p2[i];
		}
		child[n]=p1[n];
		
			int newi=0;
			newi=index2(p1, p2[n]);
			int c=0;
			while(p2[newi]!=p1[n]) {
				    
				child[newi]=p1[newi];             
				newi= index2(p1, p2[newi]);
				     
			}
			child[newi]=p1[newi];
			child=mutation(child);
		return child;
	}
    
     //new generation function that takes the old generation as a parameter
    //and return a paths[][] as the new generation
    
    public static int[][] newGeneration(int[][] oldGeneration, double[] distances){
    	int p1=0;
    	int p2=0;
    	int best= best(distances);
    	
    	int[][] newgen=new int[oldGeneration.length][oldGeneration[0].length];
    	//insert the best
    	newgen[0]=oldGeneration[best];
    	
    	for (int i=1; i<newgen.length; i++) {
    	    int[] parent1;
    	    int[] parent2;
    	        parent1=new int[oldGeneration[0].length];
    	        parent2=new int[oldGeneration[0].length];
    	    
    	        p1=parent1(distances);
    	        p2=parent2(distances);

    	        while (p1==p2) {
    	            p1=parent1(distances);
    	            p2=parent2(distances);
    	        }

    	        parent1=oldGeneration[p1];
    	        parent2=oldGeneration[p2];
    	           	       	        
    	        newgen[i]=child1(parent1, parent2);
    	        
     	}
    	
    	return newgen;
    }

                
    public static int worst (double[] distances) {
    	double max=0;
    	int index=0;
    	for (int i=0; i<distances.length; i++) {
    		if(max<=distances[i]) {
    			max=distances[i];
    			index=i;
    		}
    	}
    	return index;
    }
    
    //average function that takes the population fitness values as a parameter and return the avg
    public static double average(double[] distances) {
    	double dis=0;
    	double result;
    	for (int i=0; i<distances.length; i++) {
    		dis+=distances[i];
    	}
    	result=dis/distances.length;
    	return result;
    }
    
    public static void main(String[] args) throws IOException {
    	
    	
        Scanner userInput = new Scanner(System.in);
        double[][] coordinates = null;
        System.out.print("Enter the file path: ");
        String file = userInput.nextLine();
        
        try {
            coordinates = readCoordinates(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    
             
        int[][] paths=new int[100][count(file)];//+1
        Random random = new Random();
        int[] points = new int[count(file)];
        for (int i = 0; i <100; i++) {
            Set<Integer> uniqueNumbers = new HashSet<>();
           
            for (int j=0; j<points.length; j++) {
                int r;
                do {
                    r = random.nextInt(count(file));
                } while (uniqueNumbers.contains(r));

                uniqueNumbers.add(r);
                points[j] = r;
                paths[i][j]=points[j];
                     }
        }
        
        
        double[] distances=new double[paths.length];
       	int indexOfBest=0;
       	int indexOfWorst=0;
       
        for(int i=1; i<=100; i++) {
        	distances=distancecalc(coordinates ,paths);
        	
        	indexOfBest=best(distances);
        	indexOfWorst= worst(distances);
        	
        	System.out.println("the best value fot the "+ i+ "th "+ 
                 "generation is: " + distances[indexOfBest]);//write the path sonra 

        	System.out.println("the worst value fot the "+ i+ "th "+ 
                        "generation is: " + distances[indexOfWorst]);
        		
        	System.out.println("the average of the "+ i+ "th "+ 
                    "generation is: " + average(distances));	
        	System.out.println("--------------------------------------------------");
        	paths=newGeneration(paths, distances);
        	
        }
    
    }
   
}