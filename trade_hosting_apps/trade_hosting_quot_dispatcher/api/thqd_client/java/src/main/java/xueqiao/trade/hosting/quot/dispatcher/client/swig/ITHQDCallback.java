/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package xueqiao.trade.hosting.quot.dispatcher.client.swig;

public class ITHQDCallback {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected ITHQDCallback(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ITHQDCallback obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        THQDClientSwigJNI.delete_ITHQDCallback(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  protected void swigDirectorDisconnect() {
    swigCMemOwn = false;
    delete();
  }

  public void swigReleaseOwnership() {
    swigCMemOwn = false;
    THQDClientSwigJNI.ITHQDCallback_change_ownership(this, swigCPtr, false);
  }

  public void swigTakeOwnership() {
    swigCMemOwn = true;
    THQDClientSwigJNI.ITHQDCallback_change_ownership(this, swigCPtr, true);
  }

  public void onReceivedItemData(String platform, String contract_symbol, byte[] data) {
    THQDClientSwigJNI.ITHQDCallback_onReceivedItemData(swigCPtr, this, platform, contract_symbol, data);
  }

  protected ITHQDCallback() {
    this(THQDClientSwigJNI.new_ITHQDCallback(), true);
    THQDClientSwigJNI.ITHQDCallback_director_connect(this, swigCPtr, swigCMemOwn, true);
  }

}