package com.satikey.tools.supervisord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import com.satikey.tools.supervisord.exceptions.SupervisordException;

/**
 * @author Lei Duan(satifanie@gmail.com) .
 */
public class Supervisord {
    private final static Logger LOGGER = LoggerFactory.getLogger(Supervisord.class);

    private final static String DEFAULT_URL = "http://localhost:9001/RPC2";
    private final static String DEFAULT_NAMESPACE = "supervisor";
    private final static String DOT = ".";

    private String api = DEFAULT_URL;
    private String namespace = DEFAULT_NAMESPACE;
    private String proxyURL;
    private int proxyPort;

    private String username;
    private String password;

    private Supervisord() {
    }

    public static Supervisord connect() {
        Supervisord me = new Supervisord();
        return me;

    }

    public static Supervisord connect(String apiUrl) {
        Supervisord me = new Supervisord();
        me.api = apiUrl;
        return me;
    }

    /**
     * this can be got by the api getIdentification(#link getIdentification()); but to save one http
     * request, better to set it static
     *
     * @param namespace the command prefix: default supervisor.
     * @see #getIdentification()
     */
    public Supervisord namespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public Supervisord proxy(String host, int port) {
        this.proxyURL = host;
        this.proxyPort = port;
        return this;
    }

    public Supervisord auth(String username, String
            password) {
        this.username = username;
        this.password = password;
        return this;
    }

    /**
     * Return the version of the RPC API used by supervisord
     *
     * @return string version version id
     */
    public String getAPIVersion()
            throws SupervisordException {
        return (String) new SimpleXMLRPC().call(buildFullMethodCall(Constants._GET_API_VERSION));
    }

    /**
     * Return the version of the supervisor package in use by supervisord
     *
     * @return string version version id
     */
    public String getSupervisorVersion()
            throws SupervisordException {
        return (String) new SimpleXMLRPC().call(buildFullMethodCall(Constants._GET_SUPERVISOR_VERSION));
    }

    /**
     * Return identifying string of supervisord
     *
     * @return string identifier identifying string
     */
    public String getIdentification()
            throws SupervisordException {
        return (String) new SimpleXMLRPC().call(buildFullMethodCall(Constants._GET_IDENTIFICATION));
    }

    /**
     * {'statecode': 1, 'statename': 'RUNNING'} Return current state of supervisord as a struct
     *
     * @return struct A struct with keys int statecode, string statename
     */
    public Map<String, Object> getState()
            throws SupervisordException {
        return (Map<String, Object>) new SimpleXMLRPC().call(buildFullMethodCall(Constants._GET_STATE));
    }

    /**
     * Return the PID of supervisord
     *
     * @return int PID
     */
    public int getPID()
            throws SupervisordException {
        return (Integer) new SimpleXMLRPC().call(buildFullMethodCall(Constants._GET_PID));
    }

    /**
     * Read length bytes from the main log starting at offset
     *
     * @param offset offset to start reading from
     * @param length number of bytes to read from the log.
     * @return string result Bytes of log* (#link http://supervisord.org/api.html#supervisor.rpcinterface.SupervisorNamespaceRPCInterface.readLog)
     */
    public String readLog(int offset, int length)
            throws SupervisordException {
        if (offset < 0) offset = 0;
        if (length < 0) length = 0;
        return (String) new SimpleXMLRPC().call(buildFullMethodCall(Constants._READ_LOG), offset, length);
    }

    /**
     * Clear the main log.
     *
     * @return boolean result always returns True unless error
     */
    public boolean clearLog()
            throws SupervisordException {
        return (Boolean) new SimpleXMLRPC().call(buildFullMethodCall(Constants._CLEAR_LOG));
    }

    /**
     * Shut down the supervisor process
     *
     * @return boolean result always returns True unless error
     */
    public boolean shutdown()
            throws SupervisordException {
        return (Boolean) new SimpleXMLRPC().call(buildFullMethodCall(Constants._SHUTDOWN));
    }

    /**
     * Restart the supervisor process
     *
     * @return boolean result always return True unless error
     */
    public boolean restart()
            throws SupervisordException {
        return (Boolean) new SimpleXMLRPC().call(buildFullMethodCall(Constants._RESTART));
    }


    /**
     * Get info about a process named name
     *
     * @param processName name The name of the process (or ‘group:name’) @return struct result A
     *                    structure containing data about the process
     * @return string {http://supervisord.org/api.html#supervisor.rpcinterface.SupervisorNamespaceRPCInterface.getProcessInfo}
     */
    public Object getProcessInfo(String processName)
            throws SupervisordException {
        return (Object) new SimpleXMLRPC().call(buildFullMethodCall(Constants._GET_PROCESS_INFO), processName);
    }

    /**
     * Get info about all processes
     *
     * @return array result An array of process status results
     */
    public Object[] getAllProcessInfo()
            throws SupervisordException {
        return (Object[]) new SimpleXMLRPC().call(buildFullMethodCall(Constants._GET_ALL_PROCESS_INFO));
    }

    /**
     * Start a process
     *
     * @param processName name Process name (or group:name, or group:*)
     * @param waitToStart wait Wait for process to be fully started
     * @return boolean result Always true unless error
     */
    public boolean startProcess(String processName, boolean waitToStart)
            throws SupervisordException {
        return (Boolean) new SimpleXMLRPC().call(buildFullMethodCall(Constants._START_PROCESS), processName, waitToStart);
    }

    /**
     * Start all processes listed in the configuration file
     *
     * @param waitToStart wait Wait for each process to be fully started
     * @return array result An array of process status info structs
     */
    public Object[] startAllProcesses(boolean waitToStart)
            throws SupervisordException {
        return (Object[]) new SimpleXMLRPC().call(buildFullMethodCall(Constants._START_ALL_PROCESSES), waitToStart);
    }

    /**
     * Start all processes in the group named ‘name’
     *
     * @param groupName   name The group name
     * @param waitToStart wait Wait for each process to be fully started
     * @return array result An array of process status info structs
     */
    public Object[] startProcessGroup(String groupName, boolean waitToStart)
            throws SupervisordException {
        return (Object[]) new SimpleXMLRPC().call(buildFullMethodCall(Constants._START_PROCESSES_GROUP), groupName, waitToStart);
    }

    /**
     * Stop a process named by name
     *
     * @param processName name The name of the process to stop (or ‘group:name’)
     * @param waitToStop  wait Wait for the process to be fully stopped
     * @return boolean result Always return True unless error
     */
    public boolean stopProcess(String processName, boolean waitToStop)
            throws SupervisordException {
        return (Boolean) new SimpleXMLRPC().call(buildFullMethodCall(Constants._STOP_PROCESS), processName, waitToStop);
    }

    /**
     * Stop all processes in the process group named ‘name’
     *
     * @param groupName  name The group name
     * @param waitToStop wait Wait for each process to be fully stopped
     * @return array result An array of process status info structs
     */
    public Object stopProcessGroup(String groupName, boolean waitToStop)
            throws SupervisordException {
        return new SimpleXMLRPC().call(buildFullMethodCall(Constants._STOP_PROCESSES_GROUP), groupName, waitToStop);
    }

    /**
     * Stop all processes in the process list
     *
     * @param waitToStop wait Wait for each process to be fully stopped
     * @return array result An array of process status info structs
     */
    public Object[] stopAllProcesses(boolean waitToStop)
            throws SupervisordException {
        return (Object[]) new SimpleXMLRPC().call(buildFullMethodCall(Constants._STOP_ALL_PROCESSES), waitToStop);
    }

    /**
     * Send an arbitrary UNIX signal to the process named by name
     *
     * @param processName name Name of the process to signal (or ‘group:name’)
     * @param signal      signal Signal to send, as name (‘HUP’) or number (‘1’)
     */
    public boolean signalProcess(String processName, Signal signal)
            throws SupervisordException {
        return (Boolean) new SimpleXMLRPC().call(buildFullMethodCall(Constants._SIGNAL_PROCESS), processName, signal.name());
    }

    /**
     * Send a signal to all processes in the group named ‘name’
     *
     * @param groupName name The group name
     * @param signal    signal Signal to send, as name (‘HUP’) or number (‘1’)
     * @return array
     */
    public Boolean[] signalProcessGroup(String groupName, Signal signal)
            throws SupervisordException {
        return (Boolean[]) new SimpleXMLRPC().call(buildFullMethodCall(Constants._SIGNAL_PROCESSES_GROUP), groupName, signal.name());
    }


    /**
     * Send a signal to all processes in the process list
     *
     * @param signal signal Signal to send, as name (‘HUP’) or number (‘1’)
     * @return array An array of process status info structs
     */
    public Boolean[] signalAllProcesses(Signal signal)
            throws SupervisordException {
        return (Boolean[]) new SimpleXMLRPC().call(buildFullMethodCall(Constants._SIGNAL_ALL_PROCESSES), signal.name());
    }

    /**
     * Send a string of chars to the stdin of the process name. If non-7-bit data is sent (unicode),
     * it is encoded to utf-8 before being sent to the process’ stdin. If chars is not a string or
     * is not unicode, raise INCORRECT_PARAMETERS. If the process is not running, raise NOT_RUNNING.
     * If the process’ stdin cannot accept input (e.g. it was closed by the child process), raise
     * NO_FILE.
     *
     * @param processName name The process name to send to (or ‘group:name’)
     * @param chars       The character data to send to the process
     * @return boolean result Always return True unless error
     */
    //TODO
    public boolean sendProcessStdin(String processName, String chars) {
        return true;
    }


    /**
     * Send an event that will be received by event listener subprocesses subscribing to the
     * RemoteCommunicationEvent.
     *
     * @param type type String for the “type” key in the event header
     * @param data Data for the event body
     * @return boolean Always return True unless error
     */
    //TODO
    public boolean sendRemoteCommEvent(String type, String data) {
        return true;
    }

    /**
     * Reload configuration
     *
     * @return boolean result always return True unless error
     */
    //TODO
    public boolean reloadConfig() {
        return true;
    }


    /**
     * Update the config for a running process from config file.
     *
     * @param name name name of process group to add
     * @return boolean result true if successful
     */
    //TODO
    public boolean addProcessGroup(String name) {
        return true;
    }


    /**
     * Remove a stopped process from the active configuration.
     *
     * @param name name of process group to remove
     * @return boolean result Indicates whether the removal was successful
     */
    //TODO
    public boolean removeProcessGroup(String name) {
        return true;
    }

    ////////////////////

    /**
     * Read length bytes from name’s stdout log starting at offset
     *
     * @param name   the name of the process (or ‘group:name’)
     * @param offset offset to start reading from.
     * @param length number of bytes to read from the log.
     * @return string result Bytes of log
     */
    //TODO
    public boolean readProcessStdoutLog(String name, int offset, int length) {
        return true;
    }

    /**
     * Read length bytes from name’s stderr log starting at offset
     *
     * @param name   the name of the process (or ‘group:name’)
     * @param offset offset to start reading from.
     * @param length number of bytes to read from the log.
     * @return string result Bytes of log
     */
    //TODO
    public boolean readProcessStderrLog(String name, int offset, int length) {
        return true;
    }

    /**
     * Provides a more efficient way to tail the (stdout) log than readProcessStdoutLog(). Use
     * readProcessStdoutLog() to read chunks and tailProcessStdoutLog() to tail.
     *
     * Requests (length) bytes from the (name)’s log, starting at (offset). If the total log size is
     * greater than (offset + length), the overflow flag is set and the (offset) is automatically
     * increased to position the buffer at the end of the log. If less than (length) bytes are
     * available, the maximum number of available bytes will be returned. (offset) returned is
     * always the last offset in the log +1.
     *
     * @param name   the name of the process (or ‘group:name’)
     * @param offset offset to start reading from
     * @param length maximum number of bytes to return
     * @return array result [string bytes, int offset, bool overflow]
     */
    //TODO
    public boolean tailProcessStdoutLog(String name, int offset, int length) {
        return true;
    }

    /**
     * Provides a more efficient way to tail the (stderr) log than readProcessStderrLog(). Use
     * readProcessStderrLog() to read chunks and tailProcessStderrLog() to tail.
     *
     * Requests (length) bytes from the (name)’s log, starting at (offset). If the total log size is
     * greater than (offset + length), the overflow flag is set and the (offset) is automatically
     * increased to position the buffer at the end of the log. If less than (length) bytes are
     * available, the maximum number of available bytes will be returned. (offset) returned is
     * always the last offset in the log +1.
     *
     * @param name   the name of the process (or ‘group:name’)
     * @param offset offset to start reading from
     * @param length maximum number of bytes to return
     * @return array result [string bytes, int offset, bool overflow]
     */
    //TODO
    public boolean tailProcessStderrLog(String name, int offset, int length) {
        return true;
    }

    /**
     * Clear the stdout and stderr logs for the named process and reopen them.
     *
     * @param name The name of the process (or ‘group:name’)
     * @return result Always True unless error
     */
    //TODO
    public boolean clearProcessLogs(String name) {
        return true;
    }

    /**
     * Clear all process log files
     */
    //TODO
    public boolean clearAllProcessLogs() {
        return true;
    }

    /////
    public Object[] listMethods()
            throws SupervisordException {
        return (Object[]) new SimpleXMLRPC().call(Constants.SYSTEM_LIST_METHODS);
    }

    public String methodHelp(String method)
            throws SupervisordException {
        return (String) new SimpleXMLRPC().call(Constants.SYSTEM_METHOD_HELP, buildFullMethodCall(method));
    }

    public Object[] methodSignature(String method)
            throws SupervisordException {
        return (Object[]) new SimpleXMLRPC().call(Constants.SYSTEM_METHOD_SIGNATURE);
    }

    //TODO
    void multicall(Object[] calls) {
    }

    ////

    /**
     * build a full method call with namespace prefix
     *
     * @param method method name
     * @return rpc
     */
    private String buildFullMethodCall(final String method) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(namespace).append(DOT).append(method).toString();
    }

    private class SimpleXMLRPC {
        private static final String PARAMS_S = "<params>";
        private static final String PARAMS_E = "</params>";
        private static final String PARAM_S = "<param>";
        private static final String PARAM_E = "</param>";
        private static final String VALUE_S = "<value>";
        private static final String VALUE_E = "</value>";
        private static final String METHOD_CALL_S = "<methodCall>";
        private static final String METHOD_CALL_E = "</methodCall>";
        private static final String METHOD_NAME_S = "<methodName>";
        private static final String METHOD_NAME_E = "</methodName>";
        private static final String INT_S = "<int>";
        private static final String INT_E = "</int>";
        private static final String STRING_S = "<string>";
        private static final String STRING_E = "</string>";

        Object call(String methodName, Object... args)
                throws SupervisordException {
            return post(buildCall(methodName, args));
        }

        private Object post(String xml)
                throws SupervisordException {
            Object result = null;
            StringBuilder response = new StringBuilder();
            HttpURLConnection connection = null;
            try {
                URL url = new URL(api);
                URLConnection uc = null;
                //if proxy
                if (proxyURL != null) {
                    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyURL,
                            proxyPort));
                    uc = url.openConnection(proxy);
                } else {
                    uc = url.openConnection();
                }


                connection = (HttpURLConnection) uc;
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                if (username != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(username).append(":").append(password);
                    String auth = new String(Base64.encode(sb.toString().getBytes(), Base64.DEFAULT));
                    String base64Encoded = "Basic " + auth;
                    //JAVA BUGS
                    base64Encoded = base64Encoded.replaceAll("\n", "");
                    connection.setRequestProperty("Authorization", base64Encoded);
                }

                OutputStream out = connection.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
                writer.write(xml);
                writer.flush();
                out.close();

                return new ResponseParser().parse(connection.getInputStream());
            } catch (IOException e) {
                LOGGER.error("HTTP ERR:{}", e.getMessage(), e.getCause());
            } finally {

                if (connection != null)
                    connection.disconnect();

            }
            return null;
        }

        /**
         * Build Request
         *
         * @param method method to call
         * @param params arg list
         * @return xml desc
         */
        private String buildCall(String method, Object... params) {
            StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\"?>\n")
                    .append(METHOD_CALL_S)
                    .append(METHOD_NAME_S).append(method).append(METHOD_NAME_E);
            if (params != null && params.length > 0) {
                sb.append(PARAMS_S);
                for (Object arg : params) {
                    sb
                            .append(PARAM_S)
                            .append(VALUE_S);
                    if (arg instanceof Integer) {
                        sb.append(INT_S).append(arg).append(INT_E);

                    } else {
                        sb.append(STRING_S).append(arg).append(STRING_E);
                    }
                    sb
                            .append(VALUE_E)
                            .append(PARAM_E);
                }
                sb.append(PARAMS_E);
            }
            sb.append(METHOD_CALL_E);
            return sb.toString();
        }
    }
}
