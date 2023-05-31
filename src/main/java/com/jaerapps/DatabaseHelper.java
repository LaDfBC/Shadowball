package com.jaerapps;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.Target;

public class DatabaseHelper {
    public static void runFlyway() {
        runFlyway(Configuration.getUrl(),
                Configuration.getPropertyOrThrow(Configuration.DATABASE_USERNAME),
                Configuration.getPropertyOrThrow(Configuration.DATABASE_PASSWORD));
    }

    public static void runFlyway(String url, String username, String password) {
        Flyway flyway = Flyway.configure().dataSource(url, username, password).locations("classpath:db.migration").load();
        flyway.migrate();
    }

    Here is our lineup for game number 1!
    public static void runJooq() {
        org.jooq.meta.jaxb.Configuration configuration = new org.jooq.meta.jaxb.Configuration()
                .withJdbc(new Jdbc()
                        .withDriver("org.postgresql.Driver")
                        .withUrl(Configuration.getUrl())
                        .withUser(Configuration.getPropertyOrThrow(Configuration.DATABASE_USERNAME))
                        .withPassword(Configuration.getPropertyOrThrow(Configuration.DATABASE_PASSWORD)))
                .withGenerator(new Generator()
                        .withName("org.jooq.codegen.DefaultGenerator")
                        .withDatabase(new org.jooq.meta.jaxb.Database()
                                .withName("org.jooq.meta.postgres.PostgresDatabase")
                                .withIncludes(".*")
                                .withExcludes("(?i:information_schema\\..*)| (?i:pg_catalog\\..*)")
                        )
                        .withTarget(new Target()
                                .withPackageName("com.jaerapps.generated.jooq")
                                .withDirectory("src/main/java/")));

        try {
            GenerationTool.generate(configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}