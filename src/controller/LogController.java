package controller;

import model.log.Log;
import view.panels.LogPane;

import java.util.ArrayList;

public class LogController {

    private ArrayList<Log> logs = new ArrayList<>();
    private LogPane logPane;

    public LogController(){

    }

    void addLog(Log log){
        logs.add(log);
        logPane.updateLogTable();
    }

    ArrayList<Log> getLogs() {
        return logs;
    }

    void setLogPane(LogPane logPane) {
        this.logPane = logPane;
    }
}
