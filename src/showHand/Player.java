package showHand;

import java.util.*;

public class Player {
	private String name;
	private int account;
	private int winTimes;
	int inBet = 0;
	private boolean onBoard = true;
	public ShowHandCards playerCard = new ShowHandCards();
	
	public Player(String name){
		this.name = name;
		account = 1000;
		winTimes = 0;
	}
	
	public String getName(){
		return name;
	}
	
	
	public int getBet(){
		return inBet;
	}
	
	
	public void initPlayer(){
		inBet = 0;
		onBoard = true;
		playerCard.initSHCards();
	}
	
	public void win(int bouns){
		account += bouns;
		winTimes++;
	}
	
	public int bet(String answer){
		int bet=Integer.parseInt(answer);
		inBet += bet;
		account -= bet;
		return bet;
	}
	
	public boolean isFollow(int bet,String input){
		if(input.equalsIgnoreCase("Y")){
			inBet += bet;
			account -= bet;
			return true;
		}else{
			return false;
		}
	}
	
	
	public void unFollow(){
		onBoard = false;
	}
	
	public boolean isOn(){
		return onBoard;
	}
	
	
	public int getAccount(){
		return account;
	}
	
	public int getWinTime(){
		return winTimes;
	}
	
	public String showCards(String name){
		String res1;
		if(name.equals(this.name)){
			 res1=playerCard.toString();
		}else {
			 res1=playerCard.toOther();
		}
		return res1;
	}
	
	public boolean goon(){
		//System.out.println(name);
		return true;
	}
}
