import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/*Authors:
 * Gil Pasi 	- 206500936
 * Heba Abu-Kaf - 323980441
 * */
public class SharedResources {

	
	//Semaphores
	ArrayList<Integer> allSemsPermits;
	Semaphore haySem;//Demand (1) - no more than X cows
	Semaphore waterSem;//Demand (2) - no more than Y cows
	Semaphore cowshed = new Semaphore(0); 
									/*Counts the cows that are neither
	 								 eating nor drinking nor straying*/
	
	public void setTickets(ArrayList<Integer> semsPermits) {
		/**This function gets an array with 2 cells.
		 * The first cell represents the tickets for the hay-semaphore.
		 * The second cell represents the tickets for the water-semaphore.
		 * */
		allSemsPermits = semsPermits;
		haySem = new Semaphore((int) semsPermits.get(0));
		waterSem = new Semaphore((int) semsPermits.get(1));

	}
}
