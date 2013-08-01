import java.util.ArrayList;


public class Handler {
	public ArrayList<String> array = null;
	public void save(String inputstream){
	array.add(inputstream);
	System.out.println(array);



		/*String from=inputstream.substring(0,2);
		String to=inputstream.substring(2,4);
		System.out.println(from);
		System.out.println(to);*/
	}
	final public void setGame1(boolean game1){
		game1=!game1;
	}
	public boolean returnGame1()
	{
		return game1;
	}
}
