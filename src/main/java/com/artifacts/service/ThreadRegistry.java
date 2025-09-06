/*
package com.artifacts.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadRegistry {
    public static final class ThreadTasks {
        private static final Map<String, Runnable> TASKS = new ConcurrentHashMap<>();
        private ThreadTasks() {}
        public static void register(String name, Runnable task) {
            TASKS.put(name, task);
        }
        public static Runnable get(String name) {
            return TASKS.get(name);
        }
    }
}

 */
