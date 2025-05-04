import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port){
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port: " + port);

            clientSocket = serverSocket.accept();
            System.out.println("Client connected!");

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String wordToGuess = "hangman";
            out.println(wordToGuess);

            String clientResponse = in.readLine();
            System.out.println("Client guessed: " + clientResponse);

            stop();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
            System.out.println("Server stopped.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Server server = new Server();
        server.start(1234);
    }
}
