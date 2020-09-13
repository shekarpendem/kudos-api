CREATE TABLE employees (
  id          INTEGER PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(60) NULL,
  last_name VARCHAR(60) NULL,
  email_address VARCHAR(255) NULL
);

CREATE TABLE kudos (
  id          INTEGER PRIMARY KEY AUTO_INCREMENT,
  kudo_comment VARCHAR(255) NULL,
  kudo_from VARCHAR(120) NULL,
  eid INTEGER,
  CONSTRAINT FK_EID FOREIGN KEY (eid) REFERENCES employees(id)
);