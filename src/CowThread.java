/*Authors:
 * Gil Pasi 	- 206500936
 * Heba Abu-Kaf - 323980441
 * */
public class CowThread extends Thread{
	private static int idGenerator = 1;
	final int ID;
	private static Compounds comp = null;
	
	
	public CowThread() {
		ID = idGenerator++;//A unique id for each cow
		if(comp == null)comp = new Compounds();//Create if not exists
		comp.appendCow();
	}
	
	public void run() {
		
		try {
        	comp.eat(this);//Demand (4) - drink after eating
        	comp.drink(this);
        	comp.walk(this);
        	
		} catch (InterruptedException e) {
			System.err.println("Cow " + ID + " was interrupted" );
			e.printStackTrace();
		}
		
	}
	
	
}
