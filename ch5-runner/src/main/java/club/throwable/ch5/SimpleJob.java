package club.throwable.ch5;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/12 22:26
 */
@Slf4j
@DisallowConcurrentExecution
public class SimpleJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("SimpleJob run...");
    }
}
