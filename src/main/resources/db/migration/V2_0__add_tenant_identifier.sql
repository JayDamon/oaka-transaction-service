ALTER TABLE recurring_transaction add tenant_id varchar(255);

ALTER TABLE transaction add tenant_id varchar(255);

ALTER TABLE transaction MODIFY COLUMN transaction_date DATE;