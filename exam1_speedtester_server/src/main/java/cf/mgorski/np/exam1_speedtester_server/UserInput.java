package cf.mgorski.np.exam1_speedtester_server;

import java.util.Scanner;

public class UserInput {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readString(String prompt, String default_) {
        System.out.print(prompt + " [" + default_ + "]: ");
        String in = scanner.nextLine();
        if (in.isEmpty()) {
            return default_;
        }
        return in;
    }

    public static int readInt(String prompt, int default_) {
        System.out.print(prompt + " [" + default_ + "]: ");
        String in = scanner.nextLine();
        if (in.isEmpty()) {
            return default_;
        }
        try {
            return Integer.parseInt(in);
        } catch (NumberFormatException e) {
            return readInt(prompt, default_);
        }
    }
}
