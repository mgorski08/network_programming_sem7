import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

class Application {
    public static void main(String[] args) {
        Thread listener = new Thread(new Listener(), "Listener");
        listener.start();
    }
}