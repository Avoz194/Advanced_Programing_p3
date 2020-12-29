package bgu.spl.net.impl.BGRSServer.SystemCommands.SingleCommands;

import bgu.spl.net.impl.BGRSServer.DB.Database;
import bgu.spl.net.impl.BGRSServer.SystemCommands.AdminCommand;
import bgu.spl.net.impl.BGRSServer.SystemCommands.ServerCommand;

public class AdminReg extends AdminCommand {
    String password;
    private static final int numOZeroDelimiter = 2;
    private static final int lengthOfMsg = 4;

    public AdminReg(String user, String pass) {
        super(1);
        userName = user;
        password = pass;
    }

    @Override
    public ServerCommand execute() {
        Database db = Database.getInstance();
        boolean result = db.registerNewUser(userName, password,true);
        if (result) return succAction();
        else return error();
    }
    public static int getNumOZeroDelimiter(){return numOZeroDelimiter;}
    public static int getLengthOfMsg() {
        return lengthOfMsg;
    }
}
