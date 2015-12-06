public class JsonBoolean implements Json{
	private boolean b;
	public JsonBoolean(boolean b){
		set(b);
	}
	public boolean get(){
		return b;
	}
	public void set(boolean b){
		this.b=b;
	}
	public String toString(){
	//boolean to String hack
		return ""+b;
	}
}
