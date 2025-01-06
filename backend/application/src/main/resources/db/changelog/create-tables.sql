CREATE TABLE accounts (
    id uuid DEFAULT gen_random_uuid(),
    name VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    status VARCHAR NOT NULL,
    created_time timestamp,
    updated_time timestamp,
    PRIMARY KEY (id),
    CONSTRAINT unique_accounts_email UNIQUE (email)
);

CREATE TABLE courses (
    id uuid DEFAULT gen_random_uuid(),
    name VARCHAR NOT NULL,
    description VARCHAR,
    content VARCHAR,
    version VARCHAR NOT NULL,
    created_time timestamp,
    updated_time timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE enrollments (
    id uuid DEFAULT gen_random_uuid(),
    account_id uuid,
    course_id uuid,
    status VARCHAR NOT NULL,
    content VARCHAR,
    enrolled_time timestamp,
    unenrolled_time timestamp,
    completed_time timestamp,
    created_time timestamp,
    updated_time timestamp,
    PRIMARY KEY (id),
    CONSTRAINT fk_enrollments_accounts
         FOREIGN KEY (account_id)
         REFERENCES accounts (id),
    CONSTRAINT fk_enrollments_courses
         FOREIGN KEY (course_id)
         REFERENCES courses (id)
);

CREATE TABLE subscriptions (
    id uuid DEFAULT gen_random_uuid(),
    account_id uuid,
    status VARCHAR NOT NULL,
    started_time timestamp,
    cancelled_time timestamp,
    created_time timestamp,
    updated_time timestamp,
    PRIMARY KEY (id),
    CONSTRAINT fk_subscriptions_accounts
         FOREIGN KEY (account_id)
         REFERENCES accounts (id)
);