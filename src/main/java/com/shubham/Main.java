package com.shubham;

import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(com.shubham.Main.class);

    public static void main(String[] args) {
        var app = Javalin.create().start(8080);
        app.post("/user", ctx -> {
                    logger.info(ctx.body());
                    ctx.json(ctx.body());
                }
        );
    }
}