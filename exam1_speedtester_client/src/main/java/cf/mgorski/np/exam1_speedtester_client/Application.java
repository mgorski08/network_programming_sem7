package cf.mgorski.np.exam1_speedtester_client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class Application {
    private static final Logger log = LogManager.getLogger(Application.class);
    private static final Set<InetSocketAddress> servers = new HashSet<>();

    private static void discover() {
        try {
            MulticastSocket multicastSocket = new MulticastSocket(65000);
            multicastSocket.joinGroup(new InetSocketAddress("224.0.0.10", 65000), null);
            multicastSocket.setSoTimeout(500);
            byte[] discover = "DISCOVERY".getBytes(StandardCharsets.UTF_8);
            multicastSocket.send(new DatagramPacket(discover, discover.length, new InetSocketAddress("224.0.0.10", 65000)));
            byte[] buffer = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            while (true) {
                multicastSocket.receive(datagramPacket);
                String received = new String(buffer, 0, datagramPacket.getLength(), StandardCharsets.UTF_8);
                if (received.startsWith("OFFER:")) {
                    int port = Integer.parseInt(received.substring(6));
                    servers.add(new InetSocketAddress(datagramPacket.getAddress(), port));
                    break;
                }
            }
        } catch (java.net.SocketTimeoutException ignored) {
        } catch (IOException e) {
            log.error(e);
        }
    }

    public static void main(String[] args) {
        log.info("Speedtester client");
        System.out.println("Please select server");
        InetSocketAddress server;
        while (true) {
            discover();
            try {
                System.out.println("");
                System.out.println("");
                System.out.println("-------------------");
                ArrayList<InetSocketAddress> serversList = new ArrayList<>(servers);
                for (InetSocketAddress s : serversList) {
                    System.out.print("[" + serversList.indexOf(s) + "] ");
                    System.out.println(s.toString());
                }
                System.out.println("-------------------");
                String aORs = UserInput.readString("[A]dd or [S]elect?", "S");
                if (aORs.equals("A") || aORs.equals("a")) {
                    String ip = UserInput.readString("Server IP", "127.0.0.1");
                    int port = UserInput.readInt("Server port", 7777);
                    servers.add(new InetSocketAddress(ip, port));
                } else if (aORs.equals("S") || aORs.equals("s")) {
                    int choice = UserInput.readInt("Select server", 0);
                    server = serversList.get(choice);
                    break;
                }
            } catch (Exception e) {
                log.error(e);
            }
        }
        log.info("Selected server: " + server.toString());
        int size = UserInput.readInt("Size of dataset in bytes", 50);
        String ngl = UserInput.readString("Do you want to use Nagle's algorithm? Y/N", "Y");
        boolean nagle = ngl.equals("Y") || ngl.equals("y");
        Thread udpSenderThread;
        Thread tcpSenderThread;
        try {
            udpSenderThread = new Thread(new UDPSender(server, size), "UDP");
            tcpSenderThread = new Thread(new TCPSender(server, size, nagle), "TCP");
            udpSenderThread.start();
            tcpSenderThread.start();
            System.in.read();
            udpSenderThread.interrupt();
            tcpSenderThread.interrupt();
            try {
                udpSenderThread.join();
                tcpSenderThread.join();
            } catch (InterruptedException ignored) {
            }

        } catch (IOException e) {
            log.error(e);
        }
    }
}
