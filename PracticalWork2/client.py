# client.py
import sys, xmlrpclib

proxy = xmlrpclib.Server('http://localhost:9000')

with open("text.txt", "rb") as handle:
    binary_data = xmlrpclib.Binary(handle.read())
    proxy.server_receive_file(binary_data)
