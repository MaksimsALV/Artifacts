/*
package com.artifacts.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/threads")
public class ThreadWorkerAPI {
    @GetMapping
    public Map<String, String> list() {
        Map<String, String> result = new HashMap<>();
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            // putIfAbsent keeps the first value if the name repeats
            result.putIfAbsent(thread.getName(), thread.getState().name());
        }
        return result;
    }

    @PostMapping("/{name}/start")
    public ResponseEntity<String> start(@PathVariable String name) {
        Runnable r = ThreadRegistry.ThreadTasks.get(name);
        if (r == null) return ResponseEntity.notFound().build();
        new Thread(r, name).start();
        return ResponseEntity.ok("Started: " + name);
    }

    @PostMapping("/{name}/interrupt")
    public ResponseEntity<String> interrupt(@PathVariable String name) {
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (name.equals(thread.getName())) {
                thread.interrupt();
                return ResponseEntity.accepted().body("Interrupted: " + name);
            }
        }
        return ResponseEntity.notFound().build();
    }
}


 */