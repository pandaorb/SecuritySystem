import java.util.Map;
import java.util.HashMap;
public class JsonObject implements Json{
	private Map<String,Json> m;
	public JsonObject(){
		m=new HashMap<String,Json>();
	}
	public void put(String k,Json val){
		m.put(k,val);
	}
	public Json get(String k){
		return m.get(k);
	}
	public void clear(){
		m.clear();
	}
	public String toString(){
		String out="{";
		for(String k:m.keySet())
			out+="\""+k+"\""+":"+m.get(k)+",";
		return out.replaceAll(",$","")+"}";
	}
}
