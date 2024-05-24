package kadai;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastServer {
    public static void main(String[] args) {
        String multicastAddress = "224.0.0.1"; // マルチキャストアドレス
        int port = 4446; // ポート番号
        String message = "Hello, Multicast!";

        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket();
            InetAddress group = InetAddress.getByName(multicastAddress);

            byte[] buf = message.getBytes();

            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, port);

            socket.send(packet);
            System.out.println("Message sent to multicast group.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
