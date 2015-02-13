package com;

import com.test.jasper.GenerateFromJsonJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by Kahfi on 2/13/2015.
 *
 * This class is used to activate the scheduler if you build the class.
 * You can set the parameter time of trigger as you wish
 */
public class App {

    public static void main(String[] args){
        try {
            /*Get default scheduler properties*/
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();

            /*define job for scheduler*/
            JobDetail generateJson = new JobDetail("generate", "generate", GenerateFromJsonJob.class);

            /*define trigger for scheduler*/
            Trigger tGenerateJson = TriggerUtils.makeSecondlyTrigger(10);
            tGenerateJson.setName("generateJson");

            /*schedule job that has been defined*/
            scheduler.scheduleJob(generateJson, tGenerateJson);

        }catch (SchedulerException e) {
            e.printStackTrace();
            System.out.println("There is problem with scheduler");
        }
    }
}
