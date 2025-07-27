#include <jni.h>
#include "NitroNotificationHelpersOnLoad.hpp"

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void*) {
  return margelo::nitro::nitronotificationhelpers::initialize(vm);
}
