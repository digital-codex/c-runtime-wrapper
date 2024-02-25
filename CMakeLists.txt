cmake_minimum_required(VERSION 3.26)
project(GLibWrapper C)

set(CMAKE_C_STANDARD 17)

set(
		SOURCES
		src/main/c/GLibWrapper.c
)

set(
		INCLUDES
		target/include/dev_codex_java_glibc_StdIO.h
		target/include/dev_codex_java_glibc_StdLib.h
)

add_executable(GLibWrapper SHARED ${SOURCES} ${INCLUDES})
target_include_directories(
		GLibWrapper PRIVATE
		$<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/target/include/glib-wrapper>
		$<INSTALL_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/target/include>
)