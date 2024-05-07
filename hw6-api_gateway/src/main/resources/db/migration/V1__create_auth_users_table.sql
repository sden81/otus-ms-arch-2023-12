CREATE TABLE hw6.users(
  id BIGSERIAL PRIMARY KEY,
  first_name VARCHAR (50),
  last_name VARCHAR (50),
  born_date DATE,
  login VARCHAR (50),
  password VARCHAR (50),
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW()
);