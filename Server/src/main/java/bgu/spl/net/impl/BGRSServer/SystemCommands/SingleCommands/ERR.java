package bgu.spl.net.impl.BGRSServer.SystemCommands.SingleCommands;

import bgu.spl.net.impl.BGRSServer.SystemCommands.*;

public class ERR extends ServerCommand {

    public ERR(int messageOpcode){super(13,messageOpcode);}

    @Override
    public String encode() {
        return null;
    }
}
