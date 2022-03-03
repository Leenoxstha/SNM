package com.snm.asignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CallsOnProgressList extends ArrayList<Call> {
    class CheckEmptyListTask extends TimerTask{
        @Override
        public void run(){
            try {
                if(!TelephoneDelayCallSimulation.delayedCallList.isEmpty()){
                    if(CallsOnProgressList.this.size() < TelephoneDelayCallSimulation.numOfLinks){
                        for(Call call : TelephoneDelayCallSimulation.delayedCallList){
                            if(call.getFromLine().getState().equals("idle") && call.getToLine().getState().equals("idle")){
                                TelephoneDelayCallSimulation.delayedCallList.remove(call);
                                call.setAdmittedTimestamp(System.currentTimeMillis());
                                CallsOnProgressList.this.add(call);
                                call.getFromLine().setState("busy");
                                call.getToLine().setState("busy");
                                System.out.println("\n--> Transfer form delay to progress: " + call);
                            }
                        }
                    }
                }

                if(!CallsOnProgressList.this.isEmpty()){
                    for(Call call : CallsOnProgressList.this){
                        if(call.getAdmittedTimestamp() != 0 && call.getAdmittedTimestamp() + call.getDuration() <= System.currentTimeMillis()){
                            CallsOnProgressList.this.remove(call);
                            call.getFromLine().setState("idle");
                            call.getToLine().setState("idle");
                            System.out.println("\n--> Call terminated " + call);
                            TelephoneDelayCallSimulation.completed++;
                        }
                    }
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public  CallsOnProgressList(){
        super();
        Timer timer = new Timer();
        timer.schedule(new CheckEmptyListTask(), 0, 100);
    }
}
