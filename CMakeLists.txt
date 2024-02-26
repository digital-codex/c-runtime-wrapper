cmake_minimum_required(VERSION 3.26)
project(GLibWrapper C)

set(CMAKE_C_STANDARD 17)
set(CMAKE_CXX_STANDARD 17)

set(
		SOURCES
		${CMAKE_CURRENT_SOURCE_DIR}/src/main/c/GLibWrapper.c
)

set(
		INCLUDES
		${CMAKE_CURRENT_SOURCE_DIR}/target/include/dev_codex_java_wrapper_runtime_StdIO.h
		${CMAKE_CURRENT_SOURCE_DIR}/target/include/dev_codex_java_wrapper_runtime_StdLib.h
)

add_custom_target(
		library-directory ALL COMMAND
		${CMAKE_COMMAND} -E make_directory ${CMAKE_CURRENT_SOURCE_DIR}/target/library
)
set(
		CMAKE_LIBRARY_OUTPUT_DIRECTORY
		${CMAKE_CURRENT_SOURCE_DIR}/src/main/resources
)

add_library(GLibWrapper SHARED ${SOURCES} ${INCLUDES})
target_include_directories(
		GLibWrapper PRIVATE
		${CMAKE_CURRENT_SOURCE_DIR}/target/include
		$ENV{JAVA_HOME}/include $ENV{JAVA_HOME}/include/linux
)