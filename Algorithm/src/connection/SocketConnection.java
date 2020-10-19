package connection;

import main.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * to establish connection with rpi
 */
public class SocketConnection {
    // initialize socket and input output streams
    private Socket socket             = null;
    private InputStream  din     = null;
    private PrintStream dout    = null;
    private static SocketConnection cs = null;
    private static AtomicBoolean connected = new AtomicBoolean(false);
    private static final AtomicBoolean debug = new AtomicBoolean(false);

    private SocketConnection() {
    }

    public static SocketConnection getInstance() {
        if (cs == null) {
            cs = new SocketConnection();
            cs.connectToRPI();
        }
        return cs;
    }

    public static boolean checkConnection() {
        if (cs == null) {
            return false;
        }
        return true;
    }

    boolean connectToRPI() {
        boolean result = true;
        if (socket == null) {
            try {
                socket = new Socket(Constants.IP_ADDRESS, Constants.PORT);
                System.out.println("Connected to " + Constants.IP_ADDRESS + ":" + Constants.PORT);
                din  = socket.getInputStream();
                dout = new PrintStream(socket.getOutputStream());
                connected.set(true);

            }
            catch(UnknownHostException UHEx) {
                System.out.println("UnknownHostException in ConnectionSocket connectToRPI Function");
                result = false;
            }
            catch (IOException IOEx) {
                System.out.println("IOException in ConnectionSocket connectToRPI Function");
                result = false;
            }
        }
        return result;
    }

    public void sendMessage(String message) {
        try {
            dout.write(message.getBytes());
            dout.flush();
            System.out.println('"' + message + '"' + " sent successfully");
            if (debug.get()) {
                System.out.println('"' + message + '"' + " sent successfully");
            }
        }
        catch (IOException IOEx) {
            System.out.println("IOException in ConnectionSocket sendMessage Function");
        }
    }

    public String receiveMessage() {

        byte[] byteData = new byte[Constants.BUFFER_SIZE];
        try {
            int size = 0;
            while (din.available() == 0 && connected.get()) {
                try {
                    ConnectionManager.getInstance();
                }
                catch(Exception e) {
                    System.out.println("Error in receive message");
                }
            }
            din.read(byteData);

            // This is to get rid of junk bytes
            while (size < Constants.BUFFER_SIZE) {
                if (byteData[size] == 0) {
                    break;
                }
                size++;
            }
            String message = new String(byteData, 0, size, StandardCharsets.UTF_8);

            return message;
        }
        catch (IOException IOEx) {
            System.out.println("IOException in ConnectionSocket receiveMessage Function");
        }
        return "Error";
    }

    public void closeConnection() {
        if (socket != null) {
            try {
                dout.close();
                socket.close();
                din.close();
                dout = null;
                socket = null;
                din = null;
                connected.set(false);
                System.out.println("Successfully closed the ConnectionSocket.");
            }
            catch (IOException IOEx) {
                System.out.println("IOException in ConnectionSocket closeConnection Function");
            }
        }
    }

    public static void setDebugTrue() {
        debug.set(true);
    }

    public static boolean getDebug() {
        return debug.get();
    }
}