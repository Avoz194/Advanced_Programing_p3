package bgu.spl.net.impl.BGRSServer.SystemCommands.SingleCommands;

import bgu.spl.net.impl.BGRSServer.DB.Database;
import bgu.spl.net.impl.BGRSServer.SystemCommands.*;

public class Logout extends ClientCommand {
    private static final int numOZeroDelimiter = 2;
    private static final int lengthOfMsg = 4;

    public Logout(){super(4);}

    @Override
    public ServerCommand execute() {
        Database db = Database.getInstance();
        boolean result = db.logOutUser(userName);
        if (result) return succAction();
        else return error();
    }
    public static int getNumOZeroDelimiter(){return numOZeroDelimiter;}
    public static int getLengthOfMsg() {
        return lengthOfMsg;
    }
}
