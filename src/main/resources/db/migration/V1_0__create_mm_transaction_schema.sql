CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS transaction_sub_category
(
    transaction_sub_category_id uuid DEFAULT uuid_generate_v4() not null,
    sub_category_name           varchar(100),
    PRIMARY KEY (transaction_sub_category_id)
);

CREATE TABLE IF NOT EXISTS occurrence
(
    occurrence_id uuid DEFAULT uuid_generate_v4() not null,
    occurrence    varchar(100),
    PRIMARY KEY (occurrence_id)
);

CREATE TABLE IF NOT EXISTS transaction_category
(
    transaction_category_id     uuid DEFAULT uuid_generate_v4() not null,
    category_name               VARCHAR(100),
    transaction_sub_category_id uuid,
    PRIMARY KEY (transaction_category_id)
);

CREATE TABLE IF NOT EXISTS recurring_transaction
(
    recurring_transaction_id uuid DEFAULT uuid_generate_v4() not null,
    name                     varchar(100),
    account_id               uuid not null,
    budget_category_id       uuid,
    transaction_category_id  uuid,
    frequency_type_id        uuid,
    frequency                INTEGER,
    occurrence_id            uuid,
    start_date               timestamp,
    end_date                 timestamp,
    amount                   DECIMAL not null,
    tenant_id                varchar(255) not null,
    PRIMARY KEY (recurring_transaction_id),
    FOREIGN KEY (occurrence_id) REFERENCES occurrence (occurrence_id),
    FOREIGN KEY (transaction_category_id) REFERENCES transaction_category (transaction_category_id)
);

CREATE TABLE IF NOT EXISTS transaction
(
    transaction_id           uuid DEFAULT uuid_generate_v4() not null,
    account_id               uuid,
    budget_id                uuid,
    transaction_category_id  uuid,
    transaction_type_id      uuid,
    recurring_transaction_id uuid,
    transaction_date         DATE,
    description              VARCHAR(255),
    amount                   DECIMAL,
    tenant_id                varchar(255),
    PRIMARY KEY (transaction_id)
);

INSERT INTO occurrence(occurrence_id, occurrence)
VALUES
       ('04be4770-c192-4b36-8716-c0daae946906', 'Specific Date'),
       ('1d181458-2239-4fec-add2-dda484207162', 'End of Month'),
       ('466d1e38-edd3-4d26-8b99-194728008770', 'First of Month'),
       ('090c2f95-fc2c-428a-981c-e563c582e4ba', 'Monday'),
       ('5fcd1994-5803-47cc-b33e-ee3bc20177d4', 'Tuesday'),
       ('bc89715d-627b-4784-84b6-6bd0a09d6308', 'Wednesday'),
       ('6c039c86-6ca3-4957-b338-6df1b18f70ac', 'Thursday'),
       ('6b751df9-2148-449f-82d2-4f758c580524', 'Friday'),
       ('46c82a34-7968-419d-9276-e47cb3ea4e63', 'Saturday'),
       ('0d67d6ef-2c69-4539-ace4-b338255e5865', 'Sunday');

INSERT INTO transaction_sub_category(transaction_sub_category_id, sub_category_name)
VALUES
       ('35af1b26-669e-4413-b21a-dc0db8ff09cc', 'Income'),
       ('3560d2cb-eb01-4e2b-839b-696f2686c16f', 'Housing'),
       ('d07c471c-0f67-472d-adec-4d5c472fe58c', 'Utilities'),
       ('c45ce1b4-7ca8-4b58-9ee5-1ba232ea5a08', 'Credit Card'),
       ('eebbf454-c372-4e64-9e50-8674d6edcac3', 'Health'),
       ('320eb4ab-460f-49b4-8882-a714324ac953', 'Banking'),
       ('6f6d1c0a-7a2a-4beb-b0cb-6862a7f5ee31', 'Food/Beverage'),
       ('27fb7039-2116-4487-ab62-37506b8e93e1', 'Gas/Automotive'),
       ('3654eb59-b651-4e8f-b584-08b1964c91e2', 'Household/Supplies'),
       ('7989c00b-52f4-479f-9117-a5452c588ab0', 'Entertainment'),
       ('f471b99d-ff2f-476f-9172-2c77a561b551', 'Education'),
       ('c8359cb5-7ac6-4b8e-95bf-2cee2c2f9471', 'Gifts and Contributions'),
       ('26e547bf-3c9c-4346-8bec-9c904fc0769e', 'Personal'),
       ('a259eb9c-64ec-4d1c-b3ba-27ee436c31f9', 'Business'),
       ('eeb5cf06-3805-4485-a59c-1678b4e0d564', 'Taxes'),
       ('5175c81f-abb8-4cf7-aa22-8d7f30b3e763', 'Miscellaneous'),
       ('52ddebda-dd63-4de5-bc6a-c973ce0c6375', 'Savings'),
       ('2b089196-339b-4ac2-bcc6-36c68d8cc571', 'Generic');

INSERT INTO transaction_category(transaction_category_id,category_name,transaction_sub_category_id)
VALUES
       ('c797ad15-aa56-48d5-8d4d-959b39806348', 'Paycheck','35af1b26-669e-4413-b21a-dc0db8ff09cc'),
       ('d236af62-a359-4a83-ad0d-2ee233cfb0e8', 'Other Income','35af1b26-669e-4413-b21a-dc0db8ff09cc'),
       ('0b098432-8abb-42f1-8eb5-ed0ca3cc6b56', 'Rent/Mortgage','3560d2cb-eb01-4e2b-839b-696f2686c16f'),
       ('b71dc23c-834b-4caf-8043-da5af921d9f3', 'Cable/Internet','d07c471c-0f67-472d-adec-4d5c472fe58c'),
       ('800aa693-3664-40f1-8b4e-8ad12f76569e', 'Cable','d07c471c-0f67-472d-adec-4d5c472fe58c'),
       ('cfe4a273-d9b6-4730-98b8-cd3bc8aac114', 'Satellite','d07c471c-0f67-472d-adec-4d5c472fe58c'),
       ('4ceb3fc0-8e1b-4629-a267-10c415fd0a79', 'Internet','d07c471c-0f67-472d-adec-4d5c472fe58c'),
       ('6be19dc7-cbf9-45d4-809a-9daebe2c9da8', 'Electricity','d07c471c-0f67-472d-adec-4d5c472fe58c'),
       ('9535663a-ed05-407e-9f4e-262062c4561a', 'Oil','d07c471c-0f67-472d-adec-4d5c472fe58c'),
       ('d75246d9-f58c-4a7b-ae8b-cb4e5aa20f20', 'Water','d07c471c-0f67-472d-adec-4d5c472fe58c'),
       ('c124b5dc-da65-438e-8896-94a3ac56d1c4', 'Telephone','d07c471c-0f67-472d-adec-4d5c472fe58c'),
       ('c8a8de9a-e792-4081-a392-47f83d86e733', 'Garbage Pickup','d07c471c-0f67-472d-adec-4d5c472fe58c'),
       ('20fa5218-da86-4e2e-ae40-2509268442e0', 'Cell Phone','d07c471c-0f67-472d-adec-4d5c472fe58c'),
       ('af04853e-2c18-4ace-87e2-0dd4daf8680a', 'Payment/Credit','c45ce1b4-7ca8-4b58-9ee5-1ba232ea5a08'),
       ('968ab268-394f-4413-a570-1fcba65534c7', 'Interest','c45ce1b4-7ca8-4b58-9ee5-1ba232ea5a08'),
       ('2a9adf33-bb56-416c-a65f-3d906c754959', 'Annual Fee','c45ce1b4-7ca8-4b58-9ee5-1ba232ea5a08'),
       ('978565bd-d7b2-4d2d-8209-2201895a81b1', 'Finance Charge','c45ce1b4-7ca8-4b58-9ee5-1ba232ea5a08'),
       ('bcbc4289-6f57-4fc2-90df-40288afe2a57', 'Over the Limit Fee','c45ce1b4-7ca8-4b58-9ee5-1ba232ea5a08'),
       ('599f3e23-29fc-4994-8641-3a3210a46969', 'Minimum Usage Fee','c45ce1b4-7ca8-4b58-9ee5-1ba232ea5a08'),
       ('29364322-40da-4dce-b29b-ff135bb0d315', 'Cash Advance Fee','c45ce1b4-7ca8-4b58-9ee5-1ba232ea5a08'),
       ('d165fcbe-b50d-4864-80f4-0119ad9a6c05', 'Late Fee','c45ce1b4-7ca8-4b58-9ee5-1ba232ea5a08'),
       ('bcee845c-2b29-45f9-a1ae-871f6979abd8', 'Rewards Program','c45ce1b4-7ca8-4b58-9ee5-1ba232ea5a08'),
       ('9ae00b55-767f-483e-8723-0f40f70dc9b7', 'Health Insurance','eebbf454-c372-4e64-9e50-8674d6edcac3'),
       ('e1ad7fb0-1e22-4f59-b6bc-863d1b335cff', 'Dental Insurance','eebbf454-c372-4e64-9e50-8674d6edcac3'),
       ('f67b0c10-0b8a-4b8b-93a3-509fc9082e6f', 'Medical and Hospital Costs','eebbf454-c372-4e64-9e50-8674d6edcac3'),
       ('9a8fb250-9a92-4b6b-aee0-7ae88df9edd0', 'Doctor/Dentist','eebbf454-c372-4e64-9e50-8674d6edcac3'),
       ('069baaeb-eea5-42ae-b346-ad523e2f4e93', 'Medicine','eebbf454-c372-4e64-9e50-8674d6edcac3'),
       ('2f48de44-4d34-4bb4-a564-6841fc254801', 'Glasses','eebbf454-c372-4e64-9e50-8674d6edcac3'),
       ('ff67605d-a3e4-4031-a846-29ed9e35b79e', 'Hearing Aids','eebbf454-c372-4e64-9e50-8674d6edcac3'),
       ('b5208333-c5f8-4304-a4dd-8c34856df20c', 'First Aid Supplies','eebbf454-c372-4e64-9e50-8674d6edcac3'),
       ('b5ce3a26-9391-44ef-8519-917be5859699', 'Treatment and Therapy','eebbf454-c372-4e64-9e50-8674d6edcac3'),
       ('df74dae2-154c-486f-81ae-e72047b8d923', 'Fitness Club Membership','eebbf454-c372-4e64-9e50-8674d6edcac3'),
       ('7ec9a469-4b80-47f1-9224-661effa92892', 'Investments','52ddebda-dd63-4de5-bc6a-c973ce0c6375'),
       ('ac795c7b-0508-47e9-a299-1cfab2bcf4de', 'Emergency Fund','52ddebda-dd63-4de5-bc6a-c973ce0c6375'),
       ('04b0afe3-7bd0-411e-8889-289784513977', 'Retirement','52ddebda-dd63-4de5-bc6a-c973ce0c6375'),
       ('40916d5f-4dc6-49d1-959a-19fd4eee83f6', 'Interest','320eb4ab-460f-49b4-8882-a714324ac953'),
       ('6ceb1d97-c731-43d0-976f-dde70c77ca1c', 'Banking Fees','320eb4ab-460f-49b4-8882-a714324ac953'),
       ('f898a8b5-785a-4049-af6c-c916aeda3973', 'Groceries','6f6d1c0a-7a2a-4beb-b0cb-6862a7f5ee31'),
       ('f68c3e2d-67e1-475f-9ad8-bc7af82f2a7d', 'Dining','6f6d1c0a-7a2a-4beb-b0cb-6862a7f5ee31'),
       ('2da7722c-5804-41eb-91e0-f06aeec629d4', 'Liquor/Beer','6f6d1c0a-7a2a-4beb-b0cb-6862a7f5ee31'),
       ('32d63457-9f75-4ef4-93e1-a2be87c59204', 'Snacks  Coffee Breaks','6f6d1c0a-7a2a-4beb-b0cb-6862a7f5ee31'),
       ('0eaf30d6-b0c3-4b16-8b21-d8a8d6958bc7', 'School Lunch','6f6d1c0a-7a2a-4beb-b0cb-6862a7f5ee31'),
       ('9d958326-ae57-4d1e-9792-b6baf8a0b159', 'Work Lunch','6f6d1c0a-7a2a-4beb-b0cb-6862a7f5ee31'),
       ('52bd93cf-339c-4693-bbaf-d71b5bd631f7', 'Home Food Production','6f6d1c0a-7a2a-4beb-b0cb-6862a7f5ee31'),
       ('92e0c746-6d88-4fad-96ae-d409f196f662', 'Gas','27fb7039-2116-4487-ab62-37506b8e93e1'),
       ('57b692ad-4c95-4b22-91ef-f8c4ca09e6cf', 'Insurance','27fb7039-2116-4487-ab62-37506b8e93e1'),
       ('1e5cb891-2ef9-4260-add3-be4ce9b99645', 'Maintenance','27fb7039-2116-4487-ab62-37506b8e93e1'),
       ('a9ac180c-36d3-4144-81c6-53c4abce5e6b', 'Supplies/Tools','27fb7039-2116-4487-ab62-37506b8e93e1'),
       ('fb01a502-fe68-444c-ad5e-940e68533be9', 'Licensing Fees','27fb7039-2116-4487-ab62-37506b8e93e1'),
       ('3b91f5a1-5bfa-449e-b9c5-a484a3d142d1', 'Taxi','27fb7039-2116-4487-ab62-37506b8e93e1'),
       ('0f6587a4-0a64-4612-ac31-b245930e7547', 'Bus/Subway/Train','27fb7039-2116-4487-ab62-37506b8e93e1'),
       ('6f9b60f7-0fd5-4fd5-9fa1-ee11268eb4b7', 'Parking','27fb7039-2116-4487-ab62-37506b8e93e1'),
       ('6bd30b23-0053-4fe3-be47-dd4ca99d745d', 'Tolls','27fb7039-2116-4487-ab62-37506b8e93e1'),
       ('6f484fa9-5357-4610-8dde-66528fbb6dc2', 'Laundry Supplies','3654eb59-b651-4e8f-b584-08b1964c91e2'),
       ('443a03b1-3b20-427e-8941-743dd45c0776', 'Cleaning Supplies','3654eb59-b651-4e8f-b584-08b1964c91e2'),
       ('6c34b231-fc00-4048-a0b0-708da6da9fce', 'Furniture','3654eb59-b651-4e8f-b584-08b1964c91e2'),
       ('c36ec48b-99c3-4a84-b97e-deaa9ccdc525', 'Appliances','3654eb59-b651-4e8f-b584-08b1964c91e2'),
       ('0da6676e-3800-4776-b1fc-b3d3d2f27489', 'Dishes and Cutlery','3654eb59-b651-4e8f-b584-08b1964c91e2'),
       ('6bec08a4-59dc-400e-9ed9-ca89e92265fb', 'Cooking Supplies','3654eb59-b651-4e8f-b584-08b1964c91e2'),
       ('65ff6024-8b1e-46df-8685-5bc2dbe08766', 'Linens','3654eb59-b651-4e8f-b584-08b1964c91e2'),
       ('e10f6389-bc00-4288-8e9a-b51f5e6756e1', 'Soap and Shampoo','3654eb59-b651-4e8f-b584-08b1964c91e2'),
       ('0227c55b-80dd-4ecd-9e85-0b871dc157d5', 'Facial Tissue','3654eb59-b651-4e8f-b584-08b1964c91e2'),
       ('f986f0a4-db33-4049-8fb7-c90ce3bd54bd', 'Yard Maintenance','3654eb59-b651-4e8f-b584-08b1964c91e2'),
       ('56bc4f92-bd23-41a3-b5e9-3d8fd73b3a87', 'House Repairs','3654eb59-b651-4e8f-b584-08b1964c91e2'),
       ('2e3008b8-ea92-4c86-b3e6-56c31f503311', 'Home Project Supplies','3654eb59-b651-4e8f-b584-08b1964c91e2'),
       ('7823cb65-d3a8-476a-8b9e-85d3e74c9efa', 'Safety Deposit Box Rental','3654eb59-b651-4e8f-b584-08b1964c91e2'),
       ('1ba863ac-c49d-4fe0-94cf-9d62f2202fa8', 'Yard Improvement and Supplies','3654eb59-b651-4e8f-b584-08b1964c91e2'),
       ('f6661d4d-76d2-4179-ace2-e380f19d1a96', 'Books','7989c00b-52f4-479f-9117-a5452c588ab0'),
       ('7fa3738e-436c-480b-a87b-4c4525b3e453', 'Magazines','7989c00b-52f4-479f-9117-a5452c588ab0'),
       ('103f47ef-0705-405e-800a-d06d6afa32d0', 'Movie Theater','7989c00b-52f4-479f-9117-a5452c588ab0'),
       ('59cc99ba-87c6-4041-8378-40b6bb3f10e3', 'Video Streaming/Rental/Pay Per View','7989c00b-52f4-479f-9117-a5452c588ab0'),
       ('46f07830-3cb2-45a4-a39c-cc5ef1ddcf9d', 'Sports/Games','7989c00b-52f4-479f-9117-a5452c588ab0'),
       ('6b3f7d52-95d4-478d-bf25-76fd4f40e637', 'Sporting Events','7989c00b-52f4-479f-9117-a5452c588ab0'),
       ('ffb61d50-5da7-402d-9344-b7d06e584349', 'Sporting Goods','7989c00b-52f4-479f-9117-a5452c588ab0'),
       ('d6f6e7ac-916b-4281-8036-bfb0a3086d27', 'Concerts','7989c00b-52f4-479f-9117-a5452c588ab0'),
       ('155143f8-1b73-4fa9-ad70-e809ff53b62e', 'Music','7989c00b-52f4-479f-9117-a5452c588ab0'),
       ('8450a82f-755e-4860-835c-9f27245bbd0b', 'Cultural Events','7989c00b-52f4-479f-9117-a5452c588ab0'),
       ('e2939948-039c-421f-adef-18e62c2b830a', 'Video Games','7989c00b-52f4-479f-9117-a5452c588ab0'),
       ('2fe0c4ed-ade2-4f95-b64f-8c154e08415d', 'Toys','7989c00b-52f4-479f-9117-a5452c588ab0'),
       ('0ee36731-df90-4224-b983-4622e426261d', 'Tourist Attractions','7989c00b-52f4-479f-9117-a5452c588ab0'),
       ('e32c239b-9432-4ea1-9ce5-730f7cc2f4f4', 'Electronics','7989c00b-52f4-479f-9117-a5452c588ab0'),
       ('0ed503f9-59b6-40b9-aad6-58c544dbd42c', 'Tuition','f471b99d-ff2f-476f-9172-2c77a561b551'),
       ('6ec3e491-afc3-495f-95c9-2aaa3325c3f8', 'Books','f471b99d-ff2f-476f-9172-2c77a561b551'),
       ('03759b95-f7d4-44e4-b84d-3c357df5e1a2', 'Stationary','f471b99d-ff2f-476f-9172-2c77a561b551'),
       ('4cb2508f-2087-4894-96a1-6e4a7809ba2f', 'Courses/Lessons','f471b99d-ff2f-476f-9172-2c77a561b551'),
       ('6af57d09-5fef-4c20-a171-8093354470cb', 'Gifts','c8359cb5-7ac6-4b8e-95bf-2cee2c2f9471'),
       ('6d5f528f-055d-4e66-8f7d-8294d60b440a', 'Cards and Wrapping Paper','c8359cb5-7ac6-4b8e-95bf-2cee2c2f9471'),
       ('6a4e2486-b792-47df-bb85-a14c43eabf50', 'Flowers','c8359cb5-7ac6-4b8e-95bf-2cee2c2f9471'),
       ('76fe66cd-cd8b-4292-9a85-a1ae1782b463', 'Charitable Donations','c8359cb5-7ac6-4b8e-95bf-2cee2c2f9471'),
       ('e7735767-83bc-41a5-8f8c-931700c0c010', 'Haircuts','26e547bf-3c9c-4346-8bec-9c904fc0769e'),
       ('949e0efd-3557-426e-b355-e7e9a5bf0170', 'Beauty Shop','26e547bf-3c9c-4346-8bec-9c904fc0769e'),
       ('cd5b264b-d381-4f58-bead-43c1008e369a', 'Cosmetics','26e547bf-3c9c-4346-8bec-9c904fc0769e'),
       ('7d965078-b733-4167-ba34-6535028e0fa8', 'Toiletries','26e547bf-3c9c-4346-8bec-9c904fc0769e'),
       ('50abd605-6540-49ec-9cf9-7404fc68f000', 'Shaving Supplies','26e547bf-3c9c-4346-8bec-9c904fc0769e'),
       ('e0888ffc-2162-4f7e-a00f-42a3fec432b4', 'Clothing','26e547bf-3c9c-4346-8bec-9c904fc0769e'),
       ('323ddfa3-b0b0-4167-a055-ca667bd162d9', 'Alterations','26e547bf-3c9c-4346-8bec-9c904fc0769e'),
       ('f67d8c44-224c-4660-a61a-3e72f100d9df', 'Cleaning','26e547bf-3c9c-4346-8bec-9c904fc0769e'),
       ('24d066b3-628e-43f8-adbc-129a13b7d76e', 'Miscellaneous','26e547bf-3c9c-4346-8bec-9c904fc0769e'),
       ('06f74a62-5396-4070-a53c-99e705f73302', 'Attire','a259eb9c-64ec-4d1c-b3ba-27ee436c31f9'),
       ('3b62d943-e948-4d5e-bc7d-9a80c9c11a76', 'Office Supplies','a259eb9c-64ec-4d1c-b3ba-27ee436c31f9'),
       ('dc75847b-6cf8-4828-8285-6bcbe1d9227d', 'Software','a259eb9c-64ec-4d1c-b3ba-27ee436c31f9'),
       ('31038b34-0d2d-49d9-a448-44d8f6be1445', 'State Income Tax','eeb5cf06-3805-4485-a59c-1678b4e0d564'),
       ('4a9ac87c-5961-4e9a-a724-a4d0789b1cfb', 'Federal Income Tax','eeb5cf06-3805-4485-a59c-1678b4e0d564'),
       ('e2ba1462-4147-4a6f-a791-0e1f5b38f43e', 'Social Security Tax','eeb5cf06-3805-4485-a59c-1678b4e0d564'),
       ('e4d542d5-4859-4ec0-bfa5-d1f166b33dd7', 'Medicare Tax','eeb5cf06-3805-4485-a59c-1678b4e0d564'),
       ('9639d3da-8b14-4aaa-b8be-3e24d36fdda9', 'Investment Tax','eeb5cf06-3805-4485-a59c-1678b4e0d564'),
       ('d6165238-0a85-4336-b067-130db79f1ea8', 'Tax Fees','eeb5cf06-3805-4485-a59c-1678b4e0d564'),
       ('e7cb5a89-6796-4649-92ba-6a4f014a71fc', 'Software','eeb5cf06-3805-4485-a59c-1678b4e0d564'),
       ('459024ac-129c-49e4-b3ca-3c7d96106c4b', 'Vacation','5175c81f-abb8-4cf7-aa22-8d7f30b3e763'),
       ('6edf604c-dd3f-48a9-92e5-db79027766f9', 'Unknown','5175c81f-abb8-4cf7-aa22-8d7f30b3e763'),
       ('dbd9295e-e8e2-4b1a-bab6-9376efb436fa', 'Other','5175c81f-abb8-4cf7-aa22-8d7f30b3e763'),
       ('cee18c98-1208-4655-95b0-fbb243dc84ff', 'Transfer','320eb4ab-460f-49b4-8882-a714324ac953');
