import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClientProcessor extends Thread {
    private ObjectInputStream in; // поток чтения из сокета
    private ObjectOutputStream out; // поток записи в сокет
    private String clientCurrentDirectory = System.getProperty("user.dir");

    public ClientProcessor(Socket socket) throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Pair<String, String> command= (Pair<String, String>) in.readObject();
                if(command.getKey().equals("ls")){
                    System.out.println(">  ls");
                } else if (command.getKey().equals("cd")){
                    System.out.println(">  cd " + command.getValue());
                    if(command.getValue().equals("..")){
                        clientCurrentDirectory = clientCurrentDirectory.substring(0, clientCurrentDirectory.lastIndexOf("\\"));
                    } else if (command.getValue().endsWith(".exe")){
                        Runtime.getRuntime().exec(clientCurrentDirectory + "\\" + command.getValue());
                    } else {
                        clientCurrentDirectory += "\\" + command.getValue();
                    }
                }
                File[] files = new File(clientCurrentDirectory).listFiles();
                List<String> fileNames = Arrays.stream(files)
                        .filter(file -> file.isDirectory() || file.getName().endsWith(".exe"))
                        .map(file ->  file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("\\")+1))
                        .collect(Collectors.toList());
                if(clientCurrentDirectory.length() > System.getProperty("user.dir").length()) {
                    fileNames.add(0, "..");
                }
                out.writeObject(new ArrayList<>(fileNames));
                out.flush();

            }catch (IOException | ClassNotFoundException ex){
                System.out.println("Client disconnected");
                break;
            }
        }
    }
}
