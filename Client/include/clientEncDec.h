//
// Created by rs on 1/1/21.
//

#ifndef CLIENT_CLIENTENCDEC_H
#define CLIENT_CLIENTENCDEC_H

#include <string>
#include <iostream>
#include <boost/asio.hpp>

using boost::asio::ip::tcp;



class clientEncDec {
public:
    //Encode messages from String to bytes
    char[] encode(std::string strToByt);
    //Decode messages based on ACK and ERR structure, return string
    std::string decode(char[] bytes);
    //make the command string out of the short op
    std::string opToCom(short op);
    //make the short op out of the command string
    short comToOp(std::string com);
};


#endif //CLIENT_CLIENTENCDEC_H
