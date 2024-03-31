#include <dev_codex_java_runtime_unix_InterfaceRequest.h>

#include <linux/if.h>

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifdef __cplusplus
extern "C" {
#endif

static jclass memory_address_clazz;
static jclass long_clazz;

static jfieldID address_field;

static jmethodID value;
static jmethodID longValue;

static struct ifreq* address(JNIEnv* env, jobject thiz) {
    jobject address_object = (*env)->GetObjectField(env, thiz, address_field);
    jobject long_object = (*env)->CallObjectMethod(env, address_object, value);
    return (struct ifreq*) (*env)->CallObjectMethod(env, long_object, longValue);
}

/*
 * Class:     dev_codex_java_runtime_unix_InterfaceRequest
 * Method:    getName
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_dev_codex_java_runtime_unix_InterfaceRequest_getName(JNIEnv *env, jobject thiz) {
    int len = strlen((address(env, thiz))->ifr_name);
    char name[len + 1];
    memset(name, 0, sizeof(char) * len);
    strncpy(name, (address(env, thiz))->ifr_name, len + 1);

    jbyteArray name_array = (*env)->NewByteArray(env, len + 1);
    (*env)->SetByteArrayRegion(env, name_array, 0, len, (const jbyte*) name);
    return name_array;
}

/*
 * Class:     dev_codex_java_runtime_unix_InterfaceRequest
 * Method:    updateName
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_dev_codex_java_runtime_unix_InterfaceRequest_updateName(JNIEnv *env, jobject thiz, jbyteArray j_name) {
    int len = (int) (*env)->GetArrayLength(env, (jarray) j_name);
    char* name = (char*) (*env)->GetByteArrayElements(env, j_name, NULL);
    strncpy((address(env, thiz))->ifr_name, name, len);
    (*env)->ReleaseByteArrayElements(env, j_name, (jbyte*) name, JNI_ABORT);
}

/*
 * Class:     dev_codex_java_runtime_unix_InterfaceRequest
 * Method:    getFlags
 * Signature: ()S
 */
JNIEXPORT jshort JNICALL Java_dev_codex_java_runtime_unix_InterfaceRequest_getFlags(JNIEnv *env, jobject thiz) {
    return (jshort) (address(env, thiz))->ifr_flags;
}

/*
 * Class:     dev_codex_java_runtime_unix_InterfaceRequest
 * Method:    updateFlags
 * Signature: (S)V
 */
JNIEXPORT void JNICALL Java_dev_codex_java_runtime_unix_InterfaceRequest_updateFlags(JNIEnv *env, jobject thiz, jshort j_flags) {
    (address(env, thiz))->ifr_flags = (short) j_flags;
}

/*
 * Class:     dev_codex_java_runtime_unix_InterfaceRequest
 * Method:    initialize
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_dev_codex_java_runtime_unix_InterfaceRequest_initialize(JNIEnv *env, jclass clazz) {
    address_field = (*env)->GetFieldID(env, clazz, "address", "Ldev/codex/java/runtime/type/MemoryAddress;");

    memory_address_clazz = (*env)->FindClass(env, "dev/codex/java/runtime/type/MemoryAddress");
    value = (*env)->GetMethodID(env, memory_address_clazz, "value", "()Ljava/lang/Long;");

    long_clazz = (*env)->FindClass(env, "java/lang/Long");
    longValue = (*env)->GetMethodID(env, long_clazz, "longValue", "()J");
}

#ifdef __cplusplus
}
#endif