import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import java.util.Map;
import java.util.List;

public class JsonHandler implements HttpHandler{
	private RemoteServerInterface blob;
	public JsonHandler(RemoteServerInterface b){
		blob=b;
	}
	public void handle(HttpExchange t) throws IOException{
	//get parameters
		Map<String,List<String>>params=
			(Map<String,List<String>>)t.getAttribute("parameters");
		System.out.println("received parameters: ");
		for(String i:params.keySet()){
			System.out.println(i+":");
			for(String j:params.get(i))
				System.out.println("\t"+j);
		}
	//parse json
		String out=new JsonNull().toString();
		if(params.get("submit")==null){
			out=new JsonNull().toString();
			t.sendResponseHeaders(404,out.length());
		}else if(params.get("submit").get(0).equalsIgnoreCase("armAway")){
			out=blob.armAway().toString();
			t.sendResponseHeaders(200,out.length());
		}else if(params.get("submit").get(0).equalsIgnoreCase("armHome")){
			out=blob.armHome().toString();
			t.sendResponseHeaders(200,out.length());
		}else if(params.get("submit").get(0).equalsIgnoreCase("disarm")){
			out=blob.disarm().toString();
			t.sendResponseHeaders(200,out.length());
		}else if(params.get("submit").get(0).equalsIgnoreCase("logs")){
			out=blob.getLogs().toString();
			t.sendResponseHeaders(200,out.length());
		}else if(params.get("submit").get(0).equalsIgnoreCase("validate")){
			if(params.get("key")==null){
				out=new JsonNull().toString();
				t.sendResponseHeaders(404,out.length());
			}else{
				out=blob.validate(params.get("key").get(0)).toString();
				t.sendResponseHeaders(200,out.length());
			}
		}else if(params.get("submit").get(0).equalsIgnoreCase("viewCam")){
			out=blob.viewCam().toString();
			t.sendResponseHeaders(200,out.length());
		}else if(params.get("submit").get(0).equalsIgnoreCase("toggleCam")){
			out=blob.toggleCam().toString();
			t.sendResponseHeaders(200,out.length());
		}else if(params.get("submit").get(0).equalsIgnoreCase("logout")){
			blob.logout();
			JsonObject o=new JsonObject();
			o.put("logout",new JsonString("success"));
			out=o.toString();
			t.sendResponseHeaders(200,out.length());
		}else{
			out=new JsonNull().toString();
			t.sendResponseHeaders(404,out.length());
		}
		System.out.println("responding with JSON: "+out);
	//set response header
		t.getResponseHeaders().set("Content-Type","application/json");
	//write to output stream
		OutputStream os=t.getResponseBody();
		os.write(out.getBytes());
	//close output stream
		os.close();
	}
}
