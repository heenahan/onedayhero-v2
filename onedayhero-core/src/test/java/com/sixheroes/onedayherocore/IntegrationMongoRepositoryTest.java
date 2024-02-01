package com.sixheroes.onedayherocore;

import com.sixheroes.onedayherocore.notification.application.AlarmTemplateReader;
import com.sixheroes.onedayherocore.notification.application.NotificationService;
import com.sixheroes.onedayherocore.notification.mongo.repository.AlarmRepository;
import com.sixheroes.onedayherocore.notification.mongo.repository.AlarmTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@SpringBootTest
@RecordApplicationEvents
@Import(IntegrationMongoRepositoryTest.IntegrationMongoTestConfiguration.class)
public abstract class IntegrationMongoRepositoryTest {

    @Autowired
    protected ApplicationEvents applicationEvents;

    @Autowired
    protected AlarmRepository alarmRepository;

    @Autowired
    protected AlarmTemplateRepository alarmTemplateRepository;

    @Autowired
    protected AlarmTemplateReader alarmTemplateReader;

    @Autowired
    protected NotificationService notificationService;

    @TestConfiguration
    static class IntegrationMongoTestConfiguration {

        @Bean(initMethod = "start", destroyMethod = "stop")
        public MongoDBContainer mongoDBContainer() {
            return new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
        }

        @Bean
        @DependsOn("mongoDBContainer")
        public MongoDatabaseFactory mongoDatabaseFactory(
            final MongoDBContainer mongoDBContainer
        ) {
            return new SimpleMongoClientDatabaseFactory(mongoDBContainer.getReplicaSetUrl());
        }
    }
}