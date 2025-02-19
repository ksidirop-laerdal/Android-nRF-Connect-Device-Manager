package io.runtime.mcumgr.managers;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import io.runtime.mcumgr.McuManager;
import io.runtime.mcumgr.McuMgrCallback;
import io.runtime.mcumgr.McuMgrTransport;
import io.runtime.mcumgr.exception.McuMgrException;
import io.runtime.mcumgr.response.McuMgrResponse;
import io.runtime.mcumgr.response.config.McuMgrConfigReadResponse;

@SuppressWarnings({"unused", "WeakerAccess"})
public class CrashManager extends McuManager {

    /**
     * Crash test command ID.
     */
    private final static int ID_CRASH_TEST = 0;

    /**
     * Type of crash to test on the device.
     */
    public enum Test {
        DIV_0("div0"),
        JUMP_0("jump0"),
        REF_0("ref0"),
        ASSERT("assert"),
        WDOG("wdog");

        private final String value;

        Test(@NotNull String value) {
            this.value = value;
        }

        @NotNull
        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * Construct a McuManager instance.
     *
     * @param transporter the transporter to use to send commands.
     */
    public CrashManager(@NotNull McuMgrTransport transporter) {
        super(McuManager.GROUP_CRASH, transporter);
    }

    /**
     * Trigger a crash test.
     * @param test The type of crash test.
     * @return The response
     * @throws McuMgrException on failure.
     */
    @NotNull
    public McuMgrResponse test(@NotNull Test test) throws McuMgrException {
        HashMap<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("t", test.toString());
        return send(OP_WRITE, ID_CRASH_TEST, payloadMap, SHORT_TIMEOUT, McuMgrConfigReadResponse.class);
    }

    /**
     * Trigger a crash test.
     * @param test The type of crash test.
     * @param callback The response callback.
     */
    public void test(@NotNull Test test, @NotNull McuMgrCallback<McuMgrResponse> callback) {
        HashMap<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("t", test.toString());
        send(OP_WRITE, ID_CRASH_TEST, payloadMap, SHORT_TIMEOUT, McuMgrResponse.class, callback);
    }
}
