CREATE TABLE accounts (
    id uuid DEFAULT gen_random_uuid(),
    name VARCHAR NOT NULL,
    type VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    password VARCHAR NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT false,
    created_time TIMESTAMP DEFAULT current_timestamp,
    updated_time TIMESTAMP DEFAULT current_timestamp,
    PRIMARY KEY (id),
    CONSTRAINT unique_accounts_email UNIQUE (email)
);

CREATE TABLE courses (
    id uuid,
    code VARCHAR NOT NULL,
    order_no INTEGER,
    name VARCHAR NOT NULL,
    description VARCHAR,
    content VARCHAR,
    version VARCHAR NOT NULL,
    created_time TIMESTAMP DEFAULT current_timestamp,
    updated_time TIMESTAMP DEFAULT current_timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE enrollments (
    id uuid DEFAULT gen_random_uuid(),
    account_id uuid NOT NULL,
    course_id uuid NOT NULL,
    content VARCHAR,
    enrolled_time TIMESTAMP,
    completed_time TIMESTAMP,
    created_time TIMESTAMP DEFAULT current_timestamp,
    updated_time TIMESTAMP DEFAULT current_timestamp,
    PRIMARY KEY (id),
    UNIQUE(account_id, course_id),
    CONSTRAINT fk_enrollments_accounts
         FOREIGN KEY (account_id)
         REFERENCES accounts (id),
    CONSTRAINT fk_enrollments_courses
         FOREIGN KEY (course_id)
         REFERENCES courses (id)
);

CREATE TABLE subscriptions (
    id uuid DEFAULT gen_random_uuid(),
    account_id uuid NOT NULL,
    status VARCHAR NOT NULL,
    started_time TIMESTAMP,
    cancelled_time TIMESTAMP,
    created_time TIMESTAMP DEFAULT current_timestamp,
    updated_time TIMESTAMP DEFAULT current_timestamp,
    PRIMARY KEY (id),
    CONSTRAINT fk_subscriptions_accounts
         FOREIGN KEY (account_id)
         REFERENCES accounts (id)
);

CREATE TABLE invalidated_tokens (
    id uuid DEFAULT gen_random_uuid(),
    token_hash VARCHAR NOT NULL,
    created_time TIMESTAMP DEFAULT current_timestamp,
    PRIMARY KEY (id)
);

CREATE INDEX invalidated_tokens__token_hash_index ON invalidated_tokens (token_hash);

CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
   IF row(NEW.*) IS DISTINCT FROM row(OLD.*) THEN
      NEW.updated_time = now();
      RETURN NEW;
   ELSE
      RETURN OLD;
   END IF;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_accounts_updated_time BEFORE UPDATE ON accounts FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
CREATE TRIGGER update_courses_updated_time BEFORE UPDATE ON courses FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
CREATE TRIGGER update_enrollments_updated_time BEFORE UPDATE ON enrollments FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
CREATE TRIGGER update_subscriptions_updated_time BEFORE UPDATE ON subscriptions FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
