import java.io.Serializable;
import java.util.List;

/**
 * @author carmine carella
 * 
 * Wrapping class for serialization of the board's squares 
 *
 */
public class BoardSerializable implements Serializable{
	private static final long serialVersionUID = -1674769275576989500L;
	
	List<Square> squares;
	
	public BoardSerializable(List<Square> squares) {
		this.squares = squares;
	}

	public List<Square> getSquares() {
		return squares;
	}

	public void setSquares(List<Square> squares) {
		this.squares = squares;
	}

}