#!/usr/bin/python3
import socket
from util import *


def main():
    local_ip = prompt_default("Please enter ip to bind to", input_type=str, default="0.0.0.0")
    while True:
        local_port = prompt_default("Please provide port to listen on", input_type=int, default="12345")
        if 0 <= local_port <= 65535:
            break

    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    if server_socket:
        log("Socket created")
    server_socket.bind((local_ip, local_port))
    log("Bound")
    server_socket.listen(1)
    log(f"Listening on {local_ip} on port {local_port}")
    while True:
        client_socket, address = server_socket.accept()
        log(f"Connection from {address[0]}:{address[1]}")
        while True:
            try:
                buffer = client_socket.recv(64)
                if len(buffer) == 0:
                    break
            except ConnectionResetError:
                break
            log(f"Received: {buffer}")
            client_socket.send(buffer)
            log(f"Sent: {buffer}")

        log(f"{address[0]}:{address[1]} disconnected")


if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        print()
        print("Exiting")
        pass
    except Exception as e:
        print(f"An error occurred: {e}")
