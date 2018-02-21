package pl.polsl.java.commandlistener;

/**
 * Class representing parser of protocol parameters.
 *
 * @author Monika Kokot
 * @version 3.0
 */

public class ProtocolParameterParser {

    /**
     * MEthod checking if client sent one of protocol parameter. It does not
     * matter if they are upper nor lower case. If client did not send any of
     * those protocol, method return UNKNOWN parameter.
     *
     * @param input the line client sent
     * @return one of the possible protocols
     */
    ProtocolEnum handleParameters(String input) {
        if (input.toUpperCase().contains("EXIT")) {
            return ProtocolEnum.EXIT;
        }
        if (input.toUpperCase().contains("HELP")) {
            return ProtocolEnum.HELP;
        }
        if (input.toUpperCase().contains("EXE")) {
            return ProtocolEnum.EXE;
        }
        if (input.toUpperCase().contains("COEF")) {
            return ProtocolEnum.COEF;
        }
        if (input.toUpperCase().contains("PDEG")) {
            return ProtocolEnum.PDEG;
        }
        return ProtocolEnum.UNKNOWN;

    }

}
