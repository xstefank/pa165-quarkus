package cz.muni.fi.pa165.socialnetwork.init;

import io.quarkus.scheduler.Scheduled;

public class MyDummyScheduler {

    @Scheduled(cron = "0 */15 * * * ?")
    public void print(){
        System.out.println("Wohoo, scheduling in Quarkus is easy.");
    }
}

