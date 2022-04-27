CREATE DATABASE IF NOT EXISTS apidata;

USE apidata;

CREATE TABLE user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)          NOT NULL,
    email      VARCHAR(255)          NOT NULL,
    cpf        VARCHAR(255)          NOT NULL,
    birth_date date                  NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE user
    ADD CONSTRAINT uc_user_cpf UNIQUE (cpf);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

CREATE TABLE vaccine
(
    id                  BIGINT       NOT NULL,
    name                VARCHAR(255) NOT NULL,
    disease_name        VARCHAR(255) NOT NULL,
    disease_description VARCHAR(255) NULL,
    minimum_age         INT          NOT NULL,
    doses_amount        INT          NOT NULL,
    created_at          date         NOT NULL,
    updated_at          date         NULL,
    CONSTRAINT pk_vaccine PRIMARY KEY (id)
);

ALTER TABLE vaccine
    ADD CONSTRAINT uc_vaccine_name UNIQUE (name);

CREATE TABLE apply_vaccine
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    date       date                  NOT NULL,
    user_id    BIGINT                NULL,
    vaccine_id BIGINT                NULL,
    CONSTRAINT pk_applyvaccine PRIMARY KEY (id)
);

ALTER TABLE apply_vaccine
    ADD CONSTRAINT FK_APPLYVACCINE_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE apply_vaccine
    ADD CONSTRAINT FK_APPLYVACCINE_ON_VACCINE FOREIGN KEY (vaccine_id) REFERENCES vaccine (id);