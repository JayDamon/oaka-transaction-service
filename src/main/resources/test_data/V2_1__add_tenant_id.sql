update recurring_transaction SET tenant_id = '5809b48e-b705-4b3e-be9f-16ce0380cb45';

INSERT INTO recurring_transaction (recurring_transaction_id, name, account_id, budget_category_id,
                                   frequency_type_id, frequency, occurrence_id,
                                   transaction_category_id, transaction_type_id, start_date,
                                   end_date, amount, tenant_id)
VALUES (4, 'Plex', 1, 10, 2, 1, 1, 70, 2, '2017-01-04', NULL, 3.99, '7c21bdec-219e-4492-a2fd-e6a2ec5fb8fa');

update transaction SET tenant_id = '5809b48e-b705-4b3e-be9f-16ce0380cb45';

INSERT INTO transaction (account_id, budget_id, transaction_category_id, transaction_type_id,
recurring_transaction_id, transaction_date, description, amount, tenant_id)
VALUES (5, 8, 44, 2, 4, '2017-02-19', '7-ELEVEN', -15.25, '7c21bdec-219e-4492-a2fd-e6a2ec5fb8fa');
