package bgu.spl.net.impl.BGRSServer.SystemCommands.SingleCommands;

import bgu.spl.net.impl.BGRSServer.DB.Database;
import bgu.spl.net.impl.BGRSServer.SystemCommands.AdminCommand;
import bgu.spl.net.impl.BGRSServer.SystemCommands.ServerCommand;

import java.util.NoSuchElementException;

public class StudentStat extends AdminCommand {
    private String studentName;

    private static final int numOZeroDelimiter = 2;
    private static final int lengthOfMsg = 4;

    public StudentStat(String student) {
        super(8);
        this.studentName = student;
    }

    @Override
    public ServerCommand execute() {
        Database db = Database.getInstance();
        String studentData;
        try {
            studentData = db.getStudentStat(studentName);
        } catch (NoSuchElementException e) {
            return error();
        }
        ACK response = succAction();
        response.setOptionalMsg(studentData);
        return response;
    }
    public static int getNumOZeroDelimiter(){return numOZeroDelimiter;}
    public static int getLengthOfMsg() {
        return lengthOfMsg;
    }
}
