--liquibase formatted sql
--changeset id:1588510956469-1
CREATE TABLE IF NOT EXISTS item (
    id        UUID                    NOT NULL CONSTRAINT PK_item PRIMARY KEY,
    name      VARCHAR UNIQUE          NOT NULL,
    added     DATE                    NOT NULL,
    available BOOLEAN   DEFAULT TRUE  NOT NULL
);