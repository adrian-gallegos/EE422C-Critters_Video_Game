/*
 * CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Adrian Gallegos
 * ag76424
 * 17360
 * Slip days used: 1
 * Spring 2022
 */

package assignment5;

import javafx.scene.paint.Color;

/*
 * This critter always walks but has a bad temper. It never fights 
 * but once in a while a critter will try and hurt it. If this
 * happens, this critter will choose to always run and fight every
 * other critter it encounters.
 */
public class Critter2 extends Critter{

	private static boolean fightMode = false;
	
	@Override
	public void doTimeStep() {
		if (getEnergy() < 100) {
			fightMode = true;
			run(getRandomInt(8));
		}
		else {
			walk(getRandomInt(8));
		}
		
	}

	@Override
	public boolean fight(String oponent) {
		if (fightMode == true) {
			return true;
		}
		return false;
	}
	
	@Override 
	public String toString() {
		return "2";
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.TRIANGLE;
	}

	@Override
	public Color viewOutlineColor() {
		return javafx.scene.paint.Color.YELLOW;
	}

	@Override
	public Color viewFillColor() {
		Color color = new Color(0, 0, 0, 1);
		color = Color.rgb(235, 229, 52, 1);
		return color;
	}
}
