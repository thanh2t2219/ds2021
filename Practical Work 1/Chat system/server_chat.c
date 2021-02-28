#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>

int main()
{
    // declaration
    int server_socket, cli, pid;
    char buffer[1023];
    struct sockaddr_in server_addr, client_addr;
    int n;
    socklen_t cli_addr_length = sizeof(client_addr);

    // create a socket
    server_socket = socket(AF_INET, SOCK_STREAM, 0);


    // bind the socket to port 2222
    bzero((char *) &server_addr, sizeof(server_addr));

    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY;
    server_addr.sin_port = htons(2222);

    bind(server_socket, (struct sockaddr *) &server_addr,
              sizeof(server_addr));

    // then listen
    listen(server_socket,0);

    while(1)
    {
        // an incoming connection
        cli = accept(server_socket,
                 (struct sockaddr *) &client_addr,
                 &cli_addr_length);

        pid = fork();

        if(pid == 0) {
            // I'm the son, I'll serve this client
            printf("client connected\n");

            while(1) {
                // it's client turn to chat, I wait and read message from client
                bzero(buffer,1024);
                n = read(cli,buffer,1023);
                printf("Client says: %s\n",buffer);

                // now it's my (server) turn
                bzero(buffer,1024);
                fgets(buffer,1023,stdin);
                n = write(cli,buffer,strlen(buffer));
                printf("Server says: %s\n", buffer);

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
    close(server_socket);
}
