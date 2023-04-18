import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cow extends Thread{
	
	//Semaphores
	static final int DEFAULT_TICKETS = 10;
	private static ArrayList allSemsPermits;
	private static Semaphore haySem;
	private static Semaphore waterSem;
	private static Semaphore straySem;
	private static Semaphore cowshed = new Semaphore(0); 
									/*Counts the cows that are neither
	 								 eating nor drinking nor straying*/
	Object strayLock = new Object();
	
	//Generators
	private static int idGenerator = 1;
	
	//Personal properties
	private int id;
	private String status;
	
	public Cow() {
		id = idGenerator++;//A unique id for each cow
			cowshed.release();
			
		
		//Initialize semaphores if not exits already
		if(haySem == null)
			setTickets(new ArrayList<>(Collections.nCopies(3,DEFAULT_TICKETS)));
		//Fill all 3 cells with the DEFUALT_TICKETS value		
	} 
	
	
	public void eat() {
        try {
        	haySem.acquire();
        	cowshed.acquire();//Cow is now occupied
        	randomSleep();
        	haySem.release();
		} catch (InterruptedException e) {
			System.out.println("Cow" + id + "interruped while eating");
			e.printStackTrace();
		}
		System.out.println("Cow " + id + " ate");

	}
	
	
	public void drink() {
        try {
        	waterSem.acquire();
        	randomSleep();
        	waterSem.release();
		} catch (InterruptedException e) {
			System.out.println("Cow" + id + "interruped while drinking");
			e.printStackTrace();
		}
		System.out.println("Cow " + id + " drank");
		
		synchronized (strayLock) {notifyAll();} 
	}
	
	
	public void walk() {
		/**This method let the cow walk only when all of its peers 
		 * are done eating and drinking.
		 * By preserving  the check order:
		 * unoccupied cows -> eating cows -> drinking cows
		 * all cows are necessarily checked.
		 * 
		 * If for example we check the food part first, by the time 
		 * we check the unoccupied cows , some of them proceeded
		 * to the food section.
		 * 
		 *  This may cause a correctness error.
		 * */
        try {
        	while
        		//Order is IMPORTANT!
        	   (cowshed.availablePermits() != 0 ||
        		haySem.availablePermits() != ((int)allSemsPermits.get(0))||
        		waterSem.availablePermits() != ((int)allSemsPermits.get(1))) {        		
        		synchronized (strayLock) {wait();}
        	}
        	
        		
        	
        	straySem.acquire();
        	randomSleep();
        	straySem.release();
		} catch (InterruptedException e) {
			System.out.println("Cow" + id + "interruped while walking");
			e.printStackTrace();
		}
		System.out.println("Cow " + id + " walked");
		synchronized (strayLock) {notifyAll();}
	}
	
	
	public void run() {
		eat();
		drink();/*Implicit demand: The cow
		 		drinks only when its done eating. */
		walk();
	}
	
	
	private void randomSleep() throws InterruptedException {
        Random random = new Random();
        //TODO: revert MIN = 2 MAX = 10
        final int MIN = 1;
        final int MAX = 1;
        int sleepTime = random.nextInt(MAX - MIN + 1) + MIN;
        sleep(sleepTime * 1000);

		
	}
	
	
	public static void setTickets(ArrayList semsPermits) {
		/**This function gets an array with 3 cells.
		 * The first cell represents the tickets for the hay-semaphore.
		 * The second cell represents the tickets for the water-semaphore.
		 * The first cell represents the tickets for the straying-semaphore.
		 * */
		allSemsPermits = semsPermits;
		haySem = new Semaphore((int) semsPermits.get(0));
		waterSem = new Semaphore((int) semsPermits.get(1));
		straySem = new Semaphore((int) semsPermits.get(2));

	}
}
