package club.throwable.ch5;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/12 22:13
 */
@Component
public class QuartzCommandLineRunner implements CommandLineRunner {

    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(String... args) throws Exception {
        JobDetail job = JobBuilder.newJob(SimpleJob.class).storeDurably().withIdentity(JobKey.jobKey("SimpleJob")).build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInSeconds(30))
                .forJob(job).build();
        scheduler.scheduleJob(job, trigger);
    }
}
