#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>

int main() {
	int socket_server, cli, pid;;
	struct sockaddr_in server_addr;
	
	// store the transfered message
	char s[100]
	
	socklen_t addr_length = sizeof(server_addr);
	
	// create a socket for server side
	socket_server = socket(AF_INET, SOCK_STREAM, 0);
	
	// check if socket is created properly
	if(socket_server < 0) {
		printf("Socket is not created properly");
	}
	
	// bind the socket to port 2222
    memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY;
    server_addr.sin_port = htons(2222);
    bind(socket_server, (struct sockaddr *)&server_addr, addr_length);
	
	// check if socket is binded properly
	if(bind(socket_server, (struct sockaddr *)&server_addr, addr_length)) {
		printf("Error in binding socket");
	}
	
	// then listen
    listen(socket_server, 0);
	
	while (1) {
        // an incoming connection
        cli = accept(socket_server, (struct sockaddr *)&server_addr, &addr_length);

        pid = fork();
        if (pid == 0) {
            // I'm the son, I'll serve this client
            printf("client connected\n");
            while (1) {
                // it's client turn to chat, I wait and read message from client
                read(cli, s, sizeof(s));
                printf("Client says: %s\n",s);

                // now it's my (server) turn
                printf("Server>", s);
                scanf("%s", s);
                write(cli, s, strlen(s) + 1);
            }
            return 0;
        }
        else {
            // I'm the father, continue the loop to accept more clients
            continue;
        }
    }
    // disconnect
    close(cli);
}