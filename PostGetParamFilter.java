import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
public class PostGetParamFilter extends Filter{
	@Override public String description(){
	//not sure what this is for
		return "This filter parses POST and GET parameters";
	}
	@Override public void doFilter(HttpExchange t,Chain c)
		throws IOException{
	//declare and initialize the parameter attribute
		t.setAttribute("parameters",new HashMap<String,List<String>>());
	//parse get variables to parameters attribute
		if(t.getRequestMethod().equalsIgnoreCase("get"))
			parseGet(t);
	//parse post variables to parameters attribute
		if(t.getRequestMethod().equalsIgnoreCase("post"))
			parsePost(t);
	//not sure what this does
		c.doFilter(t);
	}
	private void parseGet(HttpExchange t)
		throws UnsupportedEncodingException{
				Map<String,List<String>>params=
			(Map<String,List<String>>)t.getAttribute("parameters");
		parseQuery(
			t.getRequestURI().
			getRawQuery(),
			params
		);
	}
	private void parsePost(HttpExchange t)
		throws IOException{
				Map<String,List<String>>params=
			(Map<String,List<String>>)t.getAttribute("parameters");
		parseQuery(
			new BufferedReader(
				new InputStreamReader(
					t.getRequestBody(),
					"utf-8"
				)
			).readLine(),
			params
		);
	}
	private void parseQuery(String query,Map<String,List<String>>params)
		throws UnsupportedEncodingException{
					//split query into pairs by &
		String pairs[]=query.split("[&]");
	//iterate over each pair
		for(String i:pairs){
					//split each pair by =
			String param[]=i.split("[=]");
		//don't do anything with this pair if it's not actually a pair
			if(param.length==0)continue;
		//decode the key and value to strings
						param[0]=URLDecoder.decode(
				param[0],
				System.getProperty("file.encoding")
			);			param[1]=URLDecoder.decode(
				param[1],
				System.getProperty("file.encoding")
			);
		//add a list at the key if there's not one
			if(!params.containsKey(param[0]))
				params.put(param[0],new ArrayList<String>());
		//add the value to the list at the key
			params.get(param[0]).add(param[1]);
		}
	}
}
