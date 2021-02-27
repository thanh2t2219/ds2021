#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>

int main() {
    int socket_server, cli, pid;
    struct sockaddr_in addr;
    char buffer[1024];
    socklen_t ad_length = sizeof(addr);

    // create the socket
    socket_server = socket(AF_INET, SOCK_STREAM, 0);

    // bind the socket to port 2222
    memset(&addr, 0, sizeof(addr));
    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = INADDR_ANY;
    addr.sin_port = htons(2222);
    bind(socket_server, (struct sockaddr *)&addr, ad_length);

    // then listen
    listen(socket_server, 0);

    while (1) {
        // an incoming connection
        cli = accept(socket_server, (struct sockaddr *)&addr, &ad_length);

        pid = fork();
        if (pid == 0) {
            // I'm the son, I'll serve this client
            printf("client connected\n");
            while (1) {
                // it's client turn to chat, I wait and read message from client
                read(cli, buffer, sizeof(buffer));
                printf("Client says: %s\n",buffer);

                // now it's my (server) turn
                printf("Server>", buffer);
                scanf("%s", buffer);
                write(cli, buffer, strlen(buffer) + 1);
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