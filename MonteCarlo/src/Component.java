
public class Component {
	private double factor;
	private int power;
	public double value;
	public Inequality ie = null;
	Component(int factor, int power){
		this.factor = factor;
		this.power = power;
	}
	Component(double factor, int power, boolean strong, boolean larger, int value){
		this.factor = factor;
		this.power = power;
		this.ie = new Inequality(strong, larger);
		this.value = value;
	}
	public void setFactor(double factor){
		this.factor = factor;
	}
	public double getFactor(){
		return this.factor;
	}
	public void setPower(int power){
		this.power = power;
	}
	public int getPower(){
		return this.power;
	}
}
