#include <dev_codex_java_wrapper_runtime_StandardLibrary.h>
#include <dev_codex_java_wrapper_runtime_StandardIO.h>
#include <dev_codex_java_wrapper_runtime_FileControl.h>
#include <dev_codex_java_wrapper_runtime_UnixStandard.h>
#include <dev_codex_java_wrapper_runtime_Strings.h>

#include <sys/types.h>
#include <sys/stat.h>
#include <errno.h>

#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>

#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     dev_codex_java_wrapper_runtime_StandardLibrary
 * Method:    malloc
 * Signature: (J)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_dev_codex_java_wrapper_runtime_StandardLibrary_malloc(JNIEnv *env, jclass clazz, jlong jsize) {
    jclass long_clazz = (*env)->FindClass(env, "java/lang/Long");
    jmethodID valueOf = (*env)->GetStaticMethodID(env, long_clazz, "valueOf", "(J)Ljava/jang/Long;");
    return (*env)->CallStaticObjectMethod(env, long_clazz, valueOf, (jlong) malloc((size_t) jsize));
}

/*
 * Class:     dev_codex_java_wrapper_runtime_StandardLibrary
 * Method:    free
 * Signature: (Ljava/lang/Long;)V
 */
JNIEXPORT void JNICALL Java_dev_codex_java_wrapper_runtime_StandardLibrary_free(JNIEnv *env, jclass clazz, jobject jptr) {
    jclass long_clazz = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longValue = (*env)->GetMethodID(env, long_clazz, "longValue", "()J");
    free((void*) (*env)->CallObjectMethod(env, jptr, longValue));
}

/*
 * Class:     dev_codex_java_wrapper_runtime_StandardLibrary
 * Method:    calloc
 * Signature: (JJ)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_dev_codex_java_wrapper_runtime_StandardLibrary_calloc(JNIEnv *env, jclass clazz, jlong jnmemb, jlong jsize) {
    jclass long_clazz = (*env)->FindClass(env, "java/lang/Long");
    jmethodID valueOf = (*env)->GetStaticMethodID(env, long_clazz, "valueOf", "(J)Ljava/jang/Long;");
    return (*env)->CallStaticObjectMethod(env, long_clazz, valueOf, (jlong) calloc((size_t) jnmemb, (size_t) jsize));
}

/*
 * Class:     dev_codex_java_wrapper_runtime_StandardLibrary
 * Method:    realloc
 * Signature: (Ljava/lang/Long;J)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_dev_codex_java_wrapper_runtime_StandardLibrary_realloc(JNIEnv *env, jclass clazz, jobject jptr, jlong jsize) {
    jclass long_clazz = (*env)->FindClass(env, "java/lang/Long");

    jmethodID longValue = (*env)->GetMethodID(env, long_clazz, "longValue", "()J");
    jmethodID valueOf = (*env)->GetStaticMethodID(env, long_clazz, "valueOf", "(J)Ljava/jang/Long;");

    return (*env)->CallStaticObjectMethod(env, long_clazz, valueOf, (jlong) realloc((void*) (*env)->CallObjectMethod(env, jptr, longValue), (size_t) jsize));
}

/*
 * Class:     dev_codex_java_wrapper_runtime_StandardIO
 * Method:    fopen
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_dev_codex_java_wrapper_runtime_StandardIO_fopen(JNIEnv *env, jclass clazz, jstring jpathname, jstring jmode) {
    FILE* fp = NULL;

    const char* pathname = (*env)->GetStringUTFChars(env, jpathname, NULL);
    const char* mode = (*env)->GetStringUTFChars(env, jmode, NULL);
    fp = fopen(pathname, mode);
    (*env)->ReleaseStringUTFChars(env, jpathname, pathname);
    (*env)->ReleaseStringUTFChars(env, jmode, mode);

    jclass long_clazz = (*env)->FindClass(env, "java/lang/Long");
    jmethodID valueOf = (*env)->GetStaticMethodID(env, long_clazz, "valueOf", "(J)Ljava/lang/Long;");
    return (*env)->CallStaticObjectMethod(env, long_clazz, valueOf, (jlong) fp);
}

/*
 * Class:     dev_codex_java_wrapper_runtime_StandardIO
 * Method:    fdopen
 * Signature: (ILjava/lang/String;)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_dev_codex_java_wrapper_runtime_StandardIO_fdopen(JNIEnv *env, jclass clazz, jint jfd, jstring jmode) {
    FILE* fp = NULL;

    const char* mode = (*env)->GetStringUTFChars(env, jmode, NULL);
    fp = fdopen((int) jfd, mode);
    (*env)->ReleaseStringUTFChars(env, jmode, mode);

    jclass long_clazz = (*env)->FindClass(env, "java/lang/Long");
    jmethodID valueOf = (*env)->GetStaticMethodID(env, long_clazz, "valueOf", "(J)Ljava/jang/Long;");
    return (*env)->CallStaticObjectMethod(env, long_clazz, valueOf, (jlong) fp);
}

/*
 * Class:     dev_codex_java_wrapper_runtime_StandardIO
 * Method:    freopen
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_dev_codex_java_wrapper_runtime_StandardIO_freopen(JNIEnv *env, jclass clazz, jstring jpathname, jstring jmode, jobject jstream) {
    FILE* fp = NULL;

    const char* pathname = (*env)->GetStringUTFChars(env, jpathname, NULL);
    const char* mode = (*env)->GetStringUTFChars(env, jmode, NULL);

    jclass long_clazz = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longValue = (*env)->GetMethodID(env, long_clazz, "longValue", "()J");
    fp = freopen(pathname, mode, (FILE*) (*env)->CallObjectMethod(env, jstream, longValue));
    (*env)->ReleaseStringUTFChars(env, jpathname, pathname);
    (*env)->ReleaseStringUTFChars(env, jmode, mode);

    jmethodID valueOf = (*env)->GetStaticMethodID(env, long_clazz, "valueOf", "(J)Ljava/jang/Long;");
    return (*env)->CallStaticObjectMethod(env, long_clazz, valueOf, (jlong) fp);
}

/*
 * Class:     dev_codex_java_wrapper_runtime_StandardIO
 * Method:    fclose
 * Signature: (Ljava/lang/Long;)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_wrapper_runtime_StandardIO_fclose(JNIEnv *env, jclass clazz, jobject jstream) {
    jclass long_clazz = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longValue = (*env)->GetMethodID(env, long_clazz, "longValue", "()J");
    return (jint) fclose((FILE*) (*env)->CallObjectMethod(env, jstream, longValue));
}

/*
 * Class:     dev_codex_java_wrapper_runtime_StandardIO
 * Method:    fread
 * Signature: ([BJJLjava/lang/Long;)J
 */
JNIEXPORT jlong JNICALL Java_dev_codex_java_wrapper_runtime_StandardIO_fread(JNIEnv *env, jclass clazz, jbyteArray jptr, jlong jsize, jlong jnmemb, jobject jstream) {
    int len = (int) (*env)->GetArrayLength(env, (jarray) jptr);
    char buf[len];
    memset(buf, 0, sizeof(char) * len);

    jclass long_clazz = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longValue = (*env)->GetMethodID(env, long_clazz, "longValue", "()J");
    long n = fread(buf, (size_t) jsize, (size_t) jnmemb, (FILE*) (*env)->CallObjectMethod(env, jstream, longValue));
    (*env)->SetByteArrayRegion(env, jptr, 0, n * ((long) jsize), (const jbyte *) buf);
    return (jlong) n;
}

/*
 * Class:     dev_codex_java_wrapper_runtime_StandardIO
 * Method:    fwrite
 * Signature: ([BJJLjava/lang/Long;)J
 */
JNIEXPORT jlong JNICALL Java_dev_codex_java_wrapper_runtime_StandardIO_fwrite(JNIEnv *env, jclass clazz, jbyteArray jptr, jlong jsize, jlong jnmemb, jobject jstream) {
    int len = (int) (*env)->GetArrayLength(env, (jarray) jptr);
    char buf[len];
    memset(buf, 0, sizeof(char) * len);

    jclass long_clazz = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longValue = (*env)->GetMethodID(env, long_clazz, "longValue", "()J");
    (*env)->GetByteArrayRegion(env, jptr, 0, len, (jbyte *) buf);
    return (jlong) fwrite(buf, (size_t) jsize, (size_t) jnmemb, (FILE*) (*env)->CallObjectMethod(env, jstream, longValue));
}

/*
 * Class:     dev_codex_java_wrapper_runtime_StandardIO
 * Method:    fseek
 * Signature: (Ljava/lang/Long;JI)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_wrapper_runtime_StandardIO_fseek(JNIEnv *env, jclass clazz, jobject jstream, jlong joffset, jint jwhence) {
    jclass long_clazz = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longValue = (*env)->GetMethodID(env, long_clazz, "longValue", "()J");
    return (jint) fseek((FILE*) (*env)->CallObjectMethod(env, jstream, longValue), (long) joffset, (int) jwhence);
}

/*
 * Class:     dev_codex_java_wrapper_runtime_StandardIO
 * Method:    ftell
 * Signature: (Ljava/lang/Long;)J
 */
JNIEXPORT jlong JNICALL Java_dev_codex_java_wrapper_runtime_StandardIO_ftell(JNIEnv *env, jclass clazz, jobject jstream) {
    jclass long_clazz = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longValue = (*env)->GetMethodID(env, long_clazz, "longValue", "()J");
    return (jlong) ftell((FILE*) (*env)->CallObjectMethod(env, jstream, longValue));
}

/*
 * Class:     dev_codex_java_wrapper_runtime_StandardIO
 * Method:    fgetpos
 * Signature: (Ljava/lang/Long;Ljava/lang/Long;)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_wrapper_runtime_StandardIO_fgetpos(JNIEnv *env, jclass clazz, jobject jstream, jobject jpos) {
    jclass long_clazz = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longValue = (*env)->GetMethodID(env, long_clazz, "longValue", "()J");
    return (jint) fgetpos((FILE*) (*env)->CallObjectMethod(env, jstream, longValue), (fpos_t*) (*env)->CallObjectMethod(env, jpos, longValue));
}

/*
 * Class:     dev_codex_java_wrapper_runtime_StandardIO
 * Method:    fsetpos
 * Signature: (Ljava/lang/Long;Ljava/lang/Long;)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_wrapper_runtime_StandardIO_fsetpos(JNIEnv *env, jclass clazz, jobject jstream, jobject jpos) {
    jclass long_clazz = (*env)->FindClass(env, "java/lang/Long");
    jmethodID longValue = (*env)->GetMethodID(env, long_clazz, "longValue", "()J");
    return (jint) fgetpos((FILE*) (*env)->CallObjectMethod(env, jstream, longValue), (fpos_t*) (*env)->CallObjectMethod(env, jpos, longValue));
}

/*
 * Class:     dev_codex_java_wrapper_runtime_FileControl
 * Method:    open
 * Signature: (Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_wrapper_runtime_FileControl_open(JNIEnv *env, jclass clazz, jstring jpathname, jint jflags) {
    int fd = -1;

    const char* pathname = (*env)->GetStringUTFChars(env, jpathname, NULL);
    fd = open(pathname, (int) jflags);
    (*env)->ReleaseStringUTFChars(env, jpathname, pathname);

    return (jint) fd;
}

/*
 * Class:     dev_codex_java_wrapper_runtime_FileControl
 * Method:    openat
 * Signature: (ILjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_wrapper_runtime_FileControl_openat(JNIEnv *env, jclass clazz, jint jdirfd, jstring jpathname, jint jflags) {
    int fd = -1;

    const char* pathname = (*env)->GetStringUTFChars(env, jpathname, NULL);
    fd = openat((int) jdirfd, pathname, (int) jflags);
    (*env)->ReleaseStringUTFChars(env, jpathname, pathname);

    return (jint) fd;
}

/*
 * Class:     dev_codex_java_wrapper_runtime_UnixStandard
 * Method:    close
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_wrapper_runtime_UnixStandard_close(JNIEnv *env, jclass clazz, jint jfd) {
    return (jint) close((int) jfd);
}

/*
 * Class:     dev_codex_java_wrapper_runtime_Strings
 * Method:    strerror
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_dev_codex_java_wrapper_runtime_Strings_strerror(JNIEnv *env, jclass clazz) {
    char *msg = strerror(errno);

    int len = strlen(msg);
    jbyteArray msg_array = (*env)->NewByteArray(env, len);
    (*env)->SetByteArrayRegion(env, msg_array, 0, len, (const jbyte *) msg);

    return msg_array;
}

#ifdef __cplusplus
}
#endif