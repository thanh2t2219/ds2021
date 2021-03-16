#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <Winsock2.h>

int main(){
	//create a socket
	int network_socket;
	//AF_INET is the domain of the socket, SOCK_STREAM is to be type of the socket, also mean it's a TCP socket, connection socket
	network_socket = socket(AF_INET, SOCK_STREAM,0);
	//specify an address for the socket
	struct sockaddr_in server_address;
	//first create socket family, it'll be the same as the family of AF_INET socket 
	//to know what type of address we are working with 
	server_address.sin_family = AF_INET;
	//specify the port that we connect to 
	// the right network byte order : 
	server_address.sin_port = htons(9002);
	//define structure for server address
	server_address.sin_addr.s_addr = INADDR_ANY;//real server that need to connect to
	int connection_status = connect(network_socket,(struct sockaddr *)&server_address, sizeof(server_address));
	//check for error connection 
	if(connection_status==-1){
		printf("There was an error asking a connection to the remote socket \n\n");
	}
	//receive data from server 
	char server_response[256];
	recv(network_socket, (char *)server_response, sizeof(server_response),0);
	//print out the server response 
	printf("the server sent data : %s\n",server_response );
	//close the socket
	close(network_socket);
	return 0;
}