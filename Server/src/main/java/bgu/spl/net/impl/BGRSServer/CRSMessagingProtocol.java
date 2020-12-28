package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.BGRSServer.DB.Database;
import bgu.spl.net.impl.BGRSServer.SystemCommands.*;
import bgu.spl.net.impl.BGRSServer.SystemCommands.SingleCommands.*;

public class CRSMessagingProtocol implements MessagingProtocol<Command> {
    private boolean shouldTerminate = false;
    private String userName = null;

    /**
     * Process the Command sent from the user, using msg.execute() for the different actions per OPCode.
     * Make sure to validate whether the action is allowed, if the user should be logged in and is the right
     * userType for the action.
     * Make sure to update userName with the currently loggedIn user (null if no user loggedIn)
     *
     * @param msg the received Command
     * @return @ServerCommand (ACK/ERR) with the relevant response for the action
     */
    public Command process(Command msg) {

        int opCode = msg.getOpCode();
        /*Validation:
            If the user should be logged in to preform the command or is not allowed to (Admin VS Student)
         */
        if (((opCode > 3) & (userName == null)) | ((opCode > 4) & userTypeVerify(msg))) {
            return new ERR(msg.getOpCode());
        }

        //execute task
        ServerCommand resultOP = msg.execute();
        //if action was successful:
        if (resultOP.getClass() == ACK.class) {
            //if msg is login - update username
            if (msg.getOpCode() == 3) {
                userName = ((Login) msg).getUserName();
            }
            //if msg is logout - update username and shouldTerminate
            if (msg.getOpCode() == 4) {
                shouldTerminate = true;
                userName = null;
            }
        }
        return resultOP;
    }

    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    /**
     * Make sure whether the user (@op.username) is allowed to perform the action or isn't.
     * Admin isn't allowed to perform
     *
     * @param op - the client command the user with to perform
     * @return false if there is the user isn't allowed to use the Command, or else if he is
     */
    private boolean userTypeVerify(Command op) {
        boolean isAdmin = Database.getInstance().isAdmin(userName);
        Class opClass = op.getClass();
        if (((!isAdmin) & (AdminCommand.class.isInstance(op))) | (isAdmin & !(AdminCommand.class.isInstance(op)))) {
            return false;
        }
        return true;
    }
}
