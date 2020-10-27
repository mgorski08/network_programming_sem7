#!/usr/bin/python3
import socket
from util import *

def main():
    remote_ip = prompt_default("Please enter remote ip",
                               input_type=str,
                               default="127.0.0.1")
    remote_port = prompt_default_restrain("Please enter remote port",
                                          input_type=int,
                                          restraint=lambda x: 0 <= x <= 65535,
                                          default="12345")
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    if s:
        log("Socket created")
    s.connect((remote_ip, remote_port))
    log(f"Connected to: {remote_ip}:{remote_port}")
    while True:
        user_input = input()
        user_input += '\n'
        message_bytes = user_input.encode('utf-8')
        s.send(message_bytes)
        log("Sent")
        log(f"Received: {s.recv(len(message_bytes))}")

if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        pass
    except Exception as e:
        print(f"An error occurred: {e}")
