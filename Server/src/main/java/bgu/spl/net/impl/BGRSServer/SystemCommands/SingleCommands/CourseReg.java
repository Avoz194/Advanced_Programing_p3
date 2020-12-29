package bgu.spl.net.impl.BGRSServer.SystemCommands.SingleCommands;

import bgu.spl.net.impl.BGRSServer.DB.Database;
import bgu.spl.net.impl.BGRSServer.SystemCommands.ServerCommand;
import bgu.spl.net.impl.BGRSServer.SystemCommands.StudentCommand;

public class CourseReg extends StudentCommand {
    private int courseNumber;
    private static final int numOZeroDelimiter = 2;
    private static final int lengthOfMsg = 4;
    public CourseReg(int courseNumber) {
        super(5);
        this.courseNumber=courseNumber;
    }

    @Override
    public ServerCommand execute() {
        Database db = Database.getInstance();
        boolean result = db.registerToCourse(userName,courseNumber);
        if (result) return succAction();
        else return error();
    }
    public static int getNumOZeroDelimiter(){return numOZeroDelimiter;}
    public static int getLengthOfMsg() {
        return lengthOfMsg;
    }
}
