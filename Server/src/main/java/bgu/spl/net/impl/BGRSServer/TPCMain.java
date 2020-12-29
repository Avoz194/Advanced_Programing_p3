package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.BGRSServer.DB.Database;
import bgu.spl.net.srv.Server;

public class TPCMain {

    public static void main(String[] args) {
        Database.getInstance().initialize("../courses.txt"); //TODO:validate position, as far as I understand should be in the main folder (with Server and Client foldres)
        int port = Integer.parseInt(args[0]);
        Server.threadPerClient(
                port, //port
                () -> new CRSMessagingProtocol(), //protocol factory
                ()->new CRSMsgEncoderDecoder() //message encoder decoder factory
       ).serve();
    }
}
