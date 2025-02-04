-- _accounts schema

CREATE SCHEMA _accounts;

CREATE TABLE _accounts.accounts (
    id uuid DEFAULT gen_random_uuid(),
    name VARCHAR NOT NULL,
    type VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    password VARCHAR NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id),
    UNIQUE (email)
);

CREATE TABLE _accounts.invalidated_tokens (
    id uuid DEFAULT gen_random_uuid(),
    token_hash VARCHAR NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id)
);

CREATE INDEX invalidated_tokens__token_hash_index ON _accounts.invalidated_tokens (token_hash);

CREATE TABLE _accounts.reset_password_tokens (
    id uuid DEFAULT gen_random_uuid(),
    account_id uuid,
    token VARCHAR NOT NULL,
    expire_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id),
    CONSTRAINT fk_reset_password_tokens__accounts
         FOREIGN KEY (account_id)
         REFERENCES _accounts.accounts (id)
);

CREATE INDEX reset_password_tokens__token_index ON _accounts.reset_password_tokens (token);

-- _courses schema

CREATE SCHEMA _courses;

CREATE TABLE _courses.courses (
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

CREATE TABLE _courses.enrollments (
    id uuid DEFAULT gen_random_uuid(),
    account_id uuid NOT NULL,
    course_id uuid NOT NULL,
    content VARCHAR,
    enrolled_at TIMESTAMP WITH TIME ZONE,
    completed_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id),
    UNIQUE (account_id, course_id),
    CONSTRAINT fk_enrollments__accounts
         FOREIGN KEY (account_id)
         REFERENCES _accounts.accounts (id),
    CONSTRAINT fk_enrollments__courses
         FOREIGN KEY (course_id)
         REFERENCES _courses.courses (id)
);

-- _subscriptions schema

CREATE SCHEMA _subscriptions;

CREATE TABLE _subscriptions.customers (
    id uuid DEFAULT gen_random_uuid(),
    account_id uuid NOT NULL,
    customer_id VARCHAR NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id),
    CONSTRAINT fk_customers__accounts
        FOREIGN KEY (account_id)
        REFERENCES _accounts.accounts (id),
    UNIQUE (account_id),
    UNIQUE (customer_id)
);

CREATE INDEX customers__customer_id_index ON _subscriptions.customers (customer_id);

CREATE TABLE _subscriptions.subscriptions (
    id uuid DEFAULT gen_random_uuid(),
    subscription_id VARCHAR NOT NULL,
    customer_id VARCHAR NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    period_start_at TIMESTAMP WITH TIME ZONE,
    period_end_at TIMESTAMP WITH TIME ZONE,
    invoice_id VARCHAR,
    canceled_at TIMESTAMP WITH TIME ZONE,
    cancel_at TIMESTAMP WITH TIME ZONE,
    cancel_reason VARCHAR NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE _subscriptions.checkout_sessions (
    id uuid DEFAULT gen_random_uuid(),
    account_id uuid,
    session_id VARCHAR NOT NULL,
    expired_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    completed_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id),
    CONSTRAINT fk_checkout_sessions__accounts
         FOREIGN KEY (account_id)
         REFERENCES _accounts.accounts (id)
);

CREATE INDEX checkout_sessions__session_id_index ON _subscriptions.checkout_sessions (session_id);

CREATE TABLE _subscriptions.events_log (
    id uuid DEFAULT gen_random_uuid(),
    event_type VARCHAR NOT NULL,
    customer_id VARCHAR NOT NULL,
    payload VARCHAR NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE _subscriptions.invoices (
    id uuid DEFAULT gen_random_uuid(),
    invoice_id VARCHAR NOT NULL,
    paid BOOLEAN DEFAULT false,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id),
    UNIQUE (invoice_id)
);

CREATE TABLE _subscriptions.free_trials (
    id uuid DEFAULT gen_random_uuid(),
    account_id uuid NOT NULL,
    expire_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    PRIMARY KEY (id),
    CONSTRAINT fk_free_trials__accounts
        FOREIGN KEY (account_id)
        REFERENCES _accounts.accounts (id)
);

---- common

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

CREATE TRIGGER update_accounts_updated_at BEFORE UPDATE ON _accounts.accounts FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
CREATE TRIGGER update_courses_updated_at BEFORE UPDATE ON _courses.courses FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
CREATE TRIGGER update_enrollments_updated_at BEFORE UPDATE ON _courses.enrollments FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
CREATE TRIGGER update_subscriptions_updated_at BEFORE UPDATE ON _subscriptions.subscriptions FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
CREATE TRIGGER update_checkout_sessions_updated_at BEFORE UPDATE ON _subscriptions.checkout_sessions FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
CREATE TRIGGER update_free_trials_updated_at BEFORE UPDATE ON _subscriptions.free_trials FOR EACH ROW EXECUTE PROCEDURE update_modified_column();
