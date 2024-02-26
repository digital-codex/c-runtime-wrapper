#include <dev_codex_java_wrapper_runtime_StdIO.h>
#include <dev_codex_java_wrapper_runtime_StdLib.h>

#include <stdio.h>
#include <stdlib.h>

#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     dev_codex_java_glibc_StdIO
 * Method:    fclose
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_wrapper_runtime_StdIO_fclose(JNIEnv *env, jclass clazz, jlong jstream) {
    return (jint) fclose((FILE*) jstream);
}

/*
 * Class:     dev_codex_java_glibc_StdIO
 * Method:    fopen
 * Signature: (Ljava/lang/String;Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_dev_codex_java_wrapper_runtime_StdIO_fopen(JNIEnv *env, jclass clazz, jstring jfilename, jstring jmodes) {
    FILE* result = NULL;

    const char* filename = (*env)->GetStringUTFChars(env, jfilename, NULL);
    const char* modes = (*env)->GetStringUTFChars(env, jmodes, NULL);
    result = fopen(filename, modes);
    (*env)->ReleaseStringUTFChars(env, jfilename, filename);
    (*env)->ReleaseStringUTFChars(env, jmodes, modes);

    return (jlong) result;
}

/*
 * Class:     dev_codex_java_glibc_StdIO
 * Method:    fread
 * Signature: (JJJJ)J
 */
JNIEXPORT jlong JNICALL Java_dev_codex_java_wrapper_runtime_StdIO_fread(JNIEnv *env, jclass clazz, jlong jptr, jlong jsize, jlong jn, jlong jstream) {
    return fread((void*) jptr, (size_t) jsize, (size_t) jn, (FILE*) jstream);
}

/*
 * Class:     dev_codex_java_glibc_StdIO
 * Method:    fwrite
 * Signature: (JJJJ)J
 */
JNIEXPORT jlong JNICALL Java_dev_codex_java_wrapper_runtime_StdIO_fwrite(JNIEnv *env, jclass clazz, jlong jptr, jlong jsize, jlong jn, jlong js) {
    return fwrite((void*) jptr, (size_t) jsize, (size_t) jn, (FILE*) js);
}

/*
 * Class:     dev_codex_java_glibc_StdIO
 * Method:    fseek
 * Signature: (JJI)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_wrapper_runtime_StdIO_fseek(JNIEnv *env, jclass clazz, jlong jstream, jlong joff, jint jwhence) {
    return fseek((FILE*) jstream, (long) joff, (int) jwhence);
}

/*
 * Class:     dev_codex_java_glibc_StdLib
 * Method:    malloc
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_dev_codex_java_wrapper_runtime_StdLib_malloc(JNIEnv *env, jclass clazz, jlong jsize) {
    return (jlong) malloc((size_t) jsize);
}

/*
 * Class:     dev_codex_java_glibc_StdLib
 * Method:    free
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_dev_codex_java_wrapper_runtime_StdLib_free(JNIEnv *env, jclass clazz, jlong jptr) {
    free((void*) jptr);
}

#ifdef __cplusplus
}
#endif