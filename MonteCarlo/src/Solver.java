import java.util.ArrayList;
import java.util.Random;

public class Solver {
	
	//private static ArrayList<Point> pointsList = new ArrayList<>();
	private static Point bestPoint = null;
	private static double radius = 0;
	private static double bestValue = 0;
	
	public static void nextIterations(){
		while(radius > 0.0001){
			//pointsList.clear();
			for(int i = 0; i < 500000; ++i){
				Point p = new Point(Parser.purposeFunction.size());
				Random generator = new Random();
				for(int j = 0; j < Parser.purposeFunction.size(); ++j){
					//System.out.println(bestPoint);
					p.coordinates[j] = generator.nextDouble()*radius + bestPoint.coordinates[j];
					//System.out.println(radius);
				}
				if(cheekRestrictions(p)){
					//pointsList.add(p);
					countPurposeFunctionValue(p);}
			}
			radius /= 2;
		}
	}
	
	public static void firstIteratrion(){
		for(int i = 0; i < 500000; ++i){
			Point p = new Point(Parser.purposeFunction.size());
			Random generator = new Random();
			for(int j = 0; j < Parser.purposeFunction.size(); ++j){
				//System.out.println(Parser.variablesRestrictions.size());
				p.coordinates[j] = generator.nextDouble()*Parser.variablesRestrictions.get(j).upperRestriction;
				//System.out.print(p.coordinates[j] + " ");
				if(Parser.variablesRestrictions.get(j).upperRestriction/2 > radius)
					radius = Parser.variablesRestrictions.get(j).upperRestriction/2;
			}
	//System.out.println();
			if(cheekRestrictions(p)){
				//pointsList.add(p);
				countPurposeFunctionValue(p);}
		}
	}
	
	public static void countPurposeFunctionValue(Point p){
		double result = 0;
		int i = 0;
		for(Component c : Parser.purposeFunction){
			result += c.getFactor()*Math.pow(p.coordinates[i], c.getPower());
			i++;
		}
		//System.out.println(result);
		if(Parser.maxOfPurposeFunction){
			if(result > bestValue){
				bestValue = result;
				bestPoint = p;
			}
		}else{
			if(result < bestValue){
				bestValue = result;
				bestPoint = p;
			}
		}
	}
	public static boolean cheekRestrictions(Point p){
		int i = 0;
		
		for(Variable v : Parser.variablesRestrictions){
			//System.out.println(v.strongUpperRestriction);
			if(v.strongUpperRestriction){
				if(p.coordinates[i] >= v.upperRestriction) {
					//System.out.println(p.coordinates[i] + " " + v.upperRestriction);
					return false;}
			}else{
				if(p.coordinates[i] > v.upperRestriction) 
					return false;
			}
			i++;
		}
		for(ArrayList<Component> cl : Parser.restrictions){
			double result = 0;
			i = 0;
			for(Component c : cl){
				result += c.getFactor()*Math.pow(p.coordinates[i],c.getPower());
				i++;
			}
			
			if(cl.get(0).ie.strong){	
				if(cl.get(0).ie.larger){
					if(result <= cl.get(0).value) return false;
				}else{
					if(result >= cl.get(0).value) {
					
						return false;
					}
						
				}
			}else{
				if(cl.get(0).ie.larger){
					if(result < cl.get(0).value) return false;
				}else{
					if(result > cl.get(0).value) return false;
				}
			}
		}
		
		return true;
	}
	
	public static void main(String[] args){
		//System.out.println(Parser.variablesRestrictions.get(0).upperRestriction);
		Parser.parse();
		firstIteratrion();
		if(bestPoint == null){
			System.out.println("Brak rozwi¹zania");
			return;
		}
		nextIterations();
		for(double d : bestPoint.coordinates){
			System.out.println(d);
		}
		System.out.println(bestValue);
		/*for(Variable v : variablesRestrictions){
			System.out.println(v.lowerRestriction + " " + v.upperRestriction);
		}*/
	}
}
