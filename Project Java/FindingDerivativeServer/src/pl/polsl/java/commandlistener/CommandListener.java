/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.java.commandlistener;

import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.Properties;
import pl.polsl.java.model.*;

/**
 * Class representing server's command listener. It is responsible for receiving
 * commands from the client, executing proper computing and sending answer to
 * the client.
 *
 * @author Monika Kokot
 * @version 3.0
 */
public class CommandListener {

    /**
     * Port on which server works.
     */
    int port;

    /**
     * Server instance.
     */
    ServerSocket server;

    /**
     * Client instance.
     */
    Socket client;

    /**
     * Input Strign received from the client.
     */
    BufferedReader input;

    /**
     * Output String to send to the client.
     */
    PrintWriter output;

    /**
     * Input polynomial, of which there must be found derivative.
     */
    Polynomial polynomial;

    /**
     * Output polynomial - found derivative.
     */
    Polynomial outPolynomial;

    /**
     * Instance of derivative finding class.
     */
    DerivativeFinding derivativeFinding;

    /**
     * Constructor of server's command listener. It reads port from xml file and
     * sets all data.
     */
    public CommandListener() {
        try {
            Properties prop = new Properties();
            try {
                FileInputStream inStr = new FileInputStream("server.xml");
                prop.loadFromXML(inStr);
                this.port = Integer.parseInt(prop.getProperty("PORT"));
            } catch (FileNotFoundException e) {
                System.err.println(e);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        polynomial = new Polynomial();
        derivativeFinding = new DerivativeFinding();
        outPolynomial = new Polynomial();

    }

    /**
     * Method used while server is running. It accepts client and call method to
     * realize task.
     */
    public void startServer() {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e);
        }

        try {
            while (true) {
                client = server.accept();
                input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                output = new PrintWriter(client.getOutputStream(), true);
                realizeTask();
                output.println("EXIT");
            }

        } catch (IOException e) {
            output.println(e.toString());
        }
    }

    /**
     * Method reading command from client and executing it either client passed
     * inproper protocol parameters or not. It also sends message to client.
     */
    private void realizeTask() {
        try {
            ProtocolParameterParser protocolPars = new ProtocolParameterParser();
            String command;
            while (true) {
                command = input.readLine();
                switch (protocolPars.handleParameters(command)) {
                    case HELP:
                        showHelp();
                        break;
                    case EXE:
                        if (polynomial.getDegree() < 0) {
                            output.println("Answer code: 1. The polynomial degree is set to negativen umber.");
                            break;
                        }
                        outPolynomial = derivativeFinding.findDerivative(polynomial);
                        output.println("Answer code: 0. The output polynomial: " + outPolynomial.getPolynomialString());
                        break;
                    case EXIT:
                        client.close();
                    case PDEG:
                        setParameterPDEG(command);
                        break;
                    case COEF:
                        setParameterCOEF(command);
                        break;
                    case UNKNOWN:
                        output.println("Answer code: 1. No such sommand. Type HELP to check possible commands");
                        break;

                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * It sets the polynomial degree. If user passed it in inproper format, it
     * sends such a message to client.
     *
     * @param cmd the command containing polynomial degree.
     */
    void setParameterPDEG(String cmd) {
        if (cmd.length() < 5) {
            output.println("Answer code: 1. You must pass the degree!");
            return;
        }
        try {
            cmd = cmd.substring(5); //the fifth char will be the beginnig of polynomial degree
            if (Integer.parseInt(cmd) < 0) {
                output.println("Answer code: 1. You cannot pass negative degree of the polynomial.");
                return;
            }
            polynomial.setDegree(Integer.parseInt(cmd));
            output.println("Answer code: 0. Degree of the polynomial is set to " + cmd);
        } catch (NumberFormatException e) {
            output.println("Answer code: 1. Inproper data format.");
        }
    }

    /**
     * It sets the polynomial coefficients. If user passed it in inproper
     * format, it sends such a message to client.
     *
     * @param cmd the command containg polynomial coefficients
     */
    void setParameterCOEF(String cmd) {
        if (polynomial.getDegree() < 0) {
            output.println("Answer code: 1. You must pass polynomial degree before passing coefficients!");
            return;
        };

        if (cmd.length() < 5) {
            output.println("Answer code: 1. You must pass at least one coefficient!");
            return;
        }
        cmd = cmd.substring(5); //the fifth char will be the beginnig of polynomial coefficients

        String[] tmp = cmd.split(";");
        try {
            for (int i = 0; i < polynomial.getDegree() + 1; ++i) {
                polynomial.setACoefficient(0.0, i);
            }
            for (int i = 0; i < tmp.length; ++i) {
                if (i > polynomial.getDegree()) {
                    break;
                }
                polynomial.setACoefficient(Double.parseDouble(tmp[i]), polynomial.getDegree() - i);
            }
            output.println("Answer code: 0. The input polynomial: " + polynomial.getPolynomialString());
        } catch (NumberFormatException e) {
            output.println("Answer code: 1. Inproper data format.");
        }
    }

    /**
     * Method sending help to client.
     */
    void showHelp() {
        output.print("Answer code: 0. List of commands: ");
        output.print(" EXIT - exit the program ");
        output.print(" EXE - execute the program (after setting polynomial parameters) ");
        output.print(" PDEG - degree of the polynomial ");
        output.println(" COEF - coefficients of the polynomial. There must be seperated with semicolon, eg. 2;3;5");
    }
}
