package io.pivotal.pal.tracker.timesheets;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestOperations;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ProjectClient {

    private final Logger logger = LoggerFactory.getLogger(ProjectClient.class);

    private final RestOperations restOperations;
    private final String endpoint;
    private final ConcurrentMap<Long, ProjectInfo> cache;

    public ProjectClient(RestOperations restOperations, String registrationServerEndpoint) {
        this.restOperations = restOperations;
        this.endpoint = registrationServerEndpoint;
        cache = new ConcurrentHashMap<>();
    }

    @HystrixCommand(commandKey = "ProjectClient",
                    fallbackMethod = "getProjectFromCache")
    public ProjectInfo getProject(long projectId) {
        ProjectInfo info = restOperations.getForObject(endpoint + "/projects/" + projectId, ProjectInfo.class);
        cache.put(projectId, info);
        logger.info("Obtained and cached projectInfo for projectId '{}'", projectId);
        return info;
    }

    @HystrixCommand(commandKey = "ProjectClientCache",
                    threadPoolKey = "ProjectClientCache")
    public ProjectInfo getProjectFromCache(long projectId) {
        logger.info("Fell back to using cached project lookup for projectId '{}'", projectId);
        return cache.get(projectId);
    }
}
