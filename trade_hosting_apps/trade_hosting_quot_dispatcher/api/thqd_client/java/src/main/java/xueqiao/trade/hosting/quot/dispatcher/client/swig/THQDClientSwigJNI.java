/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package xueqiao.trade.hosting.quot.dispatcher.client.swig;

public class THQDClientSwigJNI {
  public final static native void delete_ITHQDCallback(long jarg1);
  public final static native void ITHQDCallback_onReceivedItemData(long jarg1, ITHQDCallback jarg1_, String jarg2, String jarg3, byte[] jarg4);
  public final static native long new_ITHQDCallback();
  public final static native void ITHQDCallback_director_connect(ITHQDCallback obj, long cptr, boolean mem_own, boolean weak_global);
  public final static native void ITHQDCallback_change_ownership(ITHQDCallback obj, long cptr, boolean take_or_release);
  public final static native void THQDSwig_init(String jarg1, long jarg2, ITHQDCallback jarg2_);
  public final static native void THQDSwig_addTopic(String jarg1, String jarg2);
  public final static native void THQDSwig_removeTopic(String jarg1, String jarg2);
  public final static native void THQDSwig_destroy();
  public final static native long new_THQDSwig();
  public final static native void delete_THQDSwig(long jarg1);

  public static void SwigDirector_ITHQDCallback_onReceivedItemData(ITHQDCallback jself, String platform, String contract_symbol, byte[] data) {
    jself.onReceivedItemData(platform, contract_symbol, data);
  }

  private final static native void swig_module_init();
  static {
    swig_module_init();
  }
}
