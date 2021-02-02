import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class Receiver implements Runnable {

    private final int port;

    public Receiver(int port){
        this.port = port;
    }

    @Override
    public void run() {
        DatagramSocket datagramSocket = null;
        try {
            System.out.println(Thread.currentThread().getName());
            datagramSocket = new DatagramSocket(port);
            while (true) {
                byte[] buffer = new byte[512];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                System.out.println("Received(" + datagramPacket.getSocketAddress().toString() + "): " + new String(datagramPacket.getData(), StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            if (datagramSocket != null) datagramSocket.close();
        }
    }
}
