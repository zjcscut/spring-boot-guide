package club.throwable.ch8.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/19 10:39
 */
@ConfigurationProperties(prefix = "mybatis")
@Data
public class MyBatisProperties {

    private String configLocation;
    private String mapperLocations;
    private String mapperPackages;

    private static final ResourcePatternResolver RESOLVER = new PathMatchingResourcePatternResolver();

    /**
     * 转化Mapper映射文件为Resource
     */
    public Resource[] getMapperResourceArray() {
        if (!StringUtils.hasLength(mapperLocations)) {
            return new Resource[0];
        }
        List<Resource> resources = new ArrayList<>();
        String[] locations = StringUtils.commaDelimitedListToStringArray(mapperLocations);
        for (String location : locations) {
            try {
                resources.addAll(Arrays.asList(RESOLVER.getResources(location)));
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return resources.toArray(new Resource[0]);
    }
}
