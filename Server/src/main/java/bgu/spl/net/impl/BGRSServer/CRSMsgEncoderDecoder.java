package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGRSServer.SystemCommands.Commands;

public class CRSMsgEncoderDecoder implements MessageEncoderDecoder<Commands> {
    public Commands decodeNextByte(byte nextByte) {
        return null;
    }

    public byte[] encode(Commands message) {
        return null;
    }

}
