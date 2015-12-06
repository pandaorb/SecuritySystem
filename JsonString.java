public class JsonString implements Json{
	private String s;
	public JsonString(String s){
		set(s);
	}
	public String get(){
		return s;
	}
	public void set(String s){
		String o="";
		for(int i=0;i<s.length();i++)
			switch(s.charAt(i)){
				case'\\':
					o+="\\\\";
					break;
				case'\"':
					o+="\\\"";
					break;
				case'\b':
					o+="\\b";
					break;
				case'\f':
					o+="\\f";
					break;
				case'\n':
					o+="\\n";
					break;
				case'\r':
					o+="\\r";
					break;
				case'\t':
					o+="\\t";
					break;
				default:
					o+=s.charAt(i);
			}
		this.s=o;	
	}
	public String toString(){
		return '"'+s+'"';
	}
}
