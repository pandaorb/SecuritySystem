public class RemoteServerInterface{
	public static final int
	DISARMED=0,ARMED_HOME=1,ARMED_AWAY=2;
	private static final String
	key="12345";
	private boolean
	camera=false,login=false;
	private int state=DISARMED;

	public Json validate(String keyIn){
		if(key.equals(keyIn)){
			login=true;
			JsonObject o=new JsonObject();
			o.put("key",new JsonString("valid"));
			return o;
		}
		login=false;
		JsonObject o=new JsonObject();
		o.put("key",new JsonString("invalid"));
		return o;
	}

	public void logout(){
		login=false;
	}

	public boolean getLogin(){
		return login;
	}

	public Json viewCam(){
		if(camera){
			JsonObject o=new JsonObject();
			o.put("cam",new JsonString("online"));
			return o;
		}
		JsonObject o=new JsonObject();
		o.put("cam",new JsonString("offline"));
		return o;
	}

	public Json toggleCam(){
		camera=!camera;
		return viewCam();
	}

	public Json getLogs(){
		JsonObject o=new JsonObject();
		o.put("2/20/2015 1:04",new JsonString("window sensor tripped"));
		o.put("12/25/2015 0:00",new JsonString("chimney motion sensor tripped"));
		o.put("1/5/2016 17:47",new JsonString("back door sensor tripped"));
		return o;
	}

	public Json armAway(){
		JsonObject o=new JsonObject();
		if(state!=DISARMED){
			o.put("arm",new JsonString("already armed"));
			return o;
		}
		state=ARMED_AWAY;
		o.put("arm",new JsonString("arm away success"));
		return o;
	}

	public Json armHome(){
		JsonObject o=new JsonObject();
		if(state!=DISARMED){
			o.put("arm",new JsonString("already armed"));
			return o;
		}
		state=ARMED_HOME;
		o.put("arm",new JsonString("arm home success"));
		return o;
	}
	
	public Json disarm(){
		JsonObject o=new JsonObject();
		if(state==DISARMED){
			o.put("arm",new JsonString("already disarmed"));
			return o;
		}
		state=DISARMED;
		o.put("arm",new JsonString("disarm success"));
		return o;
	}
}
