import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static List<ClientProcessor> clients = new ArrayList<>();
    public static void main(String[] args) {

        System.out.println("Socket Server Application");

        try (ServerSocket server = new ServerSocket(9999)) {
            System.out.println("Server start success");
            while (true) {
                Socket clientSocket = server.accept();
                try {
                    clients.add(new ClientProcessor(clientSocket));
                    System.out.println("New client connected");
                }catch (IOException ex){
                    clientSocket.close();
                    System.out.println("client socket problem");
                }
            }
        } catch(IOException ex) {
            System.out.println("Server problem");
        }
    }
}
