CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system

all: Client
	g++ -o bin/clientEncDec bin/connectionHandler.o bin/clientMain.o $(LDFLAGS)

Client: bin/clientEncDec.o bin/connectionHandler.o bin/clientMain.o

bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp

bin/clientMain.o: src/clientMain.cpp
	g++ $(CFLAGS) -o bin/clientMain.o src/clientMain.cpp

bin/clientMain.o: src/clientEncDec.cpp
	g++ $(CFLAGS) -o bin/clientEncDec.o src/clientEncDec.cpp

.PHONY: clean
clean:
	rm -f bin/*

