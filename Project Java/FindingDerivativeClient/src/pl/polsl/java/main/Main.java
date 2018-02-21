/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.java.main;

import pl.polsl.java.commandsender.*;

/**
 * Main class responsible for work on the client side.
 *
 * @author Monika Kokot
 * @version 3.0
 */
public class Main {

    /**
     * Main mathod. It calls client constructor and it's main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CommandSender cmdSend = new CommandSender();
        try {
            cmdSend.startClient();
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
    }
}
