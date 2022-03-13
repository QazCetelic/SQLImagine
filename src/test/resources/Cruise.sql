-- Removes the old table if it exists
DROP TABLE IF EXISTS test_db.Port;
CREATE TABLE test_db.Port (
  id INTEGER PRIMARY KEY,
  city VARCHAR(64),
  country VARCHAR(64),
  ship VARCHAR(64),
  boarding INTEGER,
  name VARCHAR(64) PRIMARY KEY,
  description VARCHAR(64),
  cruise.id INTEGER PRIMARY KEY,
  type VARCHAR(64),
  price FLOAT,
  date DATE,
  FOREIGN KEY (ship) REFERENCES Ship(ship),
  FOREIGN KEY (boarding) REFERENCES Port(id),
  FOREIGN KEY (type) REFERENCES PackageType(name)
);
-- Removes the old table if it exists
DROP TABLE IF EXISTS test_db.Ship;
CREATE TABLE test_db.Ship (
  ship VARCHAR(64),
  id INTEGER PRIMARY KEY,
  boarding INTEGER,
  name VARCHAR(64) PRIMARY KEY,
  description VARCHAR(64),
  cruise.id INTEGER PRIMARY KEY,
  type VARCHAR(64),
  price FLOAT,
  date DATE,
  FOREIGN KEY (ship) REFERENCES Ship(ship),
  FOREIGN KEY (boarding) REFERENCES Port(id),
  FOREIGN KEY (type) REFERENCES PackageType(name)
);
-- Removes the old table if it exists
DROP TABLE IF EXISTS test_db.Cruise;
CREATE TABLE test_db.Cruise (
  id INTEGER PRIMARY KEY,
  ship VARCHAR(64),
  boarding INTEGER,
  name VARCHAR(64) PRIMARY KEY,
  description VARCHAR(64),
  cruise.id INTEGER PRIMARY KEY,
  type VARCHAR(64),
  price FLOAT,
  date DATE,
  FOREIGN KEY (ship) REFERENCES Ship(ship),
  FOREIGN KEY (boarding) REFERENCES Port(id),
  FOREIGN KEY (type) REFERENCES PackageType(name)
);
-- Removes the old table if it exists
DROP TABLE IF EXISTS test_db.PackageType;
CREATE TABLE test_db.PackageType (
  name VARCHAR(64) PRIMARY KEY,
  description VARCHAR(64),
  cruise.id INTEGER PRIMARY KEY,
  type VARCHAR(64),
  price FLOAT,
  date DATE,
  FOREIGN KEY (type) REFERENCES PackageType(name)
);
-- Removes the old table if it exists
DROP TABLE IF EXISTS test_db.Package;
CREATE TABLE test_db.Package (
  name VARCHAR(64) PRIMARY KEY,
  cruise.id INTEGER PRIMARY KEY,
  type VARCHAR(64),
  price FLOAT,
  date DATE,
  FOREIGN KEY (type) REFERENCES PackageType(name)
);
-- Removes the old table if it exists
DROP TABLE IF EXISTS test_db.Departure;
CREATE TABLE test_db.Departure (
  cruise.id INTEGER PRIMARY KEY,
  date DATE
);