import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class MulticastReceiver implements Runnable {

    private final InetSocketAddress address;

    public MulticastReceiver(InetSocketAddress address) {
        this.address = address;
    }

    @Override
    public void run() {
        MulticastSocket multicastSocket = null;
        try {
            System.out.println(Thread.currentThread().getName());
            multicastSocket = new MulticastSocket(address.getPort());
            multicastSocket.joinGroup(address, null);
            while (true) {
                byte[] buffer = new byte[512];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(datagramPacket);
                System.out.println("Received(" + datagramPacket.getSocketAddress().toString() + "): " + new String(datagramPacket.getData(), StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            if(multicastSocket != null) multicastSocket.close();
        }
    }
}
