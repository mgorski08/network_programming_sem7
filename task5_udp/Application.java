import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;

class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "Usage: s|b|m|r\n" +
                        "s: Unicast sender\n" +
                        "b: Broadcast sender\n" +
                        "r: Unicast receiver\n" +
                        "m: Multicast receiver"
        );
        String choice;
        choice = scanner.next("[sbmr]");
        Runnable runnable;
        String name;
        switch (choice) {
            case "s": {
                System.out.println("Enter IP and port (e.g. 192.168.1.1 8080)");
                String ip = scanner.next();
                int port = scanner.nextInt();
                InetSocketAddress address = new InetSocketAddress(ip, port);
                runnable = new Sender(address);
                name = "Sender(" + address.toString() + ")";
                break;
            }
            case "b": {
                System.out.println("Enter port (e.g. 8080)");
                int port = scanner.nextInt();
                InetSocketAddress address = new InetSocketAddress("255.255.255.255", port);
                runnable = new Sender(address);
                name = "BroadcastSender(" + address.toString() + ")";
                break;
            }
            case "r": {
                System.out.println("Enter port (e.g. 8080)");
                int port = scanner.nextInt();
                runnable = new Receiver(port);
                name = "Receiver(" + port + ")";
                break;
            }
            case "m": {
                System.out.println("Enter IP and port (e.g. 226.1.1.1 8080)");
                String ip = scanner.next();
                int port = scanner.nextInt();
                InetSocketAddress address = new InetSocketAddress(ip, port);
                runnable = new MulticastReceiver(address);
                name = "MulticastReceiver(" + address.toString() + ")";
                break;
            }
            default:
                throw new IllegalStateException("Unknown option");
        }
        Thread thread = new Thread(runnable, name);
        thread.start();
    }
}