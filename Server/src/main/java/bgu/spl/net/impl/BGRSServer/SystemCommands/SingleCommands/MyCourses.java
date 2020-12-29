package bgu.spl.net.impl.BGRSServer.SystemCommands.SingleCommands;

import bgu.spl.net.impl.BGRSServer.DB.Database;
import bgu.spl.net.impl.BGRSServer.SystemCommands.ServerCommand;
import bgu.spl.net.impl.BGRSServer.SystemCommands.StudentCommand;

import java.util.NoSuchElementException;

public class MyCourses extends StudentCommand {

    private static final int numOZeroDelimiter = 2;
    private static final int lengthOfMsg = 4;

    public MyCourses() {
        super(11);
    }

    @Override
    public ServerCommand execute() {
        Database db = Database.getInstance();
        String courses;
        try {
            courses = db.getMyCourses(userName);
        } catch (NoSuchElementException e) {
            return error();
        }
        ACK response = succAction();
        response.setOptionalMsg(courses);
        return response;
    }
    public static int getNumOZeroDelimiter(){return numOZeroDelimiter;}
    public static int getLengthOfMsg() {
        return lengthOfMsg;
    }
}
