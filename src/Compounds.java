/*Authors:
 * Gil Pasi 	- 206500936
 * Heba Abu-Kaf - 323980441
 * */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Compounds {
	
	//Shared properties
	SharedResources res = new SharedResources();
	static final int DEFAULT_TICKETS = 10;
	
	//Areas
	HayArea ha;
	WaterArea wa;
	StrayArea sa;


	
	/**
	 * Let us mark each of the farm rules:
	 * 
	 * (1) At any given time, there will be up to X cows in the pasture.
	 * (2) At any given time, there will be up to Y cows in the barn.
	 * (3) A cow can only go for a walk after all the other cows have finished grazing in the pasture and the barn.
	 * (4) A cow can only enter the barn after finishing grazing in the pasture.
	 * (5) Each cow spends between 2 to 10 seconds in either the pasture
	 *  	or the barn (the number is randomized for each cow and each area).
	 **/
	

	
	public void appendCow () {
		res.cowshed.release();
		
		
		//Initialize semaphores if not exits already
		if(res.haySem == null)
			res.setTickets(new ArrayList<>(Collections.nCopies(3,DEFAULT_TICKETS)));
		//Fill all 3 cells with the DEFUALT_TICKETS value	
	
	}
	
	
	public synchronized void eat(CowThread cow) throws InterruptedException {
		res.enterArea("hay", cow);
		res.cowshed.acquire();//Cow is now occupied
    	
		System.out.println("Cow " + cow.ID + " is eating");
		randomSleep();
		
		res.enterArea("water", cow);;/*Implicit demand: The cow 
							drinks only when it is done eating. */
		res.leaveArea("hay", cow);

	}

	
	public synchronized void drink(CowThread cow) throws InterruptedException {
		
		System.out.println("Cow " + cow.ID + " is drinking");
		randomSleep();
		
		res.leaveArea("water", cow);
    	notifyAll();
		
	}	
	
	public synchronized void walk(CowThread cow) throws InterruptedException {
		
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
    	
		
    	while//Order is IMPORTANT!
        	   (res.cowshed.availablePermits() != 0 ||
        		res.haySem.availablePermits() != ((int)res.allSemsPermits.get(0))||
        		res.waterSem.availablePermits() != ((int)res.allSemsPermits.get(1))) {
    		System.out.println("Culprit! a cow tries to walk before all other cows are ready");
    		wait();//Demand (3) - wait for all the cows    		
    	}       		
		res.enterArea("walk", cow);
		System.out.println("Cow " + cow.ID + " is walking");
		randomSleep();
	}
	
	
	
	private void randomSleep() throws InterruptedException {
        Random random = new Random();
        final int MIN = 2;
        final int MAX = 10;
        int sleepTime = random.nextInt(MAX - MIN + 1) + MIN; //Demand (5)-random waiting in the range 2-10 
        Thread.sleep(sleepTime * 1000);		
	}
}
