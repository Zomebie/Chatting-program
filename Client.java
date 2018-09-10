import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiClient {

 public static void main(String args[]) {
  Scanner sc = new Scanner(System.in);
  String serverIp = "127.0.0.1";
  String id;
  try {

   Socket socket = new Socket(serverIp, 10001);
   System.out.println("서버에 연결되었습니다.");
   System.out.print("ID를 입력하세요 :");
   id = sc.next();

   ClientSender cs = new ClientSender(socket, id);
   // Thread cs = new Thread(new ClientSender(socket, args[0]));
   Thread r = new Thread(new ClientReceiver(socket));

   cs.start();
   r.start();

  } catch (ConnectException e) {

   e.printStackTrace();
  } catch (Exception e) {

   e.printStackTrace();

  } // try

 } // main

} // MultiClient class

class ClientSender extends Thread {
 Socket socket;
 DataOutputStream out;
 String name;

 ClientSender(Socket socket, String name) {
  this.socket = socket;
  this.name = name;
  try {
   out = new DataOutputStream(socket.getOutputStream());

  } catch (IOException e) {

   e.printStackTrace();
  }

 } // ClientSender constructor

 public void run() {
  Scanner sc = new Scanner(System.in);

  try {
   if (out != null)
    out.writeUTF(name);
   while (out != null)
    out.writeUTF("[" + name + "]" + sc.next());

  } catch (IOException e) {

   e.printStackTrace();
  }
 } // run()
} // Clientsender class

 

class ClientReceiver extends Thread {
 Socket socket;
 DataInputStream in;

 ClientReceiver(Socket socket) {
  this.socket = socket;

  try {
   in = new DataInputStream(socket.getInputStream());
  } catch (IOException e) {

   e.printStackTrace();
  }

 }

 public void run() {
  while (in != null) {
   try {
    System.out.println(in.readUTF());
   } catch (IOException e) {

    e.printStackTrace();
   }
  }
 } // run()
} // ClientReceiver class
