import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener implements Runnable {

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8181);
            while (true) {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(() -> {
                    System.out.println("Client connected: " + socket.getRemoteSocketAddress());
                    try {
                        InputStream inputStream = socket.getInputStream();
                        OutputStream outputStream = socket.getOutputStream();
                        inputStream.transferTo(outputStream);
                        socket.close();
                        System.out.println("Client disconnected: " + socket.getRemoteSocketAddress());
                    } catch (IOException e) {
                        System.out.println("An error occurred: " + e.getMessage());
                    }
                }, "Connection from: " + socket.getRemoteSocketAddress());
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
