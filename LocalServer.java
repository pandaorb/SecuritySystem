import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

public class LocalServer{
	public static void main(String[]args)throws Exception{
		RemoteServerInterface blob=new RemoteServerInterface();
		if(args.length==0){
			System.out.println("root directory expected");
			return;
		}HttpServer s=HttpServer.create(
			new InetSocketAddress(
				InetAddress.getLoopbackAddress(),80
			),0
		);s.createContext(args[0]+"json",new JsonHandler(blob))
		.getFilters().add(new PostGetParamFilter());
		s.createContext(args[0],new PageHandler(blob,args[0]+"login.html",args[0]+"main.html",args[0]+"logs.html",args[0]+"cam.html",args[0]+"home.jpe"));
		s.start();
	}
}
