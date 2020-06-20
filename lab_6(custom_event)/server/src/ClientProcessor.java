import javafx.util.Pair;

import java.io.*;
import java.net.Socket;

public class ClientProcessor extends Thread {
    private ObjectInputStream in; // поток чтения из сокета
    private ObjectOutputStream out; // поток записи в сокет
    private static final int BUFFER_SIZE = 1000;


    public ClientProcessor(Socket socket) throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object fileName = in.readObject();
                FileOutputStream fos = new FileOutputStream(fileName.toString());
                int bytesRead;
                byte [] buffer;
                do {
                    Object data = in.readObject();
                    buffer = (byte[])data;
                    bytesRead = buffer.length;
                    fos.write(buffer, 0, bytesRead);
                } while (bytesRead == BUFFER_SIZE);
                out.writeObject(new Pair<>(1, "File transfer success"));
                System.out.println("File transfer success");
                fos.close();
            }catch(IOException | ClassNotFoundException ex){
                try {
                    out.writeObject(new Pair<>(0, ex.getMessage()));
                } catch (IOException e) {
                    System.out.println("Client disconnected");
                    break;
                }
            }
        }
    }
}
