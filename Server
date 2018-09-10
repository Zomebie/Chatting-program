

import java.util.*;
import java.io.*;
import java.net.*;

public class Multi {
 HashMap<String, DataOutputStream> client;

 Multi() {
  client = new HashMap();
  Collections.synchronizedMap(client); // wrapping for concurrent access to the map
 }

 public static void main(String[] args) {
  new Multi().start();
 } // main

 public void start() {
  ServerSocket serverSocket = null;
  Socket socket = null;

  try {
   serverSocket = new ServerSocket(10001);
   System.out.println("서버가 시작되었습니다.");

   while (true) {
    socket = serverSocket.accept();  // 클라이언트소켓를 서버소켓과 연결한 후,
    System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]" + "에서 접속하였습니다.");
    ServerReceiver thread = new ServerReceiver(socket);    // 연결된 클라이언트와 1:1 매칭
    thread.start();
   }

  } catch (IOException e) {

   e.printStackTrace();
  }

 }  // main. start()

 class ServerReceiver extends Thread {
  Socket socket;
  DataInputStream in;
  DataOutputStream out;

  public ServerReceiver(Socket socket) {
   this.socket = socket;

   try {
    in = new DataInputStream(socket.getInputStream());
    out = new DataOutputStream(socket.getOutputStream());
   } catch (IOException e) {
    e.printStackTrace();
   }
  }

  public void run() {
   String name = "";

   try {
    name = in.readUTF();
    sendToAll("#" + name + "님이 들어오셨습니다.");
    // 처음 한 명이 채팅에 접속했을 땐 client map에 아무도 없기 때문에 안뜸
    client.put(name, out);
    System.out.println("현재 서버접속사 수는 " + client.size() + "입니다.");

    while (in != null) {
     sendToAll(in.readUTF());
    }

   } catch (IOException e) {
    // 클라이언트가 종료되어 입력스트림(in)이 null이 되면 while문을 빠져 나와 client 목록에서 해당 클라이언트를 제거
    //e.printStackTrace();
   } finally {
    sendToAll("#" + name + "님이 나가셨습니다."); // 접속이 끊긴 본인 창에는 안뜸 in=null이기 때문에
    client.remove(name);
    System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]" + "에서 접속을 종료하였습니다.");
    System.out.println("현재 서버접속자 수는" + client.size() + "입니다.");

   } // try block

  } // run()

 } // inner class serverReceiver

 public void sendToAll(String msg) {

  Iterator it = client.keySet().iterator();

  while (it.hasNext()) {

   try {
    DataOutputStream out = client.get(it.next());
    out.writeUTF(msg);
   } catch (IOException e) {

    e.printStackTrace();
   }

  } // while

 } // sendToAll()
} // Multi class
