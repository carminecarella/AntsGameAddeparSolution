import java.io.Serializable;

/**
 * @author carmine carella
 *
 * The board game can be modelled as a set of square with specific properties
 * An object of this class represents a tile as a square
 */
public class Square implements Serializable{

	private static final long serialVersionUID = -299056587438490771L;
	
	// current coordinates of a square as x-axis and y-axis 
	// with regards to the anthill coordinates
	private Integer x;
	private Integer y;

	// square in which the ant was located before moving on this square 
	private Square parent;

	// amount of food on the square
	private Integer food;

	// attributes used by astar algorithm
	private Integer f;
	private Integer g;
	private Integer h;
	
	//	Constructor coordinates
	public Square(Integer x, Integer y) {
		this.x = x;
		this.y = y;
		this.food = 0;

		this.f = 0;
		this.g = 0;
		this.h = 0;
		this.parent = null;
	}
	
	//Constructor coordinates and food
	public Square(Integer x, Integer y, Integer food) {
		this.x = x;
		this.y = y;
		this.food = food;

		this.f = 0;
		this.g = 0;
		this.h = 0;
		this.parent = null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Square other = (Square) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}
	
	public Square clone(){
		Square s = new Square(this.x,this.y,this.food);
		s.setG(this.g);
		s.setF(this.f);
		s.setH(this.h);
		//s.setParent(this.parent.clone());
		return s;
	}
	
	//used as key for the square in 
	//the hashmap discoveredSquare of an ant
	public String squareKey() {
		return this.x + "" + this.y;
	}
	
	//getter and setter methods
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	public Square getParent() {
		return parent;
	}
	public void setParent(Square parent) {
		this.parent = parent;
	}
	public Integer getFood() {
		return food;
	}
	public void setFood(Integer food) {
		this.food = food;
	}
	public Integer getF() {
		return f;
	}
	public void setF(Integer f) {
		this.f = f;
	}
	public Integer getG() {
		return g;
	}
	public void setG(Integer g) {
		this.g = g;
	}
	public Integer getH() {
		return h;
	}
	public void setH(Integer h) {
		this.h = h;
	}

	

	@Override
	public String toString() {
		return "Square [x=" + x + ", y=" + y + ", parent=" + parent + ", food="
				+ food + ", f=" + f + ", g=" + g + ", h=" + h + "]";
	}
}
