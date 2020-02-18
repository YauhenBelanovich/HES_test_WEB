package com.gmail.yauhen2012.repository.enums;

public enum CreateTableEnum {
    CREATE_ROLE_TABLE("CREATE TABLE role\n" +
            "(\n" +
            "    name          VARCHAR(16) PRIMARY KEY NOT NULL,\n" +
            "    description      VARCHAR(100) NOT NULL\n" +
            ");"),
    CREATE_USER_TABLE("CREATE TABLE user\n" +
            "(\n" +
            "    id   INT(11) PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
            "    username   VARCHAR(16) NOT NULL,\n" +
            "    password VARCHAR(100) NOT NULL,\n" +
            "    is_active     TINYINT(1),\n" +
            "    createdBy DATETIME DEFAULT NOW(),\n" +
            "    role VARCHAR(40) NOT NULL,\n" +
            "    FOREIGN KEY (role) REFERENCES role (name),\n" +
            "    UNIQUE (username)" +
            ");"),
    CREATE_USER_INFORMATION_TABLE("CREATE TABLE user_information\n" +
            "(\n" +
            "    user_id   INT(11) PRIMARY KEY NOT NULL,\n" +
            "    first_name   VARCHAR(16),\n" +
            "    last_name VARCHAR(16),\n" +
            "    FOREIGN KEY (user_id) REFERENCES user (id)\n" +
            ");");

    private final String query;

    CreateTableEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
