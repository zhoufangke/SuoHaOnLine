package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import showHand.Player;

class ClientThread implements Runnable {

	 

    private Socket s;

    private DataInputStream dis;

    private DataOutputStream dos;

    private String str;

    private boolean iConnect = false;

    private Player player;



    ClientThread(Socket s, String name) {

        this.s = s;

        iConnect = true;

        this.player = new Player(name);

    }



    public String recMsg() {

        try {

            dis = new DataInputStream(s.getInputStream());

            str = dis.readUTF();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return str;

    }



    public void run() {

//        System.out.println("run方法启动了！");

//        try {

//

//            while (iConnect) {

//                dis = new DataInputStream(s.getInputStream());

//                str = dis.readUTF();

//                System.out.println(str);

//                for (int i = 0; i < clients.size(); i++) {

//                    System.out.println("转发消息中..." + i);

//                    ClientThread c = clients.get(i);

//                    c.sendMsg(str);

//                }

//            }

//        } catch (IOException e) {

//            e.printStackTrace();

//        }



    }



    /**

     * 将送至服务器的消息发送给每个连接到的客户端

     */

    public void sendMsg(String str) {

        try {

            dos = new DataOutputStream(this.s.getOutputStream());

            dos.writeUTF(str);

        } catch (IOException e) {

            e.printStackTrace();

        }



    }




    public Socket getS() {

        return s;

    }



    public void setS(Socket s) {

        this.s = s;

    }



    public DataInputStream getDis() {

        return dis;

    }



    public void setDis(DataInputStream dis) {

        this.dis = dis;

    }



    public DataOutputStream getDos() {

        return dos;

    }



    public void setDos(DataOutputStream dos) {

        this.dos = dos;

    }



    public String getStr() {

        return str;

    }



    public void setStr(String str) {

        this.str = str;

    }



    public boolean isiConnect() {

        return iConnect;

    }



    public void setiConnect(boolean iConnect) {

        this.iConnect = iConnect;

    }



    public Player getPlayer() {

        return player;

    }



    public void setPlayer(Player player) {

        this.player = player;

    }

}
