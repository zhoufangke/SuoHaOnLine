package showHand;

import java.util.*;

public class ShowHandCards implements Comparable{
	
	private ArrayList<Card> shCards  = new ArrayList();
	//private String[] types = {"Straight Flush", "Four of a kind", "FullHouse", "Flush", "Straight", "Three of a kind", "Two Pairs", "One Pairs", "Zilch"};
	private String[] types = {"Zilch", "One Pairs", "Two Pairs", "Three of a kind", "Straight", "Flush", "FullHouse", "Four of a kind", "Straight Flush"};
	//同花顺>四条>满堂红>同花>顺子>三条>二对>单对>散牌
	//定义梭哈牌的种类，并针对每个各类来进行赋值，然后根据值来进行比较。
	//其中散牌赋值为0，单对为1，二对为2，三条为4，顺子为8，同花为16，满堂红为32，四条64，同花顺为128。
	private int typeIndex;
	//定义最大的牌，当种类相同的时候拿最大的牌来进行比较。
	private Card maxCard;
	
	public void initSHCards(){
		shCards.clear();
		typeIndex = 0;
		maxCard = null;
	}
	
	public int getTypeIndex(){
		this.getType();
		return typeIndex;
	}
	
	public Card getMaxCard(){
		this.getType();
		return maxCard;
	}
	
	public void getCards(Card c){
		shCards.add(c);
	}
	
	public String toString(){
		String str = new String("");
		for(Card c : shCards){
			str += c.toString();
			str += "\t";
		}
		return str;
	}
	
	public String toOther(){
		String str = "底牌" + "\t";
		for(int i = 1; i < shCards.size(); i++){
			str += shCards.get(i).toString() + "\t";
		}
		return str;
	}
	
	public boolean isFull(){
		return shCards.size() == 5;
	}
	
	//判断是否为同花顺，如果是即记录最大的牌，返回true；如果不是则返回false。
	public boolean isStrFlush(){
		if(this.isStraight() && this.isFlush()){
			return true;
		}
		return false;
	}
	
	
	//判断是否为四条，并记录最大的牌，返回true；如果不是则返回false。
	public boolean isFour(){
		int num = 4; //相同牌大小的个数
		ArrayList<Card> cardList = (ArrayList) shCards.clone();
		Collections.sort(cardList);
		boolean flag = true;
		for(int i = 0; i < cardList.size() - num + 1; i++){
			int value = cardList.get(i).getValue();
			for(int j = 0; j < num; j++){
				flag = true;
				if(value != cardList.get(i + j).getValue()){
					flag = false;
				}
				
			}
			if(flag){
				maxCard = cardList.get(i + num - 1);
				return true;
				
			}
		}
		return flag;
	}
	
	//判断是否为满堂红（一个3个，一个2个），如果是满堂红则把type设定为32，并记录3个中最大的一个为最大的牌，如果不是则返回false
	//暂时只能针对5张牌来写。
	//满堂红有两种可能，一种是前面2个为对，后面3个为3个。另外一种是前面3个为3个，后面两个为一对。分别采用isPair以及isThree来判断即可。
	//暂时还没有想到好的办法
	
	public boolean isFullHouse(){
		ArrayList<Card> cardList = (ArrayList) shCards.clone();
		Collections.sort(cardList);
		if(cardList.get(0).getValue() == cardList.get(1).getValue() && cardList.get(3).getValue() == cardList.get(4).getValue()){
			if(cardList.get(2).getValue() == cardList.get(3).getValue()){
				maxCard = cardList.get(4);	
				return true;
			}else if(cardList.get(1).getValue() == cardList.get(2).getValue()){
				maxCard = cardList.get(2);
				return true;
			}
		}
		return false;
		
	}
	
	
	//判断是否为同花，如果为同花则把type设定为16，并记录最大的牌，返回true；如果不是则返回false。
	public boolean isFlush(){
		ArrayList<Card> cardList = (ArrayList) shCards.clone();
		Collections.sort(cardList);
		int cardType = cardList.get(0).getType();
		for(int i = 0; i < cardList.size(); i++){
			if(cardType != cardList.get(i).getType()){
				return false;
			}
		}
		//type = 16;
		maxCard = cardList.get(cardList.size() - 1);
		return true;
	}
	
	//判断是否为顺子，如果是则把type设定为8，并记录最大的牌，返回true；如果不是则返回false。
	public boolean isStraight(){
		ArrayList<Card> cardList = (ArrayList) shCards.clone();
		Collections.sort(cardList);
		for(int i = 0; i < cardList.size() - 1; i++){
			if(cardList.get(i).getValue() - cardList.get(i + 1).getValue() != -1){
				return false;
			}
		}
		//type = 8;
		maxCard = cardList.get(cardList.size() - 1);
		return true;
	}
	
	//判断是否为三条，如果是则把type设定为4，并记录最大的牌，返回true，如果不是则返回false。判定方法与isFour类似。
	public boolean isThree(){
		int num = 3; //相同牌大小的个数
		ArrayList<Card> cardList = (ArrayList) shCards.clone();
		Collections.sort(cardList);
		boolean flag = true;
		for(int i = 0; i < cardList.size() - num + 1; i++){
			int value = cardList.get(i).getValue();
			for(int j = 0; j < num; j++){
				flag = true;
				if(value != cardList.get(i + j).getValue()){
					flag = false;
				}
				
			}
			if(flag){
				//type = 4;
				maxCard = cardList.get(i + num - 1);
				return true;
				
			}
		}
		return flag;
	}
	
	//判断是否为双对，如果是则把type设定为1，并记录最大的牌，返回true，如果不是则返回false。判定方法与isFour类似。
	//将整个数组拆成为两个数组，如果两个均为pair则为twoPair，并记录后面一个pair的maxCard。
	//暂未想到办法。
	
	public boolean isTwoPair(){
		ArrayList<Card> cardList = (ArrayList) shCards.clone();
		Collections.sort(cardList);
		if(cardList.get(0).getValue() == cardList.get(1).getValue()){
			if((cardList.get(2).getValue() == cardList.get(3).getValue()) || (cardList.get(3).getValue() == cardList.get(4).getValue())){
				maxCard = cardList.get(3);
				return true;
			}
		}else if((cardList.get(1).getValue() == cardList.get(2).getValue()) && (cardList.get(3).getValue() == cardList.get(4).getValue())){
				maxCard = cardList.get(3);
				return true;
		}
		return false;
	}
	
	
	//判断是否为对，如果是则把type设定为1，并记录最大的牌，返回true，如果不是则返回false。判定方法与isFour类似。
	public boolean isPair(){
		int num = 2; //相同牌大小的个数
		ArrayList<Card> cardList = (ArrayList) shCards.clone();
		Collections.sort(cardList);
		boolean flag = true;
		for(int i = 0; i < cardList.size() - num + 1; i++){
			int value = cardList.get(i).getValue();
			for(int j = 0; j < num; j++){
				flag = true;
				if(value != cardList.get(i + j).getValue()){
					flag = false;
				}
				
			}
			if(flag){
				//type = 1;
				maxCard = cardList.get(i + num - 1);
				return true;
				
			}
		}
		return flag;
	}
	
	public void zilch(){
		ArrayList<Card> cardList = (ArrayList) shCards.clone();
		Collections.sort(cardList);
		maxCard = cardList.get(cardList.size() - 1); 
	}
	
	//返回梭哈牌的类型。通过typeIndex及相应的数组来设定。
	public String getType(){
		if(this.isStrFlush()){
			typeIndex = 8;
			return types[typeIndex];
		}else if(this.isFour()){
			typeIndex = 7;
			return types[typeIndex];
		}else if(this.isFullHouse()){
			typeIndex = 6;
			return types[typeIndex];
		}else if(this.isFlush()){
			typeIndex = 5;
			return types[typeIndex];
		}else if(this.isStraight()){
			typeIndex = 4;
			return types[typeIndex];
		}else if(this.isThree()){
			typeIndex = 3;
			return types[typeIndex];
		}else if(this.isTwoPair()){
			typeIndex = 2;
			return types[typeIndex];
		}else if(this.isPair()){
			typeIndex = 1;
			return types[typeIndex];
		}else{
			this.zilch();
			typeIndex = 0;
			return types[typeIndex];
		}	
	}
	
	public int compareTo(Object obj){
		ShowHandCards s = (ShowHandCards) obj;
		if(this.getTypeIndex() > s.getTypeIndex()){
			return 1;
		}else if(this.getTypeIndex() < s.getTypeIndex()){
			return -1;
		}else {
			return this.getMaxCard().compareTo(s.getMaxCard());
		}
	}
}
