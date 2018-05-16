/**
 *  This file is part of TEGA (Tools for Evolutionary and Genetic Analysis)
 *  TEGA website: https://github.com/darioelias/TEGA
 *
 *  Copyright (C) 2018 Dario E. Elias & Eva C. Rueda
 *
 *  TEGA is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  TEGA is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *  
 *  Additional permission under GNU AGPL version 3 section 7
 *  
 *  If you modify TEGA, or any covered work, by linking or combining it with
 *  STRUCTURE, DISTRUCT or CLUMPP (or a modified version of those programs),
 *  the licensors of TEGA grant you additional permission to convey the resulting work.
 */


/**
 * @author Dario E. Elias
 * @version 1.0 
 */


package org.tega.config.liquibase;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.StopWatch;

import org.tega.config.Constants;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;

/**
 * Specific liquibase.integration.spring.SpringLiquibase that will update the database asynchronously.
 * <p>
 *     By default, this asynchronous version only works when using the "dev" profile.<p>
 *     The standard liquibase.integration.spring.SpringLiquibase starts Liquibase in the current thread:
 *     <ul>
 *         <li>This is needed if you want to do some database requests at startup</li>
 *         <li>This ensure that the database is ready when the application starts</li>
 *     </ul>
 *     But as this is a rather slow process, we use this asynchronous version to speed up our start-up time:
 *     <ul>
 *         <li>On a recent MacBook Pro, start-up time is down from 14 seconds to 8 seconds</li>
 *         <li>In production, this can help your application run on platforms like Heroku, where it must start/restart very quickly</li>
 *     </ul>
 */
public class AsyncSpringLiquibase extends SpringLiquibase {

    // named "logger" because there is already a field called "log" in "SpringLiquibase"
    private final Logger logger = LoggerFactory.getLogger(AsyncSpringLiquibase.class);

    @Inject
    @Qualifier("taskExecutor")
    private TaskExecutor taskExecutor;

    @Inject
    private Environment env;

    @Override
    public void afterPropertiesSet() throws LiquibaseException {
        if (!env.acceptsProfiles(Constants.SPRING_PROFILE_NO_LIQUIBASE)) {
            if (env.acceptsProfiles(Constants.SPRING_PROFILE_DEVELOPMENT, Constants.SPRING_PROFILE_HEROKU)) {
                taskExecutor.execute(() -> {
                    try {
                        logger.warn("Starting Liquibase asynchronously, your database might not be ready at startup!");
                        initDb();
                    } catch (LiquibaseException e) {
                        logger.error("Liquibase could not start correctly, your database is NOT ready: {}", e.getMessage(), e);
                    }
                });
            } else {
                logger.debug("Starting Liquibase synchronously");
                initDb();
            }
        } else {
            logger.debug("Liquibase is disabled");
        }
    }

    protected void initDb() throws LiquibaseException {
        StopWatch watch = new StopWatch();
        watch.start();
        super.afterPropertiesSet();
        watch.stop();
        logger.debug("Started Liquibase in {} ms", watch.getTotalTimeMillis());
    }
}
