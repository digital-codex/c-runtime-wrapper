#include <dev_codex_java_runtime_unix_FileControl.h>
#include <dev_codex_java_runtime_unix_IOControl.h>
#include <dev_codex_java_runtime_unix_NativeRuntimeWrapper.h>
#include <dev_codex_java_runtime_unix_StandardIO.h>
#include <dev_codex_java_runtime_unix_StandardLibrary.h>
#include <dev_codex_java_runtime_unix_Strings.h>
#include <dev_codex_java_runtime_unix_UnixStandard.h>

#include <errno.h>
#include <sys/ioctl.h>
#include <sys/stat.h>
#include <sys/types.h>

#include <linux/if.h>

#include <fcntl.h>
#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#ifdef __cplusplus
extern "C" {
#endif

static jclass long_clazz;

static jmethodID valueOf;
static jmethodID longValue;

/*
 * Class:     dev_codex_java_runtime_unix_FileControl
 * Method:    open
 * Signature: (Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_runtime_unix_FileControl_open(JNIEnv *env, jclass clazz, jstring j_pathname, jint j_flags) {
    int fd = -1;

    const char* pathname = (*env)->GetStringUTFChars(env, j_pathname, NULL);
    fd = open(pathname, (int) j_flags);
    (*env)->ReleaseStringUTFChars(env, j_pathname, pathname);

    return (jint) fd;
}

/*
 * Class:     dev_codex_java_runtime_unix_FileControl
 * Method:    openat
 * Signature: (ILjava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_runtime_unix_FileControl_openat(JNIEnv *env, jclass clazz, jint j_dirfd, jstring j_pathname, jint j_flags) {
    int fd = -1;

    const char* pathname = (*env)->GetStringUTFChars(env, j_pathname, NULL);
    fd = openat((int) j_dirfd, pathname, (int) j_flags);
    (*env)->ReleaseStringUTFChars(env, j_pathname, pathname);

    return (jint) fd;
}

/*
 * Class:     dev_codex_java_runtime_unix_IOControl
 * Method:    ioctl
 * Signature: (IJLjava/lang/Long;)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_runtime_unix_IOControl_ioctl(JNIEnv *env, jclass clazz, jint j_fd, jlong j_code, jobject j_request) {
    return (jint) ioctl((int) j_fd, (unsigned long) j_code, (struct ifreq*) (*env)->CallObjectMethod(env, j_request, longValue));
}

/*
 * Class:     dev_codex_java_runtime_unix_StandardIO
 * Method:    fclose
 * Signature: (Ljava/lang/Long;)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_runtime_unix_StandardIO_fclose(JNIEnv *env, jclass clazz, jobject j_stream) {
    return (jint) fclose((FILE*) (*env)->CallObjectMethod(env, j_stream, longValue));
}

/*
 * Class:     dev_codex_java_runtime_unix_StandardIO
 * Method:    fopen
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_dev_codex_java_runtime_unix_StandardIO_fopen(JNIEnv *env, jclass clazz, jstring j_pathname, jstring j_mode) {
    FILE* fp = NULL;

    const char* pathname = (*env)->GetStringUTFChars(env, j_pathname, NULL);
    const char* mode = (*env)->GetStringUTFChars(env, j_mode, NULL);
    fp = fopen(pathname, mode);
    (*env)->ReleaseStringUTFChars(env, j_pathname, pathname);
    (*env)->ReleaseStringUTFChars(env, j_mode, mode);

    return (*env)->CallStaticObjectMethod(env, long_clazz, valueOf, (jlong) fp);
}

/*
 * Class:     dev_codex_java_runtime_unix_StandardIO
 * Method:    fdopen
 * Signature: (ILjava/lang/String;)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_dev_codex_java_runtime_unix_StandardIO_fdopen(JNIEnv *env, jclass clazz, jint j_fd, jstring j_mode) {
    FILE* fp = NULL;

    const char* mode = (*env)->GetStringUTFChars(env, j_mode, NULL);
    fp = fdopen((int) j_fd, mode);
    (*env)->ReleaseStringUTFChars(env, j_mode, mode);

    return (*env)->CallStaticObjectMethod(env, long_clazz, valueOf, (jlong) fp);
}

/*
 * Class:     dev_codex_java_runtime_unix_StandardIO
 * Method:    freopen
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_dev_codex_java_runtime_unix_StandardIO_freopen(JNIEnv *env, jclass clazz, jstring j_pathname, jstring j_mode, jobject j_stream) {
    FILE* fp = NULL;

    const char* pathname = (*env)->GetStringUTFChars(env, j_pathname, NULL);
    const char* mode = (*env)->GetStringUTFChars(env, j_mode, NULL);

    fp = freopen(pathname, mode, (FILE*) (*env)->CallObjectMethod(env, j_stream, longValue));
    (*env)->ReleaseStringUTFChars(env, j_pathname, pathname);
    (*env)->ReleaseStringUTFChars(env, j_mode, mode);

    return (*env)->CallStaticObjectMethod(env, long_clazz, valueOf, (jlong) fp);
}

/*
 * Class:     dev_codex_java_runtime_unix_StandardIO
 * Method:    fread
 * Signature: ([BJJLjava/lang/Long;)J
 */
JNIEXPORT jlong JNICALL Java_dev_codex_java_runtime_unix_StandardIO_fread(JNIEnv *env, jclass clazz, jbyteArray j_ptr, jlong j_size, jlong j_nmemb, jobject j_stream) {
    int len = (int) (*env)->GetArrayLength(env, (jarray) j_ptr);
    char ptr[len];
    memset(ptr, 0, sizeof(char) * len);

    size_t n = fread(ptr, (size_t) j_size, (size_t) j_nmemb, (FILE*) (*env)->CallObjectMethod(env, j_stream, longValue));
    (*env)->SetByteArrayRegion(env, j_ptr, 0, (jsize) (n * ((size_t) j_size)), (const jbyte*) ptr);
    return (jlong) n;
}

/*
 * Class:     dev_codex_java_runtime_unix_StandardIO
 * Method:    fwrite
 * Signature: ([BJJLjava/lang/Long;)J
 */
JNIEXPORT jlong JNICALL Java_dev_codex_java_runtime_unix_StandardIO_fwrite(JNIEnv *env, jclass clazz, jbyteArray j_ptr, jlong j_size, jlong j_nmemb, jobject j_stream) {
    int len = (int) (*env)->GetArrayLength(env, (jarray) j_ptr);
    char ptr[len];
    memset(ptr, 0, sizeof(char) * len);

    (*env)->GetByteArrayRegion(env, j_ptr, 0, len, (jbyte*) ptr);
    return (jlong) fwrite(ptr, (size_t) j_size, (size_t) j_nmemb, (FILE*) (*env)->CallObjectMethod(env, j_stream, longValue));
}

/*
 * Class:     dev_codex_java_runtime_unix_StandardIO
 * Method:    fseek
 * Signature: (Ljava/lang/Long;JI)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_runtime_unix_StandardIO_fseek(JNIEnv *env, jclass clazz, jobject j_stream, jlong j_offset, jint j_whence) {
    return (jint) fseek((FILE*) (*env)->CallObjectMethod(env, j_stream, longValue), (long) j_offset, (int) j_whence);
}

/*
 * Class:     dev_codex_java_runtime_unix_StandardIO
 * Method:    ftell
 * Signature: (Ljava/lang/Long;)J
 */
JNIEXPORT jlong JNICALL Java_dev_codex_java_runtime_unix_StandardIO_ftell(JNIEnv *env, jclass clazz, jobject j_stream) {
    return (jlong) ftell((FILE*) (*env)->CallObjectMethod(env, j_stream, longValue));
}

/*
 * Class:     dev_codex_java_runtime_unix_StandardIO
 * Method:    fgetpos
 * Signature: (Ljava/lang/Long;Ljava/lang/Long;)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_runtime_unix_StandardIO_fgetpos(JNIEnv *env, jclass clazz, jobject j_stream, jobject j_pos) {
    return (jint) fgetpos((FILE*) (*env)->CallObjectMethod(env, j_stream, longValue), (fpos_t*) (*env)->CallObjectMethod(env, j_pos, longValue));
}

/*
 * Class:     dev_codex_java_runtime_unix_StandardIO
 * Method:    fsetpos
 * Signature: (Ljava/lang/Long;Ljava/lang/Long;)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_runtime_unix_StandardIO_fsetpos(JNIEnv *env, jclass clazz, jobject j_stream, jobject j_pos) {
    return (jint) fgetpos((FILE*) (*env)->CallObjectMethod(env, j_stream, longValue), (fpos_t*) (*env)->CallObjectMethod(env, j_pos, longValue));
}

static jclass int_clazz;
static jclass int_primitive_clazz;
static jclass double_clazz;
static jclass double_primitive_clazz;
static jclass char_clazz;
static jclass char_primitive_clazz;
static jclass string_clazz;

/*
 * Class:     dev_codex_java_runtime_unix_StandardIO
 * Method:    println
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_runtime_unix_StandardIO_println(JNIEnv *env, jclass clazz, jstring j_string) {
    const char* string = (*env)->GetStringUTFChars(env, j_string, NULL);
    int ret = printf("%s\n", string);
    (*env)->ReleaseStringUTFChars(env, j_string, string);
    return (jint) ret;
*/
}

/*
 * Class:     dev_codex_java_runtime_unix_StandardLibrary
 * Method:    malloc
 * Signature: (J)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_dev_codex_java_runtime_unix_StandardLibrary_malloc(JNIEnv *env, jclass clazz, jlong j_size) {
    return (*env)->CallStaticObjectMethod(env, long_clazz, valueOf, (jlong) malloc((size_t) j_size));
}

/*
 * Class:     dev_codex_java_runtime_unix_StandardLibrary
 * Method:    free
 * Signature: (Ljava/lang/Long;)V
 */
JNIEXPORT void JNICALL Java_dev_codex_java_runtime_unix_StandardLibrary_free(JNIEnv *env, jclass clazz, jobject j_ptr) {
    free((void*) (*env)->CallObjectMethod(env, j_ptr, longValue));
}

/*
 * Class:     dev_codex_java_runtime_unix_StandardLibrary
 * Method:    calloc
 * Signature: (JJ)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_dev_codex_java_runtime_unix_StandardLibrary_calloc(JNIEnv *env, jclass clazz, jlong j_nmemb, jlong j_size) {
    return (*env)->CallStaticObjectMethod(env, long_clazz, valueOf, (jlong) calloc((size_t) j_nmemb, (size_t) j_size));
}

/*
 * Class:     dev_codex_java_runtime_unix_StandardLibrary
 * Method:    realloc
 * Signature: (Ljava/lang/Long;J)Ljava/lang/Long;
 */
JNIEXPORT jobject JNICALL Java_dev_codex_java_runtime_unix_StandardLibrary_realloc(JNIEnv *env, jclass clazz, jobject j_ptr, jlong j_size) {
    return (*env)->CallStaticObjectMethod(env, long_clazz, valueOf, (jlong) realloc((void*) (*env)->CallObjectMethod(env, j_ptr, longValue), (size_t) j_size));
}

/*
 * Class:     dev_codex_java_runtime_unix_Strings
 * Method:    strerror
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_dev_codex_java_runtime_unix_Strings_strerror(JNIEnv *env, jclass clazz) {
    char* msg = strerror(errno);

    int len = strlen(msg);
    jbyteArray msg_array = (*env)->NewByteArray(env, len);
    (*env)->SetByteArrayRegion(env, msg_array, 0, len, (const jbyte*) msg);

    return msg_array;
}

/*
 * Class:     dev_codex_java_runtime_unix_UnixStandard
 * Method:    close
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_dev_codex_java_runtime_unix_UnixStandard_close(JNIEnv *env, jclass clazz, jint j_fd) {
    return (jint) close((int) j_fd);
}

/*
 * Class:     dev_codex_java_runtime_unix_UnixStandard
 * Method:    read
 * Signature: (I[BJ)J
 */
JNIEXPORT jlong JNICALL Java_dev_codex_java_runtime_unix_UnixStandard_read(JNIEnv *env, jclass clazz, jint j_fd, jbyteArray j_buf, jlong j_count) {
    int len = (int) (*env)->GetArrayLength(env, (jarray) j_buf);
    char buf[len];
    memset(buf, 0, sizeof(char) * len);

    ssize_t n = read((int) j_fd, buf, (size_t) j_count);
    (*env)->SetByteArrayRegion(env, j_buf, 0, (jsize) j_count, (const jbyte*) buf);
    return (jlong) n;
}

/*
 * Class:     dev_codex_java_runtime_unix_UnixStandard
 * Method:    write
 * Signature: (I[BJ)J
 */
JNIEXPORT jlong JNICALL Java_dev_codex_java_runtime_unix_UnixStandard_write(JNIEnv *env, jclass clazz, jint j_fd, jbyteArray j_buf, jlong j_count) {
    int len = (int) (*env)->GetArrayLength(env, (jarray) j_buf);
    char buf[len];
    memset(buf, 0, sizeof(char) * len);

    (*env)->GetByteArrayRegion(env, j_buf, 0, len, (jbyte*) buf);
    return (jlong) write((int) j_fd, buf, (size_t) j_count);
}

/*
 * Class:     dev_codex_java_runtime_unix_NativeRuntimeWrapper
 * Method:    initialize
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_dev_codex_java_runtime_unix_NativeRuntimeWrapper_initialize(JNIEnv *env, jclass clazz) {
    long_clazz = (*env)->FindClass(env, "java/lang/Long");
    valueOf = (*env)->GetStaticMethodID(env, long_clazz, "valueOf", "(J)Ljava/lang/Long;");
    longValue = (*env)->GetMethodID(env, long_clazz, "longValue", "()J");
}

#ifdef __cplusplus
}
#endif