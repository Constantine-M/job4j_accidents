CREATE TABLE IF NOT EXISTS accidents
(
    id SERIAL PRIMARY KEY,
    acc_name VARCHAR NOT NULL,
    acc_text VARCHAR NOT NULL,
    acc_address VARCHAR NOT NULL,
    accident_type_id INT NOT NULL REFERENCES accident_type(id)
);