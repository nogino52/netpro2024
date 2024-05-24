package kadai;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

public class MulticastClient {
    public static void main(String[] args) {
        String multicastAddress = "224.0.0.1"; // マルチキャストアドレス
        int port = 4446; // ポート番号

        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket(port);
            InetAddress group = InetAddress.getByName(multicastAddress);

            NetworkInterface networkInterface = NetworkInterface.getByName("eth0");
            var inetSocketAddress = new InetSocketAddress(group, port);
            
            socket.joinGroup(inetSocketAddress, networkInterface);
            System.out.println("Joined multicast group.");

            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received message: " + received);

            socket.leaveGroup(inetSocketAddress, networkInterface);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
