package com.eden.orchid.impl.tasks;

import com.caseyjbrooks.clog.Clog;
import com.eden.orchid.api.OrchidContext;
import com.eden.orchid.api.tasks.OrchidTask;
import com.eden.orchid.utilities.OrchidUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ListTasksTask extends OrchidTask {

    private OrchidContext context;

    @Inject
    public ListTasksTask(OrchidContext context) {
        this.context = context;
    }

    @Override
    public String getName() {
        return "listTasks";
    }

    @Override
    public String getDescription() {
        return "Display all available Tasks that can be runTask.";
    }

    @Override
    public void run() {
        Clog.logger(null, "" +
                "#{ $0 | fg('magenta') }[OrchidTask Name]#{$0 |reset}" +
                "\n------------------------------------------------------------------------------------" +
                "\n------------------------------------------------------------------------------------");

        for(OrchidTask task : OrchidUtils.resolveSet(context, OrchidTask.class)) {
            Clog.logger(null, "#{ $0 | fg('magenta') }[#{$1}]#{$0 |reset}",  task.getName() );

            for(String line : OrchidUtils.wrapString(task.getDescription(), 80)) {
                Clog.logger(null, "    " + line);
            }
            Clog.logger(null, "    --------------------------------------------------------------------------------");
            Clog.logger(null, "");
        }
    }
}
