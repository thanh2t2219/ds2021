#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>

int main(int argc, char *argv[])
{
    // declaration
    int client_socket, n, portno;
    struct sockaddr_in server_addr;
    struct hostent *server;

    char buffer[1024];

    if (argc < 3) {
       fprintf(stderr,"usage %s hostname port\n", argv[0]);
       exit(0);
    }
    portno = atoi(argv[2]);

    // create socket
    client_socket = socket(AF_INET, SOCK_STREAM, 0);


    // init address
    server = gethostbyname(argv[1]);

    bzero((char *) &server_addr, sizeof(server_addr));

    server_addr.sin_family = AF_INET;
    bcopy((char *)server->h_addr,
         (char *)&server_addr.sin_addr.s_addr,
         server->h_length);
    server_addr.sin_port = htons(portno);


    // connect to server
    connect(client_socket,(struct sockaddr *) &server_addr,sizeof(server_addr));
    printf("Client: ");

    while(1)
    {
        // after connected, it's client turn to chat

        // send some data to server
        bzero(buffer,1024);
        fgets(buffer,1023,stdin);
        n = write(client_socket,buffer,strlen(buffer));

        // then it's server turn
        bzero(buffer,1024);
        n = read(client_socket,buffer,1023);

        printf("Server : %s\n",buffer);
    }
    close(client_socket);
    return 0;
}
