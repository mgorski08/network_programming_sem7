import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

class Application {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8181);
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            while(true) {
                outputStream.write(inputStream.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}