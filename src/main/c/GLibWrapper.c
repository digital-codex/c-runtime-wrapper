#include <dev_codex_java_glibc_StdIO.h>
#include <dev_codex_java_glibc_StdLib.h>

#include <stdio.h>

#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     dev_codex_java_glibc_StdIO
 * Method:    fopen
 * Signature: (Ljava/lang/String;Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_dev_codex_java_glibc_StdIO_fopen(JNIEnv *env, jclass clazz, jstring jfilename, jstring jmodes) {
    FILE* result = NULL;

    const char* filename = (*env)->GetStringUTFChars(env, jfilename, NULL);
    const char* modes = (*env)->GetStringUTFChars(env, jmodes, NULL);
    result = fopen(filename, modes);
    (*env)->ReleaseStringUTFChars(env, filename, jfilename);
    (*env)->ReleaseStringUTFChars(env, modes, jmodes);

    return result;
}

/*
 * Class:     dev_codex_java_glibc_StdLib
 * Method:    malloc
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_dev_codex_java_glibc_StdLib_malloc(JNIEnv *env, jclass clazz, jlong size) {
    return (jlong) malloc((size_t) size);
}

/*
 * Class:     dev_codex_java_glibc_StdLib
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_dev_codex_java_glibc_StdLib_free(JNIEnv *env, jclass clazz, jlong ptr) {
    free((void*) ptr)
}

#ifdef __cplusplus
}