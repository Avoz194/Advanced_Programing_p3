package bgu.spl.net.impl.BGRSServer.SystemCommands.SingleCommands;

import bgu.spl.net.impl.BGRSServer.DB.Database;
import bgu.spl.net.impl.BGRSServer.SystemCommands.AdminCommand;
import bgu.spl.net.impl.BGRSServer.SystemCommands.ServerCommand;

import java.util.NoSuchElementException;

public class CourseStat extends AdminCommand {
    private int courseNumber;
    private static final int numOZeroDelimiter = 2;
    private static final int lengthOfMsg = 4;

    public CourseStat(int courseNumber) {
        super(7);
        this.courseNumber = courseNumber;
    }

    @Override
    public ServerCommand execute() {
        Database db = Database.getInstance();
        String courseData;
        try {
            courseData = db.getCourseStat(courseNumber);
        } catch (NoSuchElementException e) {
            return error();
        }
        ACK response = succAction();
        response.setOptionalMsg(courseData);
        return response;
    }
    public static int getNumOZeroDelimiter(){return numOZeroDelimiter;}
    public static int getLengthOfMsg() {
        return lengthOfMsg;
    }
}
