package com.snm.asignment;

import java.util.*;

public class TelephoneDelayCallSimulation {
    private static final int numOfLines = 8;
    static final int numOfLinks = 3;
    public static  int completed, block;
    private static final int maxTalkTime = 30;

    static CallsOnProgressList callsOnProgressList;
    public static ArrayList<Call> delayedCallList;

    static Line[] line;
    static Timer timer = new Timer();

    public TelephoneDelayCallSimulation() {}

    static class GenerateRandomCall extends TimerTask{
        @Override
         public void run(){
            Call call;
            do{
                System.out.println("\nCompleted: "+completed+"  Block: "+block);
                call = new Call(line, maxTalkTime);
            }while(call.getFromLine().getState().equals("busy"));

            System.out.println("\n\n\n Current TIME: " + new Date());
            System.out.println("\n--> A call came from: "+ call.getFromLine().getId()+" To " + call.getToLine().getId()+ " at "+ new Date(call.getAdmittedTimestamp())+ " which has duration of "+call.getDuration()/1000);

            call.connect(callsOnProgressList, delayedCallList);
            printLists();

            int delay = (1 + new Random().nextInt(10)) * 1000;
            timer.schedule(new GenerateRandomCall(),delay);

        }
    }
    static void printLists(){
        System.out.println("\nIn progress List: ");
        for (Call c: callsOnProgressList){
            System.out.println(c);
        }
        System.out.println("\nDelayed List: ");
        for(Call c: delayedCallList){
            System.out.println(c);
        }
    }

    public static void main(String[] args) {
        line = new Line[numOfLines];
        for(int i = 0; i< numOfLines ; i++){
            line[i] = new Line();
        }

        callsOnProgressList = new CallsOnProgressList();

        delayedCallList = new ArrayList<Call>();
        new GenerateRandomCall().run();
    }

}
