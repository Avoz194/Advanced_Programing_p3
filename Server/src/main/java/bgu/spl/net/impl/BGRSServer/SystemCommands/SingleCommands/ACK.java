package bgu.spl.net.impl.BGRSServer.SystemCommands.SingleCommands;

import bgu.spl.net.impl.BGRSServer.SystemCommands.ServerCommand;

public class ACK extends ServerCommand {
    private String optionalMsg=null;

    public ACK(int messageOpcode) {
        super(12, messageOpcode);
    }
    public void setOptionalMsg(String msg){optionalMsg=msg;}

    @Override
    public String encode() {
        return null;
    }
}
