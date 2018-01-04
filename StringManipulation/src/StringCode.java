/*2017
 * Author: Annahita Doulatshahi
 * 
 * Class Description: 
 * On program execution, system waits for input from stdIn. Error Check limited. Program will crash with incorrect input format
 * 
 * Example Input: PRTF:AYU,0,10;VFR,20,30;GYR,10,30|BENCH:AYU,50,10;VFR,30,30;FWT,30,20
 * 
 * Referenced: QUESTION WAS GIVEN TO ME BY A FRIEND FOR PRACTICE: Description of question in README
 * 
 * */


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class StringCode {
	 private static List<List<String>> comparing = new ArrayList<List<String>>();
	 private static List<List<String>> bench = new ArrayList<List<String>>();

	 public static void main(String[] args) throws IOException {
		 //in = to read from stdIn
	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
	    String inputString;
	    
	     //while nothing in stdIn just wait. Once a line present run code.
	    while ((inputString = in.readLine()) != null) {
	        
	    	//function 'split' defined below main(): takes input and separates string into usable data 
	        split(inputString);
	        
	        //gathering more of the asset information
	        int siz = comparing.size();
			int siz2 = bench.size();
			
			int[] unique = new int[siz2]; //accounting for the fact that assets in portfolio are not always the same as in benchMark
			int[] navP = new int[siz]; //portfolio(quantity(X0)*price(X0))
			int[] navB = new int[siz2]; //benchMark(quantity(X0)*price(X0))
			int test = 0;
			
			double navPORT = 0.0; //NAV(portfolio)
			double navBench = 0.0;//NAV(benchMark)
			
			List<String> comp = null;
			List<String> ben = null;
		
			List<String[]> results = new ArrayList<String[]>(); //first to store name, second to store result.List because we don't know how many are unique
			String[] tempResult = null;
			
			boolean compared = false;
			
			//sum values of assets NAV(portfolio) 
			for(int i = 0; i < siz; i++){
				comp = comparing.get(i);
				navP[i] = Integer.parseInt(comp.get(1)) * Integer.parseInt(comp.get(2));
				navPORT += navP[i];
			}
			//sum values of assets NAV(benchMark)
			for(int i = 0; i < siz2; i++){
				ben = bench.get(i);
				navB[i] = Integer.parseInt(ben.get(1)) * Integer.parseInt(ben.get(2));
				navBench += navB[i];
			}
			//Main Calculation and UniqueNess Check
			for(int i = 0; i < siz; i++){
				comp = comparing.get(i);
				compared = false;
				
				for(int j = 0; j < siz2; j++){
					ben = bench.get(j);
					//asset is in both Portfolio and BenchMark
					if(comp.get(0).equals(ben.get(0))){
						tempResult = new String[2];
						tempResult[0] = comp.get(0);
						//do calculation 
						tempResult[1] = "" + (((navP[i] * 100) / navPORT) - ((navB[j] * 100) / navBench));
						results.add(tempResult);
						//keep track of what's been compared
						unique[j] = 2;
						compared = true;
					}
				}
				//If asset in Portfolio but not in BenchMark
				if(!compared){
					tempResult = new String[2];
					tempResult[0] = comp.get(0);
					//do calculation 
					tempResult[1] = "" + ((navP[i] * 100) / navPORT);
					results.add(tempResult);
				}
			}
			//Run through BenchMark assets, if an asset was in BenchMark but not Portfolio, calculate here
			for(int k = 0;k < siz2; k++){
				test = unique[k];
				if(test != 2){
					ben = bench.get(k);
					tempResult = new String[2];
					tempResult[0] = ben.get(0);
					//do calculation 
					tempResult[1] = "-" + ((navB[k] * 100) / navBench);
					results.add(tempResult);
				}
			}
			
			//sort results.array[0] (by name of asset) using basic java sorter
			Collections.sort(results, new Comparator<String[]>() {
			    public int compare(String[] v1, String[] v2) {
			        return v1[0].compareTo(v2[0]);
			    }
			});
			
			//OUTPUT 
			String seperator = ",";
			String neg = "-";
			String result = "";
			String[] r = null;
			int rSize = results.size();
			
			for(int i = 0; i < rSize;i++){
				r = results.get(i);
				result = String.format("%.2f",Math.round(Double.parseDouble(r[1]) * 100) / 100.0);// '* 100) / 100.0' tricks compiler into believing double for conversion
				if(i==rSize-1){seperator="";}
				if(r[1].indexOf(neg)!=-1 && result.indexOf(neg)==-1){result=neg+result;}
				System.out.print(r[0] + ":" + result + seperator);
			}
			return;//exit while loop once string has processed
	    }
}
	  
	 private static void split(String input){
	      
	     int splitSpot = input.indexOf('|');
	      
	     String firstHalf = input.substring(input.indexOf(':')+1, splitSpot);
	     String secondHalf = input.substring(splitSpot+1);
	      
	     secondHalf = secondHalf.substring(input.indexOf(':')+2);
	      
	     int position = -1;
	     while((position=splitString(firstHalf,new ArrayList<String>(),1)) != -1){firstHalf = firstHalf.substring(position);};
	     while((position=splitString(secondHalf,new ArrayList<String>(),2)) != -1){secondHalf = secondHalf.substring(position);};		
	  }
	  
	  private static int splitString(String input, List<String> asset, int tracker){
	      String finish = ";";
	      String divide = ",";
	      
	      int position = 0;
	      int indexN = input.indexOf(divide);
	      
	      String first = input.substring(0, indexN);
	      
	      asset.add(first);
	      position = indexN+1;
	      
	      input = input.substring(indexN+1);
	      indexN = input.indexOf(divide);
	      
	      String second = input.substring(0,indexN);
	      
	      asset.add(second);
	      position += indexN+1;
	      
	      input = input.substring(indexN+1);
	      //end
	      indexN = input.indexOf(finish);
	      
	      if(indexN == -1){
	          asset.add(input);
	          if(tracker == 1){
	              comparing.add(asset);
	          }
	          if(tracker == 2){
	              bench.add(asset);
	          }
	          return -1;
	      }
	      else{
	          asset.add(input.substring(0,indexN));
	          if(tracker == 1){
	              comparing.add(asset);
	          }
	          if(tracker == 2){
	              bench.add(asset);
	          }
	          return position += indexN+1;	
	      }
	  }
}