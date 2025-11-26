CREATE TABLE IF NOT EXISTS profile (
    id SERIAL PRIMARY KEY,
    login VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL);
CREATE TABLE IF NOT EXISTS employee (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    id_profile INT NOT NULL,
    CONSTRAINT fk_profile FOREIGN KEY (id_profile) REFERENCES profile(id)
    );
