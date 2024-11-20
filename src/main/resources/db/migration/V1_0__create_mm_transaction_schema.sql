CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS occurrence
(
    occurrence_id uuid DEFAULT uuid_generate_v4() not null,
    occurrence    varchar(100),
    PRIMARY KEY (occurrence_id)
);

CREATE TABLE IF NOT EXISTS transaction
(
    transaction_id                     uuid DEFAULT uuid_generate_v4() not null,
    tenant_id                          varchar(255),
    plaid_transaction_id               VARCHAR(255),
    transaction_type                   VARCHAR(255),
    account_id                         uuid,
    plaid_account_id                   VARCHAR(255),
    budget_id                          uuid,
    pending_transaction_id             VARCHAR(255),
    location                           JSONB,
    payment_meta                       JSONB,
    account_owner                      varchar(255),
    merchant_name                      varchar(255),
    merchant_entity_id                 varchar(255),
    description                        VARCHAR(255),
    amount                             DECIMAL,
    iso_currency_code                  VARCHAR(255),
    unofficial_currency_code           VARCHAR(255),
    transaction_date                   DATE,
    pending                            BOOLEAN,
    logo_url                           text,
    website                            varchar(256),
    check_number                       varchar(255),
    payment_channel                    varchar(255),
    authorized_date                    DATE,
    authorized_datetime                timestamp with time zone,
    datetime                           timestamp with time zone,
    transaction_code                   varchar(255),
    personal_finance_category          JSONB,
    personal_finance_category_icon_url TEXT,
    counterparties                     JSONB,
    PRIMARY KEY (transaction_id)
);

INSERT INTO occurrence(occurrence_id, occurrence)
VALUES ('04be4770-c192-4b36-8716-c0daae946906', 'Specific Date'),
       ('1d181458-2239-4fec-add2-dda484207162', 'End of Month'),
       ('466d1e38-edd3-4d26-8b99-194728008770', 'First of Month'),
       ('090c2f95-fc2c-428a-981c-e563c582e4ba', 'Monday'),
       ('5fcd1994-5803-47cc-b33e-ee3bc20177d4', 'Tuesday'),
       ('bc89715d-627b-4784-84b6-6bd0a09d6308', 'Wednesday'),
       ('6c039c86-6ca3-4957-b338-6df1b18f70ac', 'Thursday'),
       ('6b751df9-2148-449f-82d2-4f758c580524', 'Friday'),
       ('46c82a34-7968-419d-9276-e47cb3ea4e63', 'Saturday'),
       ('0d67d6ef-2c69-4539-ace4-b338255e5865', 'Sunday');
