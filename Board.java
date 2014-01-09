import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ants.Direction;
import ants.Tile;

/**
 * @author carmine carella
 *
 * Class Board 
 */
public class Board {
	
	//STARTING SQUARE - ANTHILL
	Square antHillSquare;
	
	//CURRENT SQUARE
	Square currentSquare;
	
	//VISITED SQUARE
	HashMap<String, Square> discoveredSquare;
	
	//SETS OF SQUARES THAT CONTAIN FOOD OR NOT THE ANT MEETS IN HIS PATH
	Set<Square> unsettledFoodSquare;
	Set<Square> unsettledPlainSquare;
	
	public Board() {	
		
		//anthill is the first square for an ant when it is created
		antHillSquare = new Square(0,0);
		
		//when the board is created for an ant the current square is the anthill
		currentSquare = antHillSquare;
				
		this.discoveredSquare = new HashMap<String, Square>();
		
		this.discoveredSquare.put(antHillSquare.squareKey(), antHillSquare);
		
		this.unsettledFoodSquare = new HashSet<Square>();
		this.unsettledPlainSquare = new HashSet<Square>();
	}
	
	
	/****** GET DIRECTION OF NEXT SQUARE TO MOVE ******/	
	public Direction getDirection(Square square) {
		
		Direction result = null;
		
		this.unsettledPlainSquare.remove(square);		
		
		if(Utils.isNorthSquare(square, currentSquare)){			
			result = Direction.NORTH;
		}  
		if(Utils.isEastSquare(square, currentSquare)){
			result = Direction.EAST;
		} 
		if(Utils.isSouthSquare(square, currentSquare)){
			result = Direction.SOUTH;
		}
		if(Utils.isWestSquare(square, currentSquare)){
			result = Direction.WEST;
		}
		
		System.out.println("Current square: " + currentSquare.squareKey());
		System.out.println("SquareToMove: " + square);
		System.out.println("Direction to move: " + result.toString());
		
		if(result != null)
			currentSquare = square;
		
		return result;
	}

	/****** EXPLORE SORROUNDING SQUARES ******/
	// Update the current square with the new amount of food	
	public void updateCurrentSquare(Integer food) {
		currentSquare.setFood(food);		
		if (food <= 0){
			this.unsettledFoodSquare.remove(currentSquare);								
		}
	}
	
	// Explore sorrounding square around the current one
	public void exploreSurroundings(Tile N, Tile E, Tile S, Tile W) {
		
		exploreSurrounding(Utils.getNorthSquare(currentSquare), N, currentSquare.getX(), currentSquare.getY()+1);
		
		exploreSurrounding(Utils.getEastSquare(currentSquare), E, currentSquare.getX()+1, currentSquare.getY());
		
		exploreSurrounding(Utils.getSouthSquare(currentSquare), S, currentSquare.getX(), currentSquare.getY()-1);
		
		exploreSurrounding(Utils.getWestSquare(currentSquare), W, currentSquare.getX()-1, currentSquare.getY());
				
	}
	private void exploreSurrounding(String key, Tile t, Integer x, Integer y) {
		//square already explored
		if (discoveredSquare.get(key) != null) {
			discoveredSquare.get(key).setFood(t.getAmountOfFood());
			if (discoveredSquare.get(key).getFood() <= 0){
				this.unsettledFoodSquare.remove(discoveredSquare.get(key));					
			}
		} 
		//new square for the ant
		else {			
			if (t.isTravelable()) {
				Square newSquare = new Square(x, y, t.getAmountOfFood());
				discoveredSquare.put(key, newSquare);
							
				if (newSquare.getFood() > 0){
					this.unsettledFoodSquare.add(newSquare);					
				}
				else{ 
					this.unsettledPlainSquare.add(newSquare);					
				}
			}
		}
	}

	/****** NEXT CELL TO MOVE, FOOD HAS PRIORITY ******/
	public Square getNextSquare() {
		Square closestFoodSquare = closestSquare(unsettledFoodSquare);
		Square closestPlainSquare = closestSquare(unsettledPlainSquare);		
		if (closestFoodSquare != null)			
			return closestFoodSquare;
		else
			return closestPlainSquare;
	}
		
	private Square closestSquare(Set<Square> squareSet) {		
		Square best = null;		
		Integer distance = Integer.MAX_VALUE;		
		if (!squareSet.isEmpty()) {
			for (Square s : squareSet) {
				Integer d = Utils.approximateDistance(currentSquare, discoveredSquare.get(s.squareKey()));				
				if (d < distance) {
					best = discoveredSquare.get(s.squareKey());
					distance = d;
				}
			}						
		}		
		return best;
	}
	
	/****** SHARING INFORMATION ABOUT ENVIROMENT KNOWLEDGE ******/
	public void updateBoard(List<Square> squares) {
		
		for(Square s: squares) {
			if(discoveredSquare.containsKey(s.squareKey())){				
				Integer mapFood = discoveredSquare.get(s.squareKey()).getFood();
				
				discoveredSquare.get(s.squareKey()).setFood(Math.min(s.getFood(), mapFood));				
				
				if (discoveredSquare.get(s.squareKey()).getFood() <= 0){
					this.unsettledFoodSquare.remove(s.squareKey());					
				}
			}
			else {
				
				Square newSquare = new Square(s.getX(),s.getY(),s.getFood());
				newSquare.setParent(s.getParent());
				
				discoveredSquare.put(newSquare.squareKey(), newSquare);				
				if (newSquare.getFood() > 0){
					this.unsettledFoodSquare.add(newSquare);					
				} else {
					this.unsettledPlainSquare.add(newSquare);									
				}				
			}
		}	
	}
	
	/****** UTILITY METHODS ******/
 	public Square getAntHillSquare() {
		return antHillSquare;
	}
	public Square getCurrentSquare() {
		return currentSquare;
	}
	public Boolean isAntHill(){
		if(this.currentSquare.equals(antHillSquare)){
			return true;		
		} else {
			return false;
		}	
	}
	
}