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
 * This critter is extremely powerful but its species is very sparse. This is
 * due to its recklessness of always being on the run. To make matters worse,
 * this critter becomes more active and bold as it's on the brink of death.
 * That is, this critter only fights when it's close to death.
 */
public class Critter1 extends Critter{

	@Override
	public void doTimeStep() {
		int dir = getRandomInt(8);
		if (look(dir, true) == null)
			walk(dir);
		if (getEnergy() < 20) {
			for (int i = 0; i < 4; i++) {
				run(getRandomInt(8));
			}
		}
		else {
			run(getRandomInt(8));
		}
		
	}

	@Override
	public boolean fight(String oponent) {
		if (getEnergy() < 20) {
			return true;
		}
		return false;
	}
	
	@Override 
	public String toString() {
		return "1";
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.SQUARE;
	}

	@Override
	public Color viewOutlineColor() {
		return javafx.scene.paint.Color.PINK;
	}

	@Override
	public Color viewFillColor() {
		Color color = new Color(0, 0, 0, 1);
		color = Color.rgb(242, 153, 235, 1);
		return color;
	}


}
