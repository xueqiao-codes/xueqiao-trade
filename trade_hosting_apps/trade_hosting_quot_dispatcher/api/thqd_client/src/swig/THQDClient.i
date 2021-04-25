%module(directors="1") THQDClientSwig
%{
#include "thqd_callback.h"
#include "thqd_swig.h"
%}

%include "std_string.i"
%include "typemaps.i"

#ifdef SWIGJAVA

%include "java/various.i"

%typemap(in)     (uint8_t *BYTE, size_t LENGTH) {
    $1 = (uint8_t *) JCALL2(GetByteArrayElements, jenv, $input, 0);
    $2 = (size_t)    JCALL1(GetArrayLength,       jenv, $input);
}
%typemap(jni)    (uint8_t *BYTE, size_t LENGTH) "jbyteArray"
%typemap(jtype)  (uint8_t *BYTE, size_t LENGTH) "byte[]"
%typemap(jstype) (uint8_t *BYTE, size_t LENGTH) "byte[]"
%typemap(javain) (uint8_t *BYTE, size_t LENGTH) "$javainput"

%typemap(jni)    (uint8_t *BYTE, size_t LENGTH) "jbyteArray"

%typemap(in)     (uint8_t *BYTE) {
  if (!$input) {
    SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "array null");
    return $null;
  }
  if (JCALL1(GetArrayLength, jenv, $input) == 0) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIndexOutOfBoundsException, "Array must contain at least 1 element");
    return $null;
  }
  $1 = ($1_ltype) JCALL2(GetByteArrayElements, jenv, $input, 0);

}

%typemap(argout) (uint8_t* BYTE)
{ JCALL3(ReleaseByteArrayElements, jenv, $input, (jbyte *)$1, 0); }

%typemap(jni)    (uint8_t *BYTE) "jbyteArray"
%typemap(jtype)  (uint8_t *BYTE) "byte[]"
%typemap(jstype) (uint8_t *BYTE) "byte[]"
%typemap(javain) (uint8_t *BYTE) "$javainput"

/*
 * Define when to apply the new typemap: if SWIG sees a method signature (also
 * partial signature) it applies the patterns. "BYTE" is the placeholder for
 * the real parameter name.
 */
%apply (uint8_t *BYTE)   { (uint8_t *buffer) };

/*
 * First define the typemap pattern and the lines to insert if this pattern triggers
 *
 * The first typemap defines the actions that move the data from the C++ buffer to
 * a Java byte[]. This is called "input to director java proxy". SWIG inserts
 * these lines before it calls the Java proxy.
 *
 * These typemaps also use the maps defined above for uint8_t* / size_t combination.
 * The standard SWIG library defines only char* / int combination.
 */
%typemap(directorin, descriptor="[B") (uint8_t *BYTE, size_t LENGTH) {
   jbyteArray jb = (jenv)->NewByteArray($2);
   (jenv)->SetByteArrayRegion(jb, 0, $2, (jbyte *)$1);
   $input = jb;
}

/*
 * Perform these actions if the Java method returns. In this case we copy the Java
 * array back to the C++ buffer.
 */
%typemap(directorargout) (uint8_t *BYTE, size_t LENGTH)
%{(jenv)->GetByteArrayRegion($input, 0, $2, (jbyte *)$1); %}

%typemap(javadirectorin) (uint8_t *BYTE, size_t LENGTH) "$jniinput"
/* %typemap(javadirectorout) (uint8_t *BYTE, size_t LENGTH) "$javacall" */

%apply (uint8_t *BYTE, size_t LENGTH)   { (uint8_t* data, size_t size) };

%{
extern "C" jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    return JNI_VERSION_1_4;
}

extern "C" void JNI_OnUnload(JavaVM* vm, void* reserved) {
    ::xueqiao::trade::hosting::quot::dispatcher::client::swig::THQDSwig::destroy();
}
%}

#endif

%feature("director", assumeoverride=1) xueqiao::trade::hosting::quot::dispatcher::client::ITHQDCallback;
%include "../thqd_callback.h"
%include "thqd_swig.h"

