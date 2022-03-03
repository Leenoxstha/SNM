package com.snm.asignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Call {
    private Line fromLine;
    private Line toLine;
    private final long duration;
    private long admittedTimestamp;
    private final long arrivalTimestamp;

    public long getArrivalTimestamp() {
        return arrivalTimestamp;
    }
    public long getDuration() {
        return duration;
    }
    public Line getFromLine(){
        return fromLine;
    }
    public void setFromLine(Line fromLine) {
        this.fromLine = fromLine;
    }
    public Line getToLine(){
        return this.toLine;
    }
    public void setToLine(Line toLine){
        this.toLine = toLine;
    }
    public void setAdmittedTimestamp(long admittedTimestamp){
        this.admittedTimestamp = admittedTimestamp;
    }
    public long getAdmittedTimestamp(){
        return this.admittedTimestamp;
    }

    Call(Line[] line, int maxTalkTime){
        Random random = new Random();
        do{
            this.fromLine = line[random.nextInt(line.length)];
            this.toLine = line[random.nextInt(line.length)];
        }while(this.toLine.equals(this.fromLine));

        Date date = new Date();
        arrivalTimestamp = date.getTime();
        this.duration = 1000*(long) random.nextInt(maxTalkTime);
        this.admittedTimestamp = 0;
    }

    public void connect(ArrayList<Call> callsOnProgressList, ArrayList<Call> delayedCallList){
        if(fromLine.getState().equals("busy") || toLine.getState().equals("busy") || callsOnProgressList.size() >= TelephoneDelayCallSimulation.numOfLinks){
            System.out.println("--> Added to delay: " + this);
            delayedCallList.add(this);
            TelephoneDelayCallSimulation.block++;
        }else{
            this.admittedTimestamp = System.currentTimeMillis();
            callsOnProgressList.add(this);
            System.out.println("--> Added to Progress: " + this);
            fromLine.setState("busy");
            toLine.setState("busy");
        }
    }

    public String toString(){
        return "Call From " + this.fromLine.getId()+ " To "+this.toLine.getId() + " Arrived at " + new Date(this.arrivalTimestamp) + " for duration " + (duration/1000) + ((this.admittedTimestamp != 0)? " Admitted at " + (new Date(this.arrivalTimestamp)): " Not Admitted");
    }
}
