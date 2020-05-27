package controller;

import model.log.Log;
import view.panels.LogPane;

import java.util.ArrayList;

public class LogController {

    private ArrayList<Log> logs = new ArrayList<>();
    private LogPane logPane;

    public LogController(){

    }

    public void addLog(Log log){
        logs.add(log);
        logPane.updateLogTable();
    }

    public ArrayList<Log> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<Log> logs) {
        this.logs = logs;
    }

    public LogPane getLogPane() {
        return logPane;
    }

    public void setLogPane(LogPane logPane) {
        this.logPane = logPane;
    }
}
