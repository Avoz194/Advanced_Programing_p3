package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGRSServer.SystemCommands.Commands;
import bgu.spl.net.impl.BGRSServer.SystemCommands.ServerCommand;
import bgu.spl.net.impl.BGRSServer.SystemCommands.SingleCommands.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CRSMsgEncoderDecoder implements MessageEncoderDecoder<Commands> {
    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    private int nextZero = 0;
    private int nOz = 0;

    /**
     * decodeNextByte
     * first check if there are 4 byts in the array
     * get the command op
     * using the op to decode the all command base on length and number of zeros
     *
     * @param nextByte the next byte to consider for the currently decoded
     *                 message
     * @return a decoded command
     */
    public Commands decodeNextByte(byte nextByte) {
        short op = 0;
        if (len >= 4) { // checker to see if there are 4 byts of data
            op = getOp();
        }
        if ((op == ((short) 1) | op == ((short) 2) | op == ((short) 3))) {
            if (nextByte == '\0' & nOz != 2) {
                nOz++;
            } else if (nOz == 2) {
                return commandToBuildA(op);
            }
        } else if (op == ((short) 5) | op == ((short) 6) | op == ((short) 7) | op == ((short) 9) | op == ((short) 10)) {
            return commandToBuildB(op);
        } else if (op == ((short) 8)) {
            if (nextByte == '\0' & nOz != 1) {
                nOz++;
            } else if (nOz == 1) {
                return commandToBuildC(op);
            }
        } else {
            throw new IllegalArgumentException("the op is not valid");
        }
        pushByte(nextByte);
        return null; //not finish yet
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
        bytes[len++] = nextByte;
    }

    /**
     * return a short with the command op
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
     * commanToBuildA : AdminReg,StudentReg,Login id: 2 zero
     * commanToBuildB : CourseReg,KdamCheck,CourseStat,IsRegistered,UnRegister id: length = 4 byts
     * commanToBuildC : StudentStat id: 1 zero
     * <p>
     * this methods return a decoded command out of buffer feed of bytes
     *
     * @param op
     * @return a command specified by the op
     */
    private Commands commandToBuildA(short op) {
        int indexOfFirstZero = 0;
        int indexOfSecondZero = 0;
        String userName = "";
        String passWord = "";
        indexOfFirstZero = findNextZero(nextZero);
        userName = new String(bytes, 2, indexOfFirstZero, StandardCharsets.UTF_8);
        indexOfSecondZero = findNextZero(nextZero);
        passWord = new String(bytes, indexOfFirstZero, indexOfSecondZero, StandardCharsets.UTF_8);
        len = 0;
        if (op == ((short) 1)) {
            return new AdminReg(userName, passWord);
        } else if (op == ((short) 2)) {
            return new StudentReg(userName, passWord);
        } else {
            return new Login(userName, passWord);
        }

    }

    private Commands commandToBuildB(short op) {
        String courseNumber;
        int num;
        courseNumber = new String(bytes, 2, 4, StandardCharsets.UTF_8);
        num = Integer.parseInt(courseNumber);
        len = 0;
        nOz = 0;
        if (op == ((short) 5)) {
            return new CourseReg(num);
        } else if (op == ((short) 6)) {
            return new KdamCheck(num);
        } else if (op == ((short) 7)) {
            return new CourseStat(num);
        } else if (op == ((short) 9)) {
            return new IsRegistered(num);
        } else {
            return new UnRegister(num);
        }
    }

    private Commands commandToBuildC(short op) {
        String userName = "";
        userName = new String(bytes, 2, len, StandardCharsets.UTF_8);
        len = 0;
        return new StudentStat(userName);
    }

    /**
     * getting the index of the next zero from the last
     *
     * @param nextZe
     * @return -1 if didnt find any
     */
    private int findNextZero(int nextZe) {
        int index = nextZe + 1;
        while (index != bytes.length) {
            if (bytes[index] == ('\0')) {
                nextZero = index;
                return index;
            }
            index++;
        }
        return (-1);
    }

    /**
     * both of the encode needed command implementation happened in their own class
     *
     * @param message the message to encode
     * @return encoded command
     */

    public byte[] encode(Commands message) {
        short op = (short) message.getOpCode();
        if (op == 12) {
            message = (ACK) (message);
            return message.encode();
        } else {
            message = (ERR) (message);
            return (ERR) message.encode();
        }
    }
}
