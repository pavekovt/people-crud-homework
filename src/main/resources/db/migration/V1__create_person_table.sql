CREATE TABLE public.person (
  id SERIAL NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  phone VARCHAR(32),
  email VARCHAR(50),
  date_of_birth DATE NOT NULL,

  CONSTRAINT pk_person PRIMARY KEY (ID)
);