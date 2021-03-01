#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <Winsock2.h>
int main(){
	//create the simple string in server that we send any client to connect
	char server_message[256]="You have reached the server";
	//create the server socket
	int server_socket;// return value of our socket call
	//TCP socket : using AF_INET & SOCKET_STREAM
	//protocol = 0
	server_socket = socket(AF_INET, SOCK_STREAM, 0);
	//define the server address
	struct sockaddr_in server_address;
	//connection actually connect to each others
	server_address.sin_family = AF_INET;
	server_address.sin_port = htons(9002);
	//INADDR_ANY are any constants basically result to
	//any IP address on the local machine
	server_address.sin_addr.s_addr = INADDR_ANY;
	//bind the socket to specified IP and port
	bind(server_socket, (struct sockaddr*)&server_address,sizeof(server_address));
	//listening the connections
	listen(server_socket,5);//any number at least 1
	//create an interger to hold the client socket

	int client_socket;
	//finger out wherer server connected
	client_socket=accept(server_socket,NULL,NULL);

	//send the message
	send(client_socket, server_message,sizeof(server_message),0);
	//close the socket
	close(server_socket);
	return 0;
}
