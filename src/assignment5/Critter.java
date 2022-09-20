/*
 * CRITTERS Critter.java
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * Adrian Gallegos
 * ag76424
 * 17360
 * Slip days used: 1
 * Spring 2022
 */

package assignment5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

/*
 * See the PDF for descriptions of the methods and fields in this
 * class.
 * You may add fields, methods or inner classes to Critter ONLY
 * if you make your additions private; no new public, protected or
 * default-package code or data can be added to Critter.
 */

public abstract class Critter {

	/* START --- NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE, 
		SQUARE, 
		TRIANGLE, 
		DIAMOND, 
		STAR
	}

	/*
	 * the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the
	 * default color to be the same as you background
	 *
	 * critters must override at least one of the following three methods, it is not
	 * proper for critters to remain invisible in the view
	 *
	 * If a critter only overrides the outline color, then it will look like a
	 * non-filled shape, at least, that's the intent. You can edit these default
	 * methods however you need to, but please preserve that intent as you implement
	 * them.
	 */
	public javafx.scene.paint.Color viewColor() {
		return javafx.scene.paint.Color.BLACK;
	}

	public javafx.scene.paint.Color viewOutlineColor() {
		return viewColor();
	}

	public javafx.scene.paint.Color viewFillColor() {
		return viewColor();
	}

	public abstract CritterShape viewShape();

	/**
	 * @param direction
	 * @param steps
	 * @return null if space is empty, or critter
	 */
	protected final String look(int direction, boolean steps) {
		energy = energy - Params.LOOK_ENERGY_COST;
		int dist = 1;
		int look_x = x_coord;
		int look_y = y_coord;
		if (steps) {
			dist = 2;
		}
		if (direction == 0) {
			look_x += dist;
		}
		else if (direction == 1) {
			look_x += dist;
			look_y -= dist;
		}
		else if (direction == 2) {
			look_y -= dist;
		}
		else if (direction == 3) {
			look_x -= dist;
			look_y -= dist;
		}
		else if (direction == 4) {
			look_x -= dist;
		}
		else if (direction == 5) {
			look_x -= dist;
			look_y += dist;
		}
		else if (direction == 6) {
			look_y += dist;
		}
		else if (direction == 7) {
			look_x += dist;
			look_y += dist;
		}
		for (int i = 0; i < population.size(); i++) {
			if (population.get(i).x_coord == look_x && population.get(i).y_coord == look_y) {
				return population.get(i).toString();
			}
		}
		return null;
	}

	/**
	 * @param critters
	 * @return String that has the stats of each critter
	 */
	public static String runStats(List<Critter> critters) {
		String output = "";
		output = output + "" + critters.size() + " critters as follows -- ";
		Map<String, Integer> critter_count = new HashMap<String, Integer>();
		for (int i = 0; i < critters.size(); i++) {
			String crit_string = critters.get(i).toString();
			critter_count.put(crit_string, critter_count.getOrDefault(crit_string, 0) + 1);
		}
		String prefix = "";
		Iterator<String> itr = critter_count.keySet().iterator();
    	while (itr.hasNext()) {
    		String key = itr.next();
			output = output + prefix + key + ": " + critter_count.get(key);
			prefix = ", ";
		}
		return output;
	}

	/**
	 * Left pane is for the critter world
	 * Right pane is for the user-interface
	 * @param pane
	 */
	public static void displayWorld(GridPane pane) {
		Scene scene = new Scene(pane, (Main.SCREEN_WIDTH * 2) + 50, Main.SCREEN_HEIGHT);
		Main.stage.setScene(scene);
		Main.stage.show();
		GridPane ui = new GridPane();
		GridPane world = new GridPane();
		pane.add(ui, Main.SCREEN_WIDTH, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		pane.add(world, 0, 0);
		Main.color(world);
		Main.colorUI(ui, world);
	}

	/*
	 * END --- NEW FOR PROJECT 5 rest is unchanged from Project 4
	 */

	private int energy = 0;

    private int x_coord;
    private int y_coord;

    private static List<Critter> population = new ArrayList<Critter>();
    private static List<Critter> babies = new ArrayList<Critter>();
    private static boolean fightTime = false;	// determines whether or we're fighting
    private static int timestep = 0;	// keeps count of timesteps
    private boolean didMove = false;	// determines if the critter has moved this timestep

/////////////////////////////////////
    
    /* Gets the package name.  This assumes that Critter and its
     * subclasses are all in the same package. */
    private static String myPackage;

    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    private static Random rand = new Random();

    public static int getRandomInt(int max) {
        return rand.nextInt(max);
    }

    public static void setSeed(long new_seed) {
        rand = new Random(new_seed);
    }

    /**
     * create and initialize a Critter subclass.
     * critter_class_name must be the qualified name of a concrete
     * subclass of Critter, if not, an InvalidCritterException must be
     * thrown.
     *
     * @param critter_class_name
     * @throws InvalidCritterException
     */
    public static void createCritter(String critter_class_name)
            throws InvalidCritterException {
    	try {
    		// Check input
			Critter newCrit = (Critter) Class.forName(myPackage + "." + critter_class_name).newInstance();
			// Add to population
			Critter.population.add(newCrit);
			// Initialize newCrit with start energy
			newCrit.energy = Params.START_ENERGY;
			// Initialize newCrit in the critter world
			newCrit.x_coord = Critter.getRandomInt(Params.WORLD_WIDTH);
			newCrit.y_coord = Critter.getRandomInt(Params.WORLD_HEIGHT);
    		newCrit.didMove = false;
    		fightTime = false;
    	}
    	catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
    		throw new InvalidCritterException(critter_class_name);
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    /**
     * Gets a list of critters of a specific type.
     *
     * @param critter_class_name What kind of Critter is to be listed.
     *        Unqualified class name.
     * @return List of Critters.
     * @throws InvalidCritterException
     */
    public static List<Critter> getInstances(String critter_class_name)
            throws InvalidCritterException{
    	ArrayList<Critter> result = new ArrayList<Critter>();
    	if (critter_class_name == null) {
    		return population;
    	}
    	Critter new_crit = null;
		try {
			new_crit = (Critter) Class.forName(myPackage + "." + critter_class_name).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		for (int i = 0; i < population.size(); i++) {
			if (new_crit.getClass().isInstance(population.get(i))) {
				result.add(population.get(i));
			}
		}
		return result;
	}

    /**
     * Clear the world of all critters, dead and alive
     */
    public static void clearWorld() {
        population.clear();
    }

    /**
     * 1. increment timestep; timestep++;	<- not needed? but it's in FAQ for worldTimeStep()
	 * 2. doTimeSteps();
	 * 3. Do the fights. doEncounters();
	 * 4. updateRestEnergy();
	 * 5. Generate clover genClover();
	 * 6. Move babies to general population. 
     */
    public static void worldTimeStep() {
    	// increment timestep
        timestep++;
        
        // Do critter timesteps
        for (int i = 0; i < population.size(); i++) {
			population.get(i).didMove = false;
        	population.get(i).doTimeStep();
		}
        
        // Do the fights
        doEncounters();
        
        // Updates rest energy
        for (int i = 0; i < population.size(); i++) {
        	population.get(i).energy = population.get(i).energy - Params.REST_ENERGY_COST;
        }
        
        // Generate clover
	    try {
	    	genClover();
	    } catch (InvalidCritterException e) {
	    	e.printStackTrace();
	    }
        
        // Remove dead critters
        for (int i = 0; i < population.size(); i++) {
        	if (population.get(i).energy <= 0) {
				population.remove(i);
				i--;
			}
        }
        
        // Add babies to general population
        population.addAll(babies); 
        babies.clear();
        	
    }

    /**
     * Resolves the encounters of every critter that shares a location with another critter. 
     * No critter should share the same coordinate after this method.
     */
    private static void doEncounters() {
    	fightTime = true;
    	
    	HashMap<String, ArrayList<Critter>> critterWorld = new HashMap<String, ArrayList<Critter>>();
    	
    	// Makes a Map of coordinates inhabiting critters and all the alive critters that share that coordinate
    	for (int i = 0; i < population.size(); i++) {
    		String inhabitor = new String("" + population.get(i).x_coord + population.get(i).y_coord);
    		// Only includes critters that are alive
    		if(population.get(i).energy > 0) {
	    		// If there's already a critter at this space, add it
	    		if(critterWorld.containsKey(inhabitor)) { 
	    			critterWorld.get(inhabitor).add(population.get(i));
	    		}
	    		// Else if this is the first critter at the space, create a new ArrayList and add it
	    		else {
	    			critterWorld.put(inhabitor, new ArrayList<Critter>());
	    			critterWorld.get(inhabitor).add(population.get(i));
	    		}
	    	}
    	}
    	
    	// Iterate through the map
    	Iterator<String> itr = critterWorld.keySet().iterator();
    	while (itr.hasNext()) {
    		String key = itr.next();
    		ArrayList<Critter> presentCrits = critterWorld.get(key);
    		// If more than 1 critter is at the same space, they fight
    		while(presentCrits.size() > 1) {
    			Critter A = presentCrits.get(0);
    	    	Critter B = presentCrits.get(1);
    	    	
    	    	// The shared space of the 2 critters
    	    	int[] space = new int[]{B.x_coord, B.y_coord};
    	    	
    	    	boolean fightA = A.fight(B.toString());
    	    	boolean fightB = B.fight(A.toString());
    	    	
    	    	// Remove critters that run or are dead
    	    	Iterator<Critter> iter = presentCrits.iterator();
    	    	// Only check the first 2 critters
    	        for(int i = 0; i < 2; i++){
    	        	Critter X = iter.next();
    	        	if(X.getEnergy() <= 0)
    	        		iter.remove();
    	        	else if(!(X.x_coord == space[0] && X.y_coord == space[1]))
    	        		iter.remove();
    	        }
    	        
    	        // If they both stay, start fight
    	        if(presentCrits.contains(A) && presentCrits.contains(B)) {
    	        	int energyOfA = 0;
    	        	int energyOfB = 0;
    	        	if(fightA) {
    	        		energyOfA = getRandomInt(A.energy);
    	        	}
    	        	if(fightB) {
    	        		energyOfB = getRandomInt(B.energy);
    	        	}
    	        	// Critter B is the winner
    	        	if (energyOfA < energyOfB) {
    	        		B.energy = B.energy + A.energy/2;
    	        		A.energy = 0;
    	        		presentCrits.remove(A);
    	        	}
    	        	// Critter A is the winner
    	        	else {
    	        		A.energy = A.energy + B.energy/2;
    	        		B.energy = 0;
    	        		presentCrits.remove(B);
    	        	}
    	        }
    		}
    	}
    	
    	fightTime = false;
    }
    
    /**
     * Generates a number of clovers depending on REFRESH_CLOVER_COUNT
     */
    public static void genClover() throws InvalidCritterException {
    	for(int i = 0; i < Params.REFRESH_CLOVER_COUNT; i++)
    		createCritter("Clover");
    }

    public abstract void doTimeStep();

    public abstract boolean fight(String oponent);

    /* a one-character long string that visually depicts your critter
     * in the ASCII interface */
    public String toString() {
        return "";
    }

    protected int getEnergy() {
        return energy;
    }

    /**
	 * Moves critter in given direction represented by the value of the integer
	 * Moves 1 space
	 * 
	 * @param direction
	 */
	protected final void walk(int direction) {
		energy = energy - Params.WALK_ENERGY_COST;
		move(direction, 1);
	}

	/**
	 * Moves critter in given direction represented by the value of the integer
	 * Moves 2 spaces 
	 * 
	 * @param direction
	 */
	protected final void run(int direction) {
		energy = energy - Params.RUN_ENERGY_COST;
		move(direction, 2);
	}

	/**
	 * Move set number of spaces in a certain direction.
	 * Number of spaces to move is dependent on the value given by distance.
	 *
	 * @param direction to move in
	 * @param dist      distance to move for
	 */
	protected final void move(int direction, int dist) {
		if (direction == 0) {
			x_coord += dist;
		}
		if (direction == 1) {
			x_coord += dist;
			y_coord -= dist;
		}
		if (direction == 2) {
			y_coord -= dist;
		}
		if (direction == 3) {
			x_coord -= dist;
			y_coord -= dist;
		}
		if (direction == 4) {
			x_coord -= dist;
		}
		if (direction == 5) {
			x_coord -= dist;
			y_coord += dist;
		}
		if (direction == 6) {
			y_coord += dist;
		}
		if (direction == 7) {
			x_coord += dist;
			y_coord += dist;
		}
		
		if (x_coord > Params.WORLD_WIDTH - 1) {
			x_coord = x_coord - Params.WORLD_WIDTH - 1;
		}
		if (x_coord < 0) {
			x_coord = x_coord + Params.WORLD_WIDTH - 1;
		}
		if (y_coord > Params.WORLD_HEIGHT - 1) {
			y_coord = y_coord - Params.WORLD_HEIGHT - 1;
		}
		if (y_coord < 0) {
			y_coord = y_coord + Params.WORLD_HEIGHT - 1;
		}
	}

	/**
	 * Creates an offspring of the parent critter
	 * 
	 * @param offspring
	 * @param direction
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if (energy < Params.MIN_REPRODUCE_ENERGY) {
			return;
		}
		offspring.energy = energy / 2;
		energy = (int) Math.ceil(energy / 2);
		
		offspring.x_coord = x_coord;
		offspring.y_coord = y_coord;
		
		offspring.move(direction, 1);
		babies.add(offspring);
	}
	
	public int getX_coord() {
        return x_coord;
    }

    public int getY_coord() {
        return y_coord;
    }

    /**
     * The TestCritter class allows some critters to "cheat". If you
     * want to create tests of your Critter model, you can create
     * subclasses of this class and then use the setter functions
     * contained here.
     * <p>
     * NOTE: you must make sure that the setter functions work with
     * your implementation of Critter. That means, if you're recording
     * the positions of your critters using some sort of external grid
     * or some other data structure in addition to the x_coord and
     * y_coord functions, then you MUST update these setter functions
     * so that they correctly update your grid/data structure.
     */
    static abstract class TestCritter extends Critter {

        protected void setEnergy(int new_energy_value) {
            super.energy = new_energy_value;
        }

        protected void setX_coord(int new_x_coord) {
            super.x_coord = new_x_coord;
        }

        protected void setY_coord(int new_y_coord) {
            super.y_coord = new_y_coord;
        }

        /**
         * This method getPopulation has to be modified by you if you
         * are not using the population ArrayList that has been
         * provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.
         */
        protected static List<Critter> getPopulation() {
            return population;
        }

        /**
         * This method getBabies has to be modified by you if you are
         * not using the babies ArrayList that has been provided in
         * the starter code.  In any case, it has to be implemented
         * for grading tests to work.  Babies should be added to the
         * general population at either the beginning OR the end of
         * every timestep.
         */
        protected static List<Critter> getBabies() {
            return babies;
        }
    } // End of TestCritter
}