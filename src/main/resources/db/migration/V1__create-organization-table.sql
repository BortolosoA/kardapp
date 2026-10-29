CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE organization (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    cnpj_name VARCHAR(100) NOT NULL,
    fantasy_name VARCHAR(100) NOT NULL,
    cnpj VARCHAR(14) NOT NULL,
    ie INTEGER(14) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);