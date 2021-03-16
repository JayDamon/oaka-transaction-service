DROP TABLE IF EXISTS transaction_sub_category;
DROP TABLE IF EXISTS recurring_transaction;
DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS transaction_category;
DROP TABLE IF EXISTS transaction_type;
DROP TABLE IF EXISTS occurrence;

CREATE TABLE transaction_sub_category
(
    transaction_sub_category_id BIGINT NOT NULL AUTO_INCREMENT,
    sub_category_name           varchar(100),
    PRIMARY KEY (transaction_sub_category_id)
);

CREATE TABLE occurrence
(
    occurrence_id INT NOT NULL AUTO_INCREMENT,
    occurrence    varchar(100),
    PRIMARY KEY (occurrence_id)
);

CREATE TABLE transaction_category
(
    transaction_category_id     BIGINT NOT NULL AUTO_INCREMENT,
    category_name               VARCHAR(100),
    transaction_sub_category_id BIGINT,
    PRIMARY KEY (transaction_category_id)
);

CREATE TABLE transaction_type
(
    transaction_type_id INT NOT NULL AUTO_INCREMENT,
    transaction_type    varchar(100),
    PRIMARY KEY (transaction_type_id)
);

CREATE TABLE recurring_transaction
(
    recurring_transaction_id INT NOT NULL AUTO_INCREMENT,
    name                     varchar(100),
    account_id               BIGINT,
    budget_category_id       INT,
    transaction_category_id  BIGINT,
    frequency_type_id        INT,
    frequency                INT,
    occurrence_id            INT,
    transaction_type_id      INT,
    start_date               DATETIME,
    end_date                 DATETIME,
    amount                   DECIMAL,
    PRIMARY KEY (recurring_transaction_id),
    FOREIGN KEY (occurrence_id) REFERENCES occurrence (occurrence_id),
    FOREIGN KEY (transaction_category_id) REFERENCES transaction_category (transaction_category_id),
    FOREIGN KEY (transaction_type_id) REFERENCES transaction_type (transaction_type_id)
);

CREATE TABLE transaction
(
    transaction_id           BIGINT NOT NULL AUTO_INCREMENT,
    account_id               BIGINT,
    budget_id                BIGINT,
    transaction_category_id  BIGINT,
    transaction_type_id      INT,
    recurring_transaction_id BIGINT,
    transaction_date         DATETIME,
    description              VARCHAR(255),
    amount                   DECIMAL,
    PRIMARY KEY (transaction_id)
);

