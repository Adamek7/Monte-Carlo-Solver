
public class Variable {
	public double upperRestriction;
	public double lowerRestriction;
	public boolean strongUpperRestriction;
	public boolean strongLowerRestriction;
	Variable(double upperRestriction, double lowerRestriction, boolean strongUpperRestriction, boolean strongLowerRestriction){
		this.upperRestriction = upperRestriction;
		this.lowerRestriction = lowerRestriction;
		this.strongUpperRestriction = strongUpperRestriction;
		this.strongLowerRestriction = strongLowerRestriction;
	}
}
