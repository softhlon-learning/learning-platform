CREATE TABLE accounts (
    id uuid DEFAULT gen_random_uuid(),
    name VARCHAR NOT NULL,
    type VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    password VARCHAR NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT false,
    created_time TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
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
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE enrollments (
    id uuid DEFAULT gen_random_uuid(),
    account_id uuid NOT NULL,
    course_id uuid NOT NULL,
    content VARCHAR,
    enrolled_time TIMESTAMP WITH TIME ZONE,
    completed_time TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id),
    UNIQUE(account_id, course_id),
    CONSTRAINT fk_enrollments_accounts
         FOREIGN KEY (account_id)
         REFERENCES accounts (id),
    CONSTRAINT fk_enrollments_courses
         FOREIGN KEY (course_id)
         REFERENCES courses (id)
);

CREATE TABLE customers (
    id uuid DEFAULT gen_random_uuid(),
    account_id uuid NOT NULL,
    customer_id VARCHAR NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id),
        CONSTRAINT fk_customers_accounts
             FOREIGN KEY (account_id)
             REFERENCES accounts (id),
    CONSTRAINT unique_customers_email UNIQUE (customer_id)
);

CREATE INDEX customers__customer_id_index ON customers (customer_id);

CREATE TABLE subscriptions (
    id uuid DEFAULT gen_random_uuid(),
    subscription_id VARCHAR NOT NULL,
    customer_id VARCHAR NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    canceled_at TIMESTAMP WITH TIME ZONE,
    cancel_at TIMESTAMP WITH TIME ZONE,
    cancel_reason VARCHAR NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE invalidated_tokens (
    id uuid DEFAULT gen_random_uuid(),
    token_hash VARCHAR NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id)
);

CREATE INDEX invalidated_tokens__token_hash_index ON invalidated_tokens (token_hash);

CREATE TABLE reset_password_tokens (
    id uuid DEFAULT gen_random_uuid(),
    account_id uuid,
    token VARCHAR NOT NULL,
    expiration_time TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id),
    CONSTRAINT fk_reset_password_tokens_accounts
         FOREIGN KEY (account_id)
         REFERENCES accounts (id)
);

CREATE INDEX reset_password_tokens__token_index ON reset_password_tokens (token);

CREATE TABLE checkout_sessions (
    id uuid DEFAULT gen_random_uuid(),
    account_id uuid,
    session_id VARCHAR NOT NULL,
    expired_time TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    completed_time TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id),
    CONSTRAINT fk_checkout_sessions_accounts
         FOREIGN KEY (account_id)
         REFERENCES accounts (id)
);

CREATE INDEX checkout_sessions__session_id_index ON checkout_sessions (session_id);

CREATE TABLE events_log (
    id uuid DEFAULT gen_random_uuid(),
    event_type VARCHAR NOT NULL,
    customer_id VARCHAR NOT NULL,
    payload VARCHAR NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id)
);

CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
   IF row(NEW.*) IS DISTINCT FROM row(OLD.*) THEN
      NEW.updated_at = now();
      RETURN NEW;
   ELSE
      RETURN OLD;
   END IF;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_accounts_updated_at BEFORE UPDATE ON accounts FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
CREATE TRIGGER update_courses_updated_at BEFORE UPDATE ON courses FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
CREATE TRIGGER update_enrollments_updated_at BEFORE UPDATE ON enrollments FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
CREATE TRIGGER update_subscriptions_updated_at BEFORE UPDATE ON subscriptions FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
CREATE TRIGGER update_checkout_sessions_updated_at BEFORE UPDATE ON checkout_sessions FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
