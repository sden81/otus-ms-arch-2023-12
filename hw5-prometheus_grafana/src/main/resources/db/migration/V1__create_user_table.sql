CREATE TABLE hw5.users(
  id BIGSERIAL,
  first_name VARCHAR (50),
  last_name VARCHAR (50),
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW()
);