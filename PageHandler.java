import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import java.util.Map;
import java.util.HashMap;

public class PageHandler implements HttpHandler{
	private Map<String,File>files;
	private RemoteServerInterface blob;
	private String login,home;
	public PageHandler(RemoteServerInterface b,String loginIn,String homeIn,String...filenames){
		blob=b;
		files=new HashMap<String,File>();
		for(String filename:filenames){
			String[]split=filename.split("/");
			files.put(filename,new File(split[split.length-1]));
		}
		login=loginIn;home=homeIn;
		String[]split=login.split("/");
		files.put(login,new File(split[split.length-1]));
		split=home.split("/");
		files.put(home,new File(split[split.length-1]));
	}
	public void handle(HttpExchange t) throws IOException{
	//consume request body
		for(InputStream in=t.getRequestBody();in.read()>0;)in.skip(Long.MAX_VALUE);
	//set response header
		t.getResponseHeaders().set("content-type","text/html");
	//retrieve file descriptor
		System.out.println("serving page: "+t.getRequestURI().getPath());
		File file;
		if(blob.getLogin())file=files.get(t.getRequestURI().getPath());
		else file=files.get(login);
		if(blob.getLogin()&&t.getRequestURI().getPath().equals(login))
			file=files.get(home);
		byte[]blob="invalid page".getBytes();
		if(file==null){
		//send a 404 error if the filename is not found
			System.out.println("file not found");
			t.sendResponseHeaders(404,blob.length);
		}else{
			blob=new byte[(int)file.length()];
			t.sendResponseHeaders(200,file.length());
			new BufferedInputStream(new FileInputStream(file)).read(blob,0,blob.length);
		}
	//write file to output buffer
		OutputStream os=t.getResponseBody();
		os.write(blob,0,blob.length);
		os.close();
	}
}
