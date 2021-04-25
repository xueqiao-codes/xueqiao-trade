/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.quot.comb.thriftapi;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradeHostingQuotComb {

  public interface Iface {

    /**
     * 同步订阅主题
     * 
     * @param platformArgs
     * @param syncRequest
     */
    public void syncCombTopics(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, SyncCombTopicsRequest syncRequest) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException;

  }

  public interface AsyncIface {

    public void syncCombTopics(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, SyncCombTopicsRequest syncRequest, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException;

  }

  public static class Client extends org.apache.thrift.TServiceClient implements Iface {
    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(org.apache.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public void syncCombTopics(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, SyncCombTopicsRequest syncRequest) throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException
    {
      send_syncCombTopics(platformArgs, syncRequest);
      recv_syncCombTopics();
    }

    public void send_syncCombTopics(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, SyncCombTopicsRequest syncRequest) throws org.apache.thrift.TException
    {
      syncCombTopics_args args = new syncCombTopics_args();
      args.setPlatformArgs(platformArgs);
      args.setSyncRequest(syncRequest);
      sendBase("syncCombTopics", args);
    }

    public void recv_syncCombTopics() throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException
    {
      syncCombTopics_result result = new syncCombTopics_result();
      receiveBase(result, "syncCombTopics");
      if (result.err != null) {
        throw result.err;
      }
      return;
    }

  }
  public static class AsyncClient extends org.apache.thrift.async.TAsyncClient implements AsyncIface {
    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
      private org.apache.thrift.async.TAsyncClientManager clientManager;
      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void syncCombTopics(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, SyncCombTopicsRequest syncRequest, org.apache.thrift.async.AsyncMethodCallback resultHandler) throws org.apache.thrift.TException {
      checkReady();
      syncCombTopics_call method_call = new syncCombTopics_call(platformArgs, syncRequest, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class syncCombTopics_call extends org.apache.thrift.async.TAsyncMethodCall <syncCombTopics_call>{
      private org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs;
      private SyncCombTopicsRequest syncRequest;
      public syncCombTopics_call(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs, SyncCombTopicsRequest syncRequest, org.apache.thrift.async.AsyncMethodCallback resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.platformArgs = platformArgs;
        this.syncRequest = syncRequest;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("syncCombTopics", org.apache.thrift.protocol.TMessageType.CALL, 0));
        syncCombTopics_args args = new syncCombTopics_args();
        args.setPlatformArgs(platformArgs);
        args.setSyncRequest(syncRequest);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public void getResult() throws org.soldier.platform.svr_platform.comm.ErrorInfo, org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        (new Client(prot)).recv_syncCombTopics();
      }
    }

  }

  public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor {
    public Processor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
    }

    protected Processor(I iface, Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> getProcessMap(Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      processMap.put("syncCombTopics", new syncCombTopics());
      return processMap;
    }

    public static class syncCombTopics<I extends Iface> extends org.apache.thrift.ProcessFunction<I, syncCombTopics_args> {
      public syncCombTopics() {
        super("syncCombTopics");
      }

      public syncCombTopics_args getEmptyArgsInstance() {
        return new syncCombTopics_args();
      }

      protected boolean isOneway() {
        return false;
      }

      public syncCombTopics_result getResult(I iface, syncCombTopics_args args) throws org.apache.thrift.TException {
        syncCombTopics_result result = new syncCombTopics_result();
        try {
          iface.syncCombTopics(args.platformArgs, args.syncRequest);
        } catch (org.soldier.platform.svr_platform.comm.ErrorInfo err) {
          result.err = err;
        }
        return result;
      }
    }

  }

  public static class AsyncProcessor<I extends AsyncIface> extends org.apache.thrift.TBaseAsyncProcessor<I> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncProcessor.class.getName());
    public AsyncProcessor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.AsyncProcessFunction<I, ? extends org.apache.thrift.TBase, ?>>()));
    }

    protected AsyncProcessor(I iface, Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends AsyncIface> Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase,?>> getProcessMap(Map<String,  org.apache.thrift.AsyncProcessFunction<I, ? extends  org.apache.thrift.TBase, ?>> processMap) {
      processMap.put("syncCombTopics", new syncCombTopics());
      return processMap;
    }

    public static class syncCombTopics<I extends AsyncIface> extends org.apache.thrift.AsyncProcessFunction<I, syncCombTopics_args, Void> {
      public syncCombTopics() {
        super("syncCombTopics");
      }

      public syncCombTopics_args getEmptyArgsInstance() {
        return new syncCombTopics_args();
      }

      public AsyncMethodCallback<Void> getResultHandler(final AsyncFrameBuffer fb, final int seqid) {
        final org.apache.thrift.AsyncProcessFunction fcall = this;
        return new AsyncMethodCallback<Void>() { 
          public void onComplete(Void o) {
            syncCombTopics_result result = new syncCombTopics_result();
            try {
              fcall.sendResponse(fb,result, org.apache.thrift.protocol.TMessageType.REPLY,seqid);
              return;
            } catch (Exception e) {
              LOGGER.error("Exception writing to internal frame buffer", e);
            }
            fb.close();
          }
          public void onError(Exception e) {
            byte msgType = org.apache.thrift.protocol.TMessageType.REPLY;
            org.apache.thrift.TBase msg;
            syncCombTopics_result result = new syncCombTopics_result();
            if (e instanceof org.soldier.platform.svr_platform.comm.ErrorInfo) {
                        result.err = (org.soldier.platform.svr_platform.comm.ErrorInfo) e;
                        result.setErrIsSet(true);
                        msg = result;
            }
             else 
            {
              msgType = org.apache.thrift.protocol.TMessageType.EXCEPTION;
              msg = (org.apache.thrift.TBase)new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
            }
            try {
              fcall.sendResponse(fb,msg,msgType,seqid);
              return;
            } catch (Exception ex) {
              LOGGER.error("Exception writing to internal frame buffer", ex);
            }
            fb.close();
          }
        };
      }

      protected boolean isOneway() {
        return false;
      }

      public void start(I iface, syncCombTopics_args args, org.apache.thrift.async.AsyncMethodCallback<Void> resultHandler) throws TException {
        iface.syncCombTopics(args.platformArgs, args.syncRequest,resultHandler);
      }
    }

  }

  public static class syncCombTopics_args implements org.apache.thrift.TBase<syncCombTopics_args, syncCombTopics_args._Fields>, java.io.Serializable, Cloneable, Comparable<syncCombTopics_args>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("syncCombTopics_args");

    private static final org.apache.thrift.protocol.TField PLATFORM_ARGS_FIELD_DESC = new org.apache.thrift.protocol.TField("platformArgs", org.apache.thrift.protocol.TType.STRUCT, (short)1);
    private static final org.apache.thrift.protocol.TField SYNC_REQUEST_FIELD_DESC = new org.apache.thrift.protocol.TField("syncRequest", org.apache.thrift.protocol.TType.STRUCT, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new syncCombTopics_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new syncCombTopics_argsTupleSchemeFactory());
    }

    public org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs; // required
    public SyncCombTopicsRequest syncRequest; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      PLATFORM_ARGS((short)1, "platformArgs"),
      SYNC_REQUEST((short)2, "syncRequest");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // PLATFORM_ARGS
            return PLATFORM_ARGS;
          case 2: // SYNC_REQUEST
            return SYNC_REQUEST;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.PLATFORM_ARGS, new org.apache.thrift.meta_data.FieldMetaData("platformArgs", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, org.soldier.platform.svr_platform.comm.PlatformArgs.class)));
      tmpMap.put(_Fields.SYNC_REQUEST, new org.apache.thrift.meta_data.FieldMetaData("syncRequest", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, SyncCombTopicsRequest.class)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(syncCombTopics_args.class, metaDataMap);
    }

    public syncCombTopics_args() {
    }

    public syncCombTopics_args(
      org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs,
      SyncCombTopicsRequest syncRequest)
    {
      this();
      this.platformArgs = platformArgs;
      this.syncRequest = syncRequest;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public syncCombTopics_args(syncCombTopics_args other) {
      if (other.isSetPlatformArgs()) {
        this.platformArgs = new org.soldier.platform.svr_platform.comm.PlatformArgs(other.platformArgs);
      }
      if (other.isSetSyncRequest()) {
        this.syncRequest = new SyncCombTopicsRequest(other.syncRequest);
      }
    }

    public syncCombTopics_args deepCopy() {
      return new syncCombTopics_args(this);
    }

    @Override
    public void clear() {
      this.platformArgs = null;
      this.syncRequest = null;
    }

    public org.soldier.platform.svr_platform.comm.PlatformArgs getPlatformArgs() {
      return this.platformArgs;
    }

    public syncCombTopics_args setPlatformArgs(org.soldier.platform.svr_platform.comm.PlatformArgs platformArgs) {
      this.platformArgs = platformArgs;
      return this;
    }

    public void unsetPlatformArgs() {
      this.platformArgs = null;
    }

    /** Returns true if field platformArgs is set (has been assigned a value) and false otherwise */
    public boolean isSetPlatformArgs() {
      return this.platformArgs != null;
    }

    public void setPlatformArgsIsSet(boolean value) {
      if (!value) {
        this.platformArgs = null;
      }
    }

    public SyncCombTopicsRequest getSyncRequest() {
      return this.syncRequest;
    }

    public syncCombTopics_args setSyncRequest(SyncCombTopicsRequest syncRequest) {
      this.syncRequest = syncRequest;
      return this;
    }

    public void unsetSyncRequest() {
      this.syncRequest = null;
    }

    /** Returns true if field syncRequest is set (has been assigned a value) and false otherwise */
    public boolean isSetSyncRequest() {
      return this.syncRequest != null;
    }

    public void setSyncRequestIsSet(boolean value) {
      if (!value) {
        this.syncRequest = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case PLATFORM_ARGS:
        if (value == null) {
          unsetPlatformArgs();
        } else {
          setPlatformArgs((org.soldier.platform.svr_platform.comm.PlatformArgs)value);
        }
        break;

      case SYNC_REQUEST:
        if (value == null) {
          unsetSyncRequest();
        } else {
          setSyncRequest((SyncCombTopicsRequest)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case PLATFORM_ARGS:
        return getPlatformArgs();

      case SYNC_REQUEST:
        return getSyncRequest();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case PLATFORM_ARGS:
        return isSetPlatformArgs();
      case SYNC_REQUEST:
        return isSetSyncRequest();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof syncCombTopics_args)
        return this.equals((syncCombTopics_args)that);
      return false;
    }

    public boolean equals(syncCombTopics_args that) {
      if (that == null)
        return false;

      boolean this_present_platformArgs = true && this.isSetPlatformArgs();
      boolean that_present_platformArgs = true && that.isSetPlatformArgs();
      if (this_present_platformArgs || that_present_platformArgs) {
        if (!(this_present_platformArgs && that_present_platformArgs))
          return false;
        if (!this.platformArgs.equals(that.platformArgs))
          return false;
      }

      boolean this_present_syncRequest = true && this.isSetSyncRequest();
      boolean that_present_syncRequest = true && that.isSetSyncRequest();
      if (this_present_syncRequest || that_present_syncRequest) {
        if (!(this_present_syncRequest && that_present_syncRequest))
          return false;
        if (!this.syncRequest.equals(that.syncRequest))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    @Override
    public int compareTo(syncCombTopics_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetPlatformArgs()).compareTo(other.isSetPlatformArgs());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetPlatformArgs()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.platformArgs, other.platformArgs);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetSyncRequest()).compareTo(other.isSetSyncRequest());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSyncRequest()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.syncRequest, other.syncRequest);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("syncCombTopics_args(");
      boolean first = true;

      sb.append("platformArgs:");
      if (this.platformArgs == null) {
        sb.append("null");
      } else {
        sb.append(this.platformArgs);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("syncRequest:");
      if (this.syncRequest == null) {
        sb.append("null");
      } else {
        sb.append(this.syncRequest);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
      if (platformArgs != null) {
        platformArgs.validate();
      }
      if (syncRequest != null) {
        syncRequest.validate();
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class syncCombTopics_argsStandardSchemeFactory implements SchemeFactory {
      public syncCombTopics_argsStandardScheme getScheme() {
        return new syncCombTopics_argsStandardScheme();
      }
    }

    private static class syncCombTopics_argsStandardScheme extends StandardScheme<syncCombTopics_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, syncCombTopics_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // PLATFORM_ARGS
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.platformArgs = new org.soldier.platform.svr_platform.comm.PlatformArgs();
                struct.platformArgs.read(iprot);
                struct.setPlatformArgsIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // SYNC_REQUEST
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.syncRequest = new SyncCombTopicsRequest();
                struct.syncRequest.read(iprot);
                struct.setSyncRequestIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, syncCombTopics_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.platformArgs != null) {
          oprot.writeFieldBegin(PLATFORM_ARGS_FIELD_DESC);
          struct.platformArgs.write(oprot);
          oprot.writeFieldEnd();
        }
        if (struct.syncRequest != null) {
          oprot.writeFieldBegin(SYNC_REQUEST_FIELD_DESC);
          struct.syncRequest.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class syncCombTopics_argsTupleSchemeFactory implements SchemeFactory {
      public syncCombTopics_argsTupleScheme getScheme() {
        return new syncCombTopics_argsTupleScheme();
      }
    }

    private static class syncCombTopics_argsTupleScheme extends TupleScheme<syncCombTopics_args> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, syncCombTopics_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetPlatformArgs()) {
          optionals.set(0);
        }
        if (struct.isSetSyncRequest()) {
          optionals.set(1);
        }
        oprot.writeBitSet(optionals, 2);
        if (struct.isSetPlatformArgs()) {
          struct.platformArgs.write(oprot);
        }
        if (struct.isSetSyncRequest()) {
          struct.syncRequest.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, syncCombTopics_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(2);
        if (incoming.get(0)) {
          struct.platformArgs = new org.soldier.platform.svr_platform.comm.PlatformArgs();
          struct.platformArgs.read(iprot);
          struct.setPlatformArgsIsSet(true);
        }
        if (incoming.get(1)) {
          struct.syncRequest = new SyncCombTopicsRequest();
          struct.syncRequest.read(iprot);
          struct.setSyncRequestIsSet(true);
        }
      }
    }

  }

  public static class syncCombTopics_result implements org.apache.thrift.TBase<syncCombTopics_result, syncCombTopics_result._Fields>, java.io.Serializable, Cloneable, Comparable<syncCombTopics_result>   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("syncCombTopics_result");

    private static final org.apache.thrift.protocol.TField ERR_FIELD_DESC = new org.apache.thrift.protocol.TField("err", org.apache.thrift.protocol.TType.STRUCT, (short)1);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new syncCombTopics_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new syncCombTopics_resultTupleSchemeFactory());
    }

    public org.soldier.platform.svr_platform.comm.ErrorInfo err; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      ERR((short)1, "err");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // ERR
            return ERR;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.ERR, new org.apache.thrift.meta_data.FieldMetaData("err", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(syncCombTopics_result.class, metaDataMap);
    }

    public syncCombTopics_result() {
    }

    public syncCombTopics_result(
      org.soldier.platform.svr_platform.comm.ErrorInfo err)
    {
      this();
      this.err = err;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public syncCombTopics_result(syncCombTopics_result other) {
      if (other.isSetErr()) {
        this.err = new org.soldier.platform.svr_platform.comm.ErrorInfo(other.err);
      }
    }

    public syncCombTopics_result deepCopy() {
      return new syncCombTopics_result(this);
    }

    @Override
    public void clear() {
      this.err = null;
    }

    public org.soldier.platform.svr_platform.comm.ErrorInfo getErr() {
      return this.err;
    }

    public syncCombTopics_result setErr(org.soldier.platform.svr_platform.comm.ErrorInfo err) {
      this.err = err;
      return this;
    }

    public void unsetErr() {
      this.err = null;
    }

    /** Returns true if field err is set (has been assigned a value) and false otherwise */
    public boolean isSetErr() {
      return this.err != null;
    }

    public void setErrIsSet(boolean value) {
      if (!value) {
        this.err = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case ERR:
        if (value == null) {
          unsetErr();
        } else {
          setErr((org.soldier.platform.svr_platform.comm.ErrorInfo)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case ERR:
        return getErr();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case ERR:
        return isSetErr();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof syncCombTopics_result)
        return this.equals((syncCombTopics_result)that);
      return false;
    }

    public boolean equals(syncCombTopics_result that) {
      if (that == null)
        return false;

      boolean this_present_err = true && this.isSetErr();
      boolean that_present_err = true && that.isSetErr();
      if (this_present_err || that_present_err) {
        if (!(this_present_err && that_present_err))
          return false;
        if (!this.err.equals(that.err))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    @Override
    public int compareTo(syncCombTopics_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = Boolean.valueOf(isSetErr()).compareTo(other.isSetErr());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetErr()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.err, other.err);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("syncCombTopics_result(");
      boolean first = true;

      sb.append("err:");
      if (this.err == null) {
        sb.append("null");
      } else {
        sb.append(this.err);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class syncCombTopics_resultStandardSchemeFactory implements SchemeFactory {
      public syncCombTopics_resultStandardScheme getScheme() {
        return new syncCombTopics_resultStandardScheme();
      }
    }

    private static class syncCombTopics_resultStandardScheme extends StandardScheme<syncCombTopics_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, syncCombTopics_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // ERR
              if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                struct.err = new org.soldier.platform.svr_platform.comm.ErrorInfo();
                struct.err.read(iprot);
                struct.setErrIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, syncCombTopics_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.err != null) {
          oprot.writeFieldBegin(ERR_FIELD_DESC);
          struct.err.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class syncCombTopics_resultTupleSchemeFactory implements SchemeFactory {
      public syncCombTopics_resultTupleScheme getScheme() {
        return new syncCombTopics_resultTupleScheme();
      }
    }

    private static class syncCombTopics_resultTupleScheme extends TupleScheme<syncCombTopics_result> {

      @Override
      public void write(org.apache.thrift.protocol.TProtocol prot, syncCombTopics_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetErr()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetErr()) {
          struct.err.write(oprot);
        }
      }

      @Override
      public void read(org.apache.thrift.protocol.TProtocol prot, syncCombTopics_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.err = new org.soldier.platform.svr_platform.comm.ErrorInfo();
          struct.err.read(iprot);
          struct.setErrIsSet(true);
        }
      }
    }

  }

}
