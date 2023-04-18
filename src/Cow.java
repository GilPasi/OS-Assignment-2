/*Authors:
 * Gil Pasi 	- 206500936
 * Heba Abu-Kaf - 323980441
 * */
public class Cow extends Thread{
	private static int idGenerator = 1;
	private int id;
	private static Compounds commonFarm = null;
	
	
	public Cow() {
		id = idGenerator++;//A unique id for each cow
		if(commonFarm == null)createFarm(new Compounds());
		commonFarm.addCow();
	}
	
	public void run() {commonFarm.treat(id);}
	
	public void createFarm(Compounds newFarm) {commonFarm = newFarm;}
	
}
