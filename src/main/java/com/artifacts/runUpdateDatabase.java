package com.artifacts;

import com.artifacts.game.engine.launcher.Login;
import static com.artifacts.repository.GetLogsParser.getLogsParser;

public class runUpdateDatabase {
    public static void main(String[] args) {
        Login.login();
        getLogsParser();
    }
}