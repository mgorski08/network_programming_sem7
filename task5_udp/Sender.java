import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Sender implements Runnable {

    private final Scanner scanner = new Scanner(System.in);
    private final SocketAddress address;

    public Sender(SocketAddress address) {
        this.address = address;
    }

    @Override
    public void run() {
        DatagramSocket datagramSocket = null;
        try {
            System.out.println(Thread.currentThread().getName());
            datagramSocket = new DatagramSocket();
            while (true) {
                byte[] bytes = scanner.nextLine().getBytes(StandardCharsets.UTF_8);
                DatagramPacket datagramPacket = new DatagramPacket(
                        bytes,
                        bytes.length,
                        address);
                datagramSocket.send(datagramPacket);
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            if (datagramSocket != null) datagramSocket.close();
        }
    }
}
