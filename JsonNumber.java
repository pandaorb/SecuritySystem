public class JsonNumber implements Json{
	private double d;
	public JsonNumber(double d){
		set(d);
	}
	public double get(){
		return d;
	}
	public void set(double d){
		this.d=d;
	}
	public String toString(){
		return (""+d).replaceAll(".0$","");
	}
}
