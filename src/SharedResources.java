
/*Authors:
 * Gil Pasi 	- 206500936
 * Heba Abu-Kaf - 323980441
 * */
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
public class SharedResources {

	
	//Semaphores
	ArrayList<Integer> allSemsPermits;
	Semaphore haySem;//Demand (1) - no more than X cows
	Semaphore waterSem;//Demand (2) - no more than Y cows
	Semaphore cowshed = new Semaphore(0); 
									/*Counts the cows that are neither
	 								 eating nor drinking nor straying*/
	//Areas
	HayArea ha;
	WaterArea wa;
	StrayArea sa = new StrayArea(-1) ;
	
	public void setTickets(ArrayList<Integer> semsPermits ) {
		/**This function gets an array with 2 cells.
		 * The first cell represents the tickets for the hay-semaphore.
		 * The second cell represents the tickets for the water-semaphore.
		 * */
		allSemsPermits = semsPermits;
		
		haySem = new Semaphore((int) semsPermits.get(0));
		ha = new HayArea((int)semsPermits.get(0));
		
		waterSem = new Semaphore((int) semsPermits.get(1));
		wa = new WaterArea((int)semsPermits.get(1));
		

	}
	
	public void enterArea(String area , CowThread cow) throws InterruptedException {
		switch (area) {
			case "hay": 
			haySem.acquire();
			ha.addCow(cow);
			break;
			
			case "water": 
			waterSem.acquire();
			wa.addCow(cow);
			break;
			
			case "walk": 
			sa.addCow(cow);
			break;
			
			default:
				System.err.println("Invalid input in enterArea method");
		}
	}
	
	public void leaveArea(String area , CowThread cow) throws InterruptedException {
		switch (area) {
			case "hay": 
			haySem.release();
			ha.removeCow(cow);
			break;
			
			case "water": 
			waterSem.release();
			wa.removeCow(cow);
			break;
			
			case "walk": 
			sa.removeCow(cow);
			break;
			
			default:
				System.err.println("Invalid input in leaveArea method");
		}
	}
	
}
