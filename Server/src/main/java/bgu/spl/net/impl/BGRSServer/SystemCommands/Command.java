package bgu.spl.net.impl.BGRSServer.SystemCommands;

import bgu.spl.net.srv.BlockingConnectionHandler;

public abstract class Command {
    private int opCode;
    public int getOpCode(){return opCode;}
    public abstract ServerCommand execute();//TODO: make sure whether its protected

}
