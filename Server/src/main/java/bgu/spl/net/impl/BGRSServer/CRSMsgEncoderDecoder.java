package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGRSServer.SystemCommands.Commands;
import bgu.spl.net.impl.BGRSServer.SystemCommands.SingleCommands.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CRSMsgEncoderDecoder implements MessageEncoderDecoder<Commands> {
    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    private int indexOfFirstZero = 0;
    private int indexOfSecondZero = 0;


    /**
     * @param nextByte the next byte to consider for the currently decoded
     *                 message
     * @return a decoded command
     */
    public Commands decodeNextByte(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        if (nextByte == '\n') {
            short op = getOp();
            return commandToBuild(op); //return a command depandes by the op number
        }
        pushByte(nextByte);
        return null; //not a line yet
    }

    /**
     * pushs the bytes and memeo the indexes of the 00X0 (if there are)
     *
     * @param nextByte
     */
    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }
        if (nextByte == '\0' && indexOfFirstZero == 0) {
            indexOfFirstZero = len;
        } else if (nextByte == '\0') {
            indexOfSecondZero = len;
        }
        bytes[len++] = nextByte;
    }


    /**
     * //return a short with the command op
     *
     * @return short op out of byte
     */
    private short getOp() {
        byte[] b = new byte[2];
        b[0] = bytes[0];
        b[1] = bytes[1];
        short result = (short) ((b[0] & 0xff) << 8);
        result += (short) (b[1] & 0xff);
        return result;
    }


    /**
     * this function build a command to be sent.
     * as the decoder gets a byte msg and the server want to understand out program transform the byte code
     * into a command
     * <p>
     * as well as parsing the strings attach spesified for each diffrent op
     *
     * @param op
     * @return a command specified with the op
     */
    private Commands commandToBuild(short op) {
        String userName;
        String passWord;
        String courseNumber;
        int num;
        switch (op) {
            case (short) 1:
                userName = new String(bytes, 2, indexOfFirstZero, StandardCharsets.UTF_8);
                passWord = new String(bytes, indexOfFirstZero, len, StandardCharsets.UTF_8);
                len = 0;
                return new AdminReg(userName, passWord);
            break;
            case (short) 2:
                userName = new String(bytes, 2, indexOfFirstZero, StandardCharsets.UTF_8);
                passWord = new String(bytes, indexOfFirstZero, len, StandardCharsets.UTF_8);
                len = 0;
                return new StudentReg(userName, passWord);
            break;
            case (short) 3:
                userName = new String(bytes, 2, indexOfFirstZero, StandardCharsets.UTF_8);
                passWord = new String(bytes, indexOfFirstZero, len, StandardCharsets.UTF_8);
                len = 0;
                return new Login(userName, passWord);
            break;
            case (short) 4:
                return new Logout();
            break;
            case (short) 5:
                courseNumber = new String(bytes, 2, len, StandardCharsets.UTF_8);
                num = Integer.parseInt(courseNumber);
                len=0;
                return new CourseReg(num);
            break;
            case (short) 6:
                courseNumber = new String(bytes, 2, len, StandardCharsets.UTF_8);
                num = Integer.parseInt(courseNumber);
                len=0;
                return new KdamCheck(num);
            break;
            case (short) 7:
                courseNumber = new String(bytes, 2, len, StandardCharsets.UTF_8);
                num = Integer.parseInt(courseNumber);
                len=0;
                return new CourseStat(num);
            break;
            case (short) 8:
                userName = new String(bytes, 2, indexOfFirstZero, StandardCharsets.UTF_8);
                len=0;
                return new StudentStat(userName);
            break;
            case (short) 9:
                courseNumber = new String(bytes, 2, len, StandardCharsets.UTF_8);
                num = Integer.parseInt(courseNumber);
                len=0;
                return new IsRegistered(num);
            break;
            case (short) 10:
                courseNumber = new String(bytes, 2, len, StandardCharsets.UTF_8);
                num = Integer.parseInt(courseNumber);
                len=0;
                return new UnRegister(num);
            break;
            case (short) 11:
                return new MyCourses();
            break;
        }
        return new ERR(1); //TODO:what we doing if there is a problem ?
    }

    /**
     * we can encode 2 massages
     * error who encode short op to byts and appened the encoded messageop
     * acknoladge does the same but it appaned the encoded string (massage) using
     * java utf8 regular encoder with the \0 in the end
     *
     * @param message the message to encode
     * @return encoded command
     */
    public byte[] encode(Commands message) {
        short op = (short) message.getOpCode();
        short mOp = (short) message.getMassageOpCode(); //TODO: to impl
        String str;
        byte[] bytesArrToApp = new byte[0];
        int length = 4;
        if (op == 12) {
            str = message.getOptionalMsg(); //TODO: to impl
            bytesArrToApp = (str + "\n").getBytes();
            length = 4 + bytesArrToApp.length;
        }
        byte[] bytesArr = new byte[length];
        bytesArr[0] = (byte) ((op >> 8) & 0xFF);
        bytesArr[1] = (byte) (op & 0xFF);
        bytesArr[2] = (byte) ((mOp >> 8) & 0xFF);
        bytesArr[3] = (byte) (mOp & 0xFF);
        for (int i = 4; i < bytesArr.length & op == 12; i++) {
            bytesArr[i] = bytesArrToApp[i - 4];
        }
        return bytesArr;
    }
}
