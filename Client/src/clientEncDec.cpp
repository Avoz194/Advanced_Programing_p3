
#include "../include/clientEncDec.h"

char[] ClientEncDec::encode(std::string strToByt){
    //the first word in all commands is the op
    int indexOfFirstSpace = strToByt.find(' ');
    string com= strToByt.substr(0,indexOfFirstSpace);
    //making short op out of the string command
    short op = coToOp(com);
    int len = strToByt.length-indexOfFirstSpace+2;
    char[] ans = new char[len];
    //getting the first 2 byts out of the op code
    ans[0] = ((op >> 8) & 0xFF);
    ans[1] = (op & 0xFF);
    // getting rest of the message
    for(int i=2;i<len;i++){
        ans[i]=strToByt.at(i+indexOfFirstSpace-2);
    }
    return ans;
}
string ClientEncDec::decode(char [] bytes){
    int len=bytes.length;
    std::string op;
    std::string aditionalOp;
    std::string optionalMsg;
    std::string ans='';

    //getting first two bytes
    short first = (short)((bytes[0] & 0xff) << 8);
    first +=(short)(bytes[1] & 0xff);
    op = ClientEncDec::opToCom(first);
    //getting second two bytes
    short second = (short)((bytes[2] & 0xff) << 8);
    second +=(short)(bytes[3] & 0xff);
    aditionalOp = ClientEncDec::opToCom(second);
    //TODO: this is how to decode ?
    //decoding the rest of the command as a string
    if(bytes.length>4){
        ans=ans+op+aditionalOp;
        for(int =4;bytes.length;i++)
        ans+=(String)(bytes[i] & 0xff);
    }
    return ans;
}
//for decode, turn command string to short op
string ClientEncDec::opToCom(short op){
    if(op==12){
        return "ACK";
    }else{
        return "ERROR";
    }
}
//for encode, turn short op to string command
short ClientEncDec::coToOp(std::string com){
    short op;
    switch(com){
        case "ADMINREG":
            op = ((short)1);
            break;
        case "STUDENTREG":
            op = ((short)2);
            break;
        case "LOGIN":
            op = ((short)3);
            break;
        case "LOGOUT":
            op = ((short)4);
            break;
        case "COURSEREG":
            op = ((short)5);
            break;
        case "KDAMCHECK":
            op = ((short)6);
            break;
        case "COURSESTAT":
            op = ((short)7);
            break;
        case "STUDENTSTAT":
            op = ((short)8);
            break;
        case "ISREGISTER":
            op = ((short)9);
            break;
        case "UNREGISTER":
            op = ((short)10);
            break;
        case "MYCOURSES":
            op = ((short)11);
            break;
    }
}

