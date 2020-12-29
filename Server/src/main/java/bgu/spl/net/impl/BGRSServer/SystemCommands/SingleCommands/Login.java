package bgu.spl.net.impl.BGRSServer.SystemCommands.SingleCommands;

import bgu.spl.net.impl.BGRSServer.DB.Database;
import bgu.spl.net.impl.BGRSServer.SystemCommands.ClientCommand;
import bgu.spl.net.impl.BGRSServer.SystemCommands.ServerCommand;

public class Login extends ClientCommand {
    private String password;
    private static final int numOZeroDelimiter = 2;
    private static final int lengthOfMsg = 4;

    public Login(String user, String pass) {
        super(3);
        userName=user;
        password=pass;
    }

    @Override
    public ServerCommand execute() {
        Database db = Database.getInstance();
        boolean result = db.logInUser(userName,password);
        if (result) return succAction();
        else return error();
    }
    public static int getNumOZeroDelimiter(){return numOZeroDelimiter;}
    public static int getLengthOfMsg() {
        return lengthOfMsg;
    }
}
