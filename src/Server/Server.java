package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import showHand.Card;
import showHand.Player;
import showHand.Poker;

public class Server{
	 List<ClientThread> clients = new ArrayList<ClientThread>();
	 ArrayList<Player> players = new ArrayList<>();
	 private int currentPage = 0;
	 Poker poker = new Poker();
	    private int playerNum = 1;
	    int lastMax = 0;
	    private String totoalStr = "";
	    public static void main(String[] args) {

	        new Server().start();

	    }
		private void start() {
			// TODO Auto-generated method stub

		            boolean iConnect = false;

		            ServerSocket ss;
					try {
						ss = new ServerSocket(1320);
					

		            iConnect = true;

		            totoalStr = packMsg(totoalStr, "服务器已经启动....\r\n");

		            sendClientMsg(totoalStr);

		 

		            while (iConnect) {

		 

		                Socket s = ss.accept();

		                totoalStr = packMsg(totoalStr, "玩家 " + playerNum + " 绑定服务器端口成功！\r\n");

		                sendClientMsg(totoalStr);

		 

		                ClientThread currentClient = new ClientThread(s, "玩家 " + playerNum);//创建线程引用

		                clients.add(currentClient);//把当前客户端加入集合
		                Player player = new Player("玩家"+playerNum);
		                players.add(player);

		 

		                totoalStr = packMsg(totoalStr, "玩家 " + playerNum + " 加入....\r\n");

		                sendClientMsg(totoalStr);

		 

		                if (playerNum == 2) {

		                    totoalStr = packMsg(totoalStr, "已经到达玩家上限，不再允许玩家进入游戏！\r\n");

		                    sendClientMsg(totoalStr);

		                    break;

		                }

		                ++playerNum;

		            }

		            totoalStr = packMsg(totoalStr, "游戏开始，庄家开始洗牌....\r\n");
		        	
		            sendClientMsg(totoalStr);
		          int  bet = 0;
		    		int clip =0;
		        	poker.initPoker();
		        	for(Player p : players){
		    			p.initPlayer();
		    		}
		    		for(int i = 0; i < 1; i++){
		    			for(Player p : players){
		    				p.playerCard.getCards(poker.deliverCard());
		    			}
		    		}
		            //五轮牌
		            for(int a= 1;a<5;a++){
		            	
		            	int quiteNum = 0;
		    			lastMax = Deliver(players, lastMax);
		    			if(players.get(lastMax).goon())
		    			{	
		    				 String originalStr = new String(totoalStr);
		    				 	String sname1=players.get(lastMax).getName();
		    				 	char sname2=sname1.charAt(2);
		    				 	String sname3=String.valueOf(sname2);
		    				 	int snamenum=Integer.parseInt(sname3);
		    				 	System.out.println(snamenum);
				                ClientThread c = clients.get(snamenum-1);

				                originalStr = packMsg(originalStr, sname1 + "所看见的牌\n");
				                c.sendMsg(originalStr);
				                
				                
				                originalStr = packMsg(originalStr, showCards(players.get(lastMax)));
				                c.sendMsg(originalStr);
				                
				                originalStr = packMsg(originalStr, "请输入你的赌注:");
				                c.sendMsg(originalStr);
				                String answer = c.recMsg();
				                
				                originalStr = packMsg(originalStr, "\n请等待其他玩家\n...\n...");
				                c.sendMsg(originalStr);
				                bet = players.get(lastMax).bet(answer);
								clip += bet;
								
								
								for(int j = 1; j < players.size(); j++){
									String originalStr1 = new String(totoalStr);
									int follower = (j + lastMax) % players.size();
									ClientThread c1 = clients.get(follower);
									 originalStr1 = packMsg(originalStr1,players.get(follower).getName() + "所看见的牌");
									 c1.sendMsg(originalStr1);
									 
									 originalStr1 = packMsg(originalStr1, showCards(players.get(follower)));
									 c1.sendMsg(originalStr1);
									 
									 originalStr1 = packMsg(originalStr1, "这一轮赌注为"+bet+"是否跟注:(Y/N)");
						                c1.sendMsg(originalStr1);
						                String answer1 = c1.recMsg();
						                if(answer1.equals("Y")){
						                	 originalStr1 = packMsg(originalStr1, "你已跟注\n");
								                c1.sendMsg(originalStr1);
						                }
						                
									if(players.get(follower).isOn() && players.get(follower).isFollow(bet,answer1)){
										clip += bet;
									}else {
										players.get(follower).unFollow();
										quiteNum ++;
									}
								}
		          
		    			}
		    			else{}
		    			}
		            Player winner = this.finndWinner(players);
		    		winner.win(clip);
		    		String reswin="";
		    		reswin = packMsg(reswin, "游戏结束....\r\n");
		    		sendClientMsg(reswin);
		    		for(Player p : players){
		    			reswin = packMsg(reswin, p.getName() + "余额为： " + p.getAccount() + "; 胜利次数" + p.getWinTime()+"\n");
		    			
		    		}
		    		sendClientMsg(reswin);
					}catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}

		public Player finndWinner(ArrayList<Player> players){
		ArrayList<Player> playerList = new ArrayList<>();
		for(Player p : players){
			if(p.isOn()){
				playerList.add(p);
			}
		}
		if(playerList.size() == 1){
			return playerList.get(0);
		}else {
			Player winner = playerList.get(0);
			for(Player p : playerList){
				//System.out.println(p.playerCard.shCards.size());
				if(winner.playerCard.compareTo(p.playerCard) < 0){
					winner = p;
				}
			}
			return winner;
		}
	}
		public String showCards(Player player){
			String res = null;
		for(Player p : players){
			String sname=p.getName();
			String cads=p.showCards(player.getName());
			 res=res+sname+cads+"\n";
		}
		return res;
		
	}
		public int Deliver(List<Player> playerList, int index) {
			// TODO Auto-generated method stub
				int j = 0;
				int indexMax = 0;
				Card maxCard = new Card(0, 0);
				Card card;
				for(int i = index; i < index + playerList.size(); i++){
					j = i % playerList.size();
					if(playerList.get(j).isOn()){
						card = poker.deliverCard();
						playerList.get(i % playerList.size()).playerCard.getCards(card);
						if(card.compareTo(maxCard) > 0){
							maxCard = card;
							indexMax = j;
						}
					}
					
					//System.out.println(maxCard);
				}
				return indexMax;
		}
		private void sendClientMsg(String str) {

		        for (int i = 0; i < clients.size(); i++) {

		            ClientThread c = clients.get(i);

		            c.sendMsg(str);

		        }

		    }
	    private String packMsg(String originStr, String str) {

	        System.out.print(str);

	        originStr += str;

	        return originStr;

	    }

}





