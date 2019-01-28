package de.claudioaltamura.docker.springboot.kafka.healthcheck;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.DescribeClusterOptions;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.config.ConfigResource.Type;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.Status;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.util.Assert;

/**
 * HealthIndicator for Kafka cluster.
 *
 * @author Juan Rada
 * @see <a href="https://github.com/spring-projects/spring-boot/blob/v2.0.0.RC2/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/kafka/KafkaHealthIndicator.java">KafkaHealthIndicator</a>
 */
public class KafkaHealthIndicator extends AbstractHealthIndicator {

    static final String REPLICATION_PROPERTY = "transaction.state.log.replication.factor";

    private final KafkaAdmin kafkaAdmin;

    private final DescribeClusterOptions describeOptions;

    /**
     * Create a new {@link KafkaHealthIndicator} instance.
     *
     * @param kafkaAdmin the kafka admin
     * @param requestTimeout the request timeout in milliseconds
     */
    public KafkaHealthIndicator(KafkaAdmin kafkaAdmin, long requestTimeout) {
        Assert.notNull(kafkaAdmin, "KafkaAdmin must not be null");
        this.kafkaAdmin = kafkaAdmin;
        this.describeOptions = new DescribeClusterOptions()
                .timeoutMs((int) requestTimeout);
    }

    @Override
    protected void doHealthCheck(Builder builder) throws Exception {
        try (AdminClient adminClient = AdminClient.create(this.kafkaAdmin.getConfig())) {
            DescribeClusterResult result = adminClient.describeCluster(
                    this.describeOptions);
            String brokerId = result.controller().get().idString();
            int replicationFactor = getReplicationFactor(brokerId, adminClient);
            int nodes = result.nodes().get().size();
            Status status = nodes >= replicationFactor ? Status.UP : Status.DOWN;
            builder.status(status)
                    .withDetail("clusterId", result.clusterId().get())
                    .withDetail("brokerId", brokerId)
                    .withDetail("nodes", nodes);
        }
    }

    private int getReplicationFactor(String brokerId,
            AdminClient adminClient) throws ExecutionException, InterruptedException {
        ConfigResource configResource = new ConfigResource(Type.BROKER, brokerId);
        Map<ConfigResource, Config> kafkaConfig = adminClient
                .describeConfigs(Collections.singletonList(configResource)).all().get();
        Config brokerConfig = kafkaConfig.get(configResource);
        return Integer.parseInt(brokerConfig.get(REPLICATION_PROPERTY).value());
    }

}