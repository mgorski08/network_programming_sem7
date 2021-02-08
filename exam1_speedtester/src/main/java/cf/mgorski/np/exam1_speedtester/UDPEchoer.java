package cf.mgorski.np.exam1_speedtester;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPEchoer implements Runnable {

    private final DatagramSocket datagramSocket;

    public UDPEchoer(int port) throws SocketException {
        datagramSocket = new DatagramSocket(port);
    }

    @Override
    public void run() {
        byte[] buffer = new byte[4096];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        while (true) {
            try {
                datagramSocket.receive(datagramPacket);
                datagramSocket.send(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
