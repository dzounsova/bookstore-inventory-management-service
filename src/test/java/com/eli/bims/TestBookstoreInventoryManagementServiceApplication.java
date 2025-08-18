package com.eli.bims;

import org.springframework.boot.SpringApplication;

public class TestBookstoreInventoryManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(BookstoreInventoryManagementServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
