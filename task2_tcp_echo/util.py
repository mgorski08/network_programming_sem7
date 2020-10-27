#!/usr/bin/python3
import time

time_started = time.time()


def time_elapsed():
    return time.time() - time_started


def log(text):
    print(f"[{time_elapsed():.8}] {text}")


def prompt_default(prompt_text, input_type, default=None):
    def prompt():
        if default:
            user_input = input(f"{prompt_text} [{default}]: ")
            if user_input:
                return user_input
            else:
                return default
        else:
            return input(f"{prompt_text}: ")

    while True:
        try:
            ret_val = input_type(prompt())
            break
        except ValueError:
            pass

    return ret_val


def prompt_default_restrain(prompt_text, input_type, restraint, default=None):
    while True:
        retval = prompt_default(prompt_text, input_type, default=default)
        if restraint(retval):
            break

    return retval
