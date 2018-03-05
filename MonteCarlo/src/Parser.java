import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
	private static BufferedReader data;
	public static ArrayList<Component> purposeFunction = new ArrayList<>();
	public static boolean maxOfPurposeFunction;
	public static ArrayList<ArrayList<Component>> restrictions = new ArrayList<>();
	public static ArrayList<Variable> variablesRestrictions = new ArrayList<>();
	private static int[] numberOfEachCriteria = new int[2];
	private static void loadFile(String name){
		try{
			FileInputStream is = new FileInputStream(name);
			data = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String[] tmp = (data.readLine()).split(" ");
			int i = 0;
			for(String t : tmp){
				numberOfEachCriteria[i] = Integer.parseInt(t);
				i++;
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void parse(){
		loadFile("ex1");
		try{
			String ln = data.readLine();
		
			String[] parts2 = ln.split("\\+?\\-?\\d+x\\d+\\^");
			
			if(parts2[parts2.length-1].split("->")[1].equals("max")){
				maxOfPurposeFunction = true;
			}else{
				maxOfPurposeFunction = false;
			}
			parts2[parts2.length-1] = parts2[parts2.length-1].split("->")[0];
			String[] parts1 = ln.split("x\\d+\\^\\d+\\+?");
			parts1 = Arrays.copyOf(parts1, parts1.length-1);
			int i=1;
		    for (String part : parts1) {
		    	purposeFunction.add(new Component(Integer.parseInt(part),Integer.parseInt(parts2[i])));
		    	i++;
		    }
		    boolean strong;
			boolean larger;
		    for(i = 0; i < numberOfEachCriteria[0]; ++i){
		    	ln = data.readLine();
				parts2 = ln.split("\\+?\\-?\\d+x\\d+\\^");
				/*for(String p : parts2){
					System.out.println(p);
				}*/
				int value = Integer.parseInt(parts2[parts2.length-1].split("[<|>][=]?")[1]);
					
				switch (parts2[parts2.length-1].split("\\d")[1]){
				case "<":
					strong = true;
					larger = false;
					break;
				case "<=":
					strong = false;
					larger = false;
					break;
				case ">":
					strong = true;
					larger = true;
					break;
				case ">=":
					strong = false;
					larger = true;
					break;
				default:
					strong = false;
					larger = false;
				}
				parts2[parts2.length-1] = parts2[parts2.length-1].split("[<|>][=]?")[0];
				parts1 = ln.split("x\\d+\\^\\d+\\+?");
				ArrayList<Component> tmp = new ArrayList<>();
				for(int j = 0; j < parts1.length-1; ++j){
					tmp.add(new Component(Double.parseDouble(parts1[j]),Integer.parseInt(parts2[j+1]),strong,larger,value));
				}
				restrictions.add(tmp);
		    }
		    for(i = 0; i < numberOfEachCriteria[1]; ++i){
		    	ln = data.readLine();
				parts1 = ln.split("<[=]?");
				
				double min = Double.parseDouble(parts1[0]);
				double max = Double.parseDouble(parts1[2]);
				boolean lowerStrong = true;
				boolean upperStrong = true;
				parts1 = ln.split("x\\d");
	
				
				switch (parts1[0].split("\\d")[0]){
				case "<":
					lowerStrong = true;
					
					break;
				case "<=":
					lowerStrong = false;
					break;
				
				default:
					strong = false;
				}
				switch (parts1[0].split("\\d")[1]){
				case "<":
					upperStrong = true;
					
					break;
				case "<=":
					upperStrong = false;
					break;
				
				default:
					upperStrong = false;
				}
				variablesRestrictions.add(new Variable(max, min, upperStrong, lowerStrong));
				
			
		    }
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
}
