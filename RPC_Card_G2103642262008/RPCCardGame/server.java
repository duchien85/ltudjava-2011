public class server {

	public static void main(String arg[]){
		
		try{
			System.out.println("Server is running ....");
			new RemoteObj().run();
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	
	}
}
