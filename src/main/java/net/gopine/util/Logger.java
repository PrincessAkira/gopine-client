package net.gopine.util;

import net.gopine.GopineClient;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Used to log everything regarding {@link GopineClient}
 * @author MatthewTGM | MatthewTGM#4058
 * @since b1.0
 */
public class Logger {

    /**
     * Logs the content to the console as an `info` type.
     * @param data the data being logged.
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public static void info(Object... data) {
        for (Object out : data) System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [Gopine Info] " + out);
    }

    /**
     * Logs the content to the console as an `error` type.
     * @param data the data being logged.
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public static void error(Object... data) {
        for (Object out : data) System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [Gopine Error] " + out);
    }

    /**
     * Logs the content to the console as a `warn` type.
     * @param data the data being logged.
     * @author MatthewTGM | MatthewTGM#4058
     * @since b1.0
     */
    public static void warn(Object... data) {
        for (Object out : data) System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [Gopine Warn] " + out);
    }

    public static class CustomLogger {

        /**
         * Logs the content to the console as a `warn` type.
         * @param data the data being logged.
         * @author MatthewTGM | MatthewTGM#4058
         * @since b1.0
         */
        public static void info(String suffix, Object... data) {
            for (Object out : data) System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [Gopine " + suffix + "] " + out);
        }

    }

    public static class ModLogger {

        /**
         * Logs the content to the console as a `module info` type.
         * @param data the data being logged.
         * @author MatthewTGM | MatthewTGM#4058
         * @since b1.0
         */
        public static void info(Object... data) {
            for (Object out : data) System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [Gopine Module Info] " + out);
        }

        /**
         * Logs the content to the console as a `module error` type.
         * @param data the data being logged.
         * @author MatthewTGM | MatthewTGM#4058
         * @since b1.0
         */
        public static void error(Object... data) {
            for (Object out : data) System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [Gopine Module Error] " + out);
        }

        /**
         * Logs the content to the console as a `module warn` type.
         * @param data the data being logged.
         * @author MatthewTGM | MatthewTGM#4058
         * @since b1.0
         */
        public static void warn(Object... data) {
            for (Object out : data) System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [Gopine Module Warn] " + out);
        }

    }

    public static class SettingLogger {

        /**
         * Logs the content to the console as a `setting info` type.
         * @param data the data being logged.
         * @author MatthewTGM | MatthewTGM#4058
         * @since b1.0
         */
        public static void info(Object... data) {
            for (Object out : data) System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [Gopine Setting Info] " + out);
        }

        /**
         * Logs the content to the console as a `setting error` type.
         * @param data the data being logged.
         * @author MatthewTGM | MatthewTGM#4058
         * @since b1.0
         */
        public static void error(Object... data) {
            for (Object out : data) System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [Gopine Setting Error] " + out);
        }

        /**
         * Logs the content to the console as a `setting warn` type.
         * @param data the data being logged.
         * @author MatthewTGM | MatthewTGM#4058
         * @since b1.0
         */
        public static void warn(Object... data) {
            for (Object out : data) System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [Gopine Setting Warn] " + out);
        }

    }

}