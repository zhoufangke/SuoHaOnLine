package showHand;

import java.util.*;

public class Poker {
	private List<Card> poker= new ArrayList();
	
	public Poker(){

	}
	
	public void initPoker(){
		poker.clear();
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 13; j++){
				poker.add(new Card(i, j));
			}
		}
		Collections.shuffle(poker);
	}
	
	public Card deliverCard(){
		Card c = poker.get(0);
		poker.remove(0);
		return c;
	}
	
	public void show(){
		/*
		Iterator it = poker.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		*/
		for(Card c : poker){
			System.out.println(c);
		}
	}

}
