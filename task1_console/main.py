#!/usr/bin/python3
import termcolor
import time
import random

colors = [
    'on_red',
    'on_green',
    'on_yellow',
    'on_blue',
    'on_magenta',
    'on_cyan'
]


def print_at(text, line, column):
    print(f"\033[s\033[{line};{column}H{text}\033[u", end="", flush=True)


def seq(n):
    return ((-1) ** (n - 1)) / (2 * n - 1)


i = 1
s = 0
while True:
    s += seq(i)
    print(termcolor.colored(f"{seq(i): .4f}", "white", random.choice(colors)))
    print_at(termcolor.colored(f"{s: .4f}", "white", random.choice(colors)), 10, 10)
    i += 1
    time.sleep(0.5)
