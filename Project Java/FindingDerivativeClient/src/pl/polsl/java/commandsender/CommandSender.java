package pl.polsl.java.commandsender;

import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.Properties;
import pl.polsl.java.view.*;

/**
 * Class representing client's command sender. It is responsible for sending
 * commands to the server, receiving answers from it and showing this answer on
 * the screen.
 *
 * @author Monika Kokot
 * @version 3.0
 */
public class CommandSender {

    /**
     * IP of the host on which client will connect to server.
     */
    String ip;
    /**
     * Port on which client will connect to server.
     */
    int port;
    /**
     * Client connected to the server.
     */
    Socket client;
    /**
     * Input string received from the server.
     */
    BufferedReader input;
    /**
     * Ouput string to send to the server.
     */
    PrintWriter output;
    /**
     * View instance.
     */
    View view;
    /**
     * String sent by client.
     */
    BufferedReader userWriter;

    /**
     * Constructor of Client command sender. It reads port and ip from xml file
     * and sets all data.
     */
    public CommandSender() {
        view = new View();
        try {
            Properties prop = new Properties();
            try {
                FileInputStream inStr = new FileInputStream("client.xml");
                prop.loadFromXML(inStr);
                this.port = Integer.parseInt(prop.getProperty("PORT"));
                this.ip = prop.getProperty("hostAddress");
            } catch (FileNotFoundException e) {
                view.showCommand("No information about port or ip in properties file.");
            }

            client = new Socket(this.ip, this.port);
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(client.getOutputStream(), true);
            userWriter = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
             view.showCommand("Cannot open properties file or configure port and host.");
        }

    }

    /**
     * Method used while client is connected to the server. It rads data from
     * the client and send it to the server.
     *
     * @throws ClassNotFoundException
     */
    public void startClient() throws ClassNotFoundException {
        try {

            while (true) {
                String data = userWriter.readLine();
                output.println(data);
                receiveDataFromServer();
            }
        } catch (IOException | NullPointerException e) {
             view.showCommand("Cannot find server.");
        }
    }

    /**
     * Methos used to receive data from the server and printing it on clint
     * screen.
     *
     * @throws IOException
     */
    private void receiveDataFromServer() throws IOException {

        String command = input.readLine();
        if (command.equals("EXIT")) {
            System.exit(0);
        }
        view.showCommand(command);
    }

}
