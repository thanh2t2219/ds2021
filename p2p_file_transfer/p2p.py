"""Upload file from server to all peer clients"""


from server_client.constants import *
from server_client.client import Client
from server_client.server import Server



class p2p:
    #the default peer
    peers = ['127.0.0.1']


def main():
    # if the server breks we try to make a client a new server


    msg = fileIO.convert_to_bytes()
    while True:
        try:
            print("-" * 5 + "Trying to connect" + "-" * 5)
            # sleep a random time between 1 -5 seconds
            time.sleep(randint(RAND_TIME_START,RAND_TIME_END))
            for peer in p2p.peers:
                try:
                    client = Client(peer)
                except KeyboardInterrupt:
                    sys.exit(0)
                except:
                    pass


                # become the server
                try:
                    server = Server(msg)
                except KeyboardInterrupt:
                    sys.exit()
                except:
                    pass

        except KeyboardInterrupt as e:
            sys.exit(0)

if __name__ == "__main__":
    main()
