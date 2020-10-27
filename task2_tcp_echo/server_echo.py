#!/usr/bin/python3
import socket
from util import *


def main():
    local_ip = prompt_default("Please enter ip to bind to", input_type=str, default="0.0.0.0")
    while True:
        local_port = prompt_default("Please provide port to listen on", input_type=int, default="12345")
        if 0 <= local_port <= 65535:
            break

    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    if s:
        log("Socket created")
    s.bind((local_ip, local_port))
    log("Bound")
    s.listen(1)
    log(f"Listening on {local_ip} on port {local_port}")
    while True:
        c, address = s.accept()
        log(f"Connection from {address[0]}:{address[1]}")
        while True:
            try:
                buffer = c.recv(64)
                if len(buffer) == 0:
                    break
            except ConnectionResetError:
                break
            log(f"Received: {buffer}")
            c.send(buffer)
            log(f"Sent: {buffer}")

        log(f"{address[0]}:{address[1]} disconnected")


if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        log("Exiting")
        pass
    except Exception as e:
        print(f"An error occurred: {e}")
