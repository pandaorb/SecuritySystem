import java.util.List;
import java.util.ArrayList;
public class JsonArray implements Json{
	private List<Json>l;
	public JsonArray(){
		l=new ArrayList<Json>();
	}
	public void add(Json val){
		l.add(val);
	}
	public Json get(int i){
		return l.get(i);
	}
	public void clear(){
		l.clear();
	}
	public void remove(int i){
		l.remove(i);
	}
	public void remove(Json val){
		l.remove(val);
	}
	public String toString(){
		String out="[";
		for(Json i:l)
			out+=i+",";
		return out.replaceAll(",$","")+"]";
	}
}
