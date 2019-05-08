package showHand;

public class Card implements Comparable{
	private String[] types = {"方块","梅花","红桃","黑桃"};
	private String[] values = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
	private int typeIndex;
	private int valueIndex;
	
	public Card(int typeIndex, int valueIndex){
		this.typeIndex = typeIndex;
		this.valueIndex = valueIndex;
	}
	
	public int getType(){
		return typeIndex;
	}
	
	public int getValue(){
		return valueIndex;
	}
	
	public int compareTo(Object obj){
		Card c = (Card) obj;
		//return valueIndex > c.getValue() ? 1 : valueIndex < c.getValue() ? -1 : 0;
		if(valueIndex > c.getValue()){
			return 1;
		}else if(valueIndex < c.getValue()){
			return -1;
		}else if(typeIndex > c.getType()){
			return 1;
		}else if(typeIndex < c.getType()){
			return -1;
		}else {
			return 0;
		}
	}
	
	public String toString(){
		return types[typeIndex] + values[valueIndex];
	}
}
