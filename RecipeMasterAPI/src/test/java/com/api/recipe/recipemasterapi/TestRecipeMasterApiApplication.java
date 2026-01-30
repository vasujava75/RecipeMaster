package com.api.recipe.recipemasterapi;

import org.springframework.boot.SpringApplication;

public class TestRecipeMasterApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(RecipeMasterApiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
