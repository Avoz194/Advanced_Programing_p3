package bgu.spl.net.impl.BGRSServer;
import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.BGRSServer.SystemCommands.*;
import bgu.spl.net.impl.BGRSServer.SystemCommands.SingleCommands.*;

public class CRSMessagingProtocol implements MessagingProtocol<Command>{
    private boolean shouldTerminate=false;
    private String userName=null;

    public CRSMessagingProtocol(){}

    public Command process(Command msg) {

        //execute task
        ServerCommand resultOP = msg.execute();
        //if wasn't ERR:
        if(resultOP.getClass()!= ERR.class)
        {

            //if msg is login - update username
            if(msg.getOpCode()==3){
                userName=((Login)msg).getUserName();
            }
            //if msg is logout - update usertnamd and shouldTerminate
            if(msg.getOpCode()==4){
                shouldTerminate=false;
                userName=null;
            }
        }
        return null;
    }
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
    private boolean userTypeVerify(Command op){
        boolean isAdmin = false;
        Class opClass = op.getClass();
        if(isAdmin)
        return false;
    }
}
