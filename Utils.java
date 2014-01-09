/**
 * @author carmine carella
 * 
 * Utility class for managing conversion of coordinates x,y into String key 
 * or Direction N,E,S,W
 *
 */
public final class Utils {
	
	//North square coordinates are (x, y+1) regards to current coordinates' square
	public static String getNorthSquare(Square current) {
		String key;
		
		key = current.getX() + "" + (current.getY() + 1); 
		
		return key;
	}
	
	//East square coordinates are (x+1, y) regards to current coordinates' square
	public static String getEastSquare(Square current) {
		String key;
		
		key = (current.getX() + 1) + "" + current.getY(); 
		
		return key;
	}
	
	//South square coordinates are (x, y-1) regards to current coordinates' square
	public static String getSouthSquare(Square current) {
		String key;
		
		key = current.getX() + "" + (current.getY() - 1); 
		
		return key;
	}
	
	//West square coordinates are (x-1, y) regards to current coordinates' square
	public static String getWestSquare(Square current) {
		String key;
		
		key = (current.getX()-1) + "" + current.getY(); 
		
		return key;
	}
	
	public static Integer approximateDistance(Square s1, Square s2) {
	  if (s1 == null || s2 == null)
	    return Integer.MAX_VALUE;
	  return Math.abs(s1.getX() - s2.getX()) + Math.abs(s1.getY() - s2.getY());
    }
	
	//A square s is in north direction regards to current square if current.y + 1 = s.y
	public static boolean isNorthSquare(Square square, Square current) {
		
		boolean result = false;
		
		if(	current.getX().intValue() == square.getX().intValue() &&
			(current.getY().intValue()+1) == square.getY().intValue()){
			result = true;
		}		
		
		return result;
	}
	
	//A square s is in east direction regards to current square if current.x + 1 = s.x
	public static boolean isEastSquare(Square square, Square current) {
		
		boolean result = false;
		
		if(	current.getY().intValue() == square.getY().intValue() &&
			(current.getX().intValue()+1) == square.getX()){
			result = true;
		}		
		
		return result;
	}
	
	//A square s is in south direction regards to current square if current.y - 1 = s.y
	public static boolean isSouthSquare(Square square, Square current) {
		
		boolean result = false;
		
		if(	current.getX().intValue() == square.getX().intValue() &&
			(current.getY().intValue()-1) == square.getY().intValue()){
			result = true;
		}		
		
		return result;
	}
	
	//A square s is in west direction regards to current square if current.x - 1 = s.x
	public static boolean isWestSquare(Square square, Square current) {
		
		boolean result = false;
		
		if(	current.getY().intValue() == square.getY().intValue() &&
			(current.getX().intValue()-1) == square.getX().intValue()){
			result = true;
		}		
		
		return result;
	}
	
}