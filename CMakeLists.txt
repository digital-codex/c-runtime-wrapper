cmake_minimum_required(VERSION 3.26)
project(glib-wrapper C)

set(CMAKE_C_STANDARD 17)

set(
		SOURCES
		src/main/c/GLibWrapper.c
)

set(
		INCLUDES
		target/include/
)