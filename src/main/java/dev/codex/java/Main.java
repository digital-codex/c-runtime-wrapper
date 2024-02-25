package dev.codex.java;

import dev.codex.java.glibc.GLibWrapper;
import dev.codex.java.glibc.InterfaceRequest;

public class Main {
    public static void main(String[] args) {
        InterfaceRequest ptr = (InterfaceRequest) GLibWrapper.malloc(InterfaceRequest.class, 1);
    }
}