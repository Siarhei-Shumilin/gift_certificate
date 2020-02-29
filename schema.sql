CREATE TABLE certificates (
id bigint auto_increment NOT NULL ,
name VARCHAR(30) NOT NULL,
description VARCHAR(300) NOT NULL,
price DECIMAL(4,2) NOT NULL,
create_date TIMESTAMP NOT NULL,
last_update_date TIMESTAMP NOT NULL,
duration INTEGER NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE tags (
id bigint auto_increment NOT NULL,
   name VARCHAR(20) UNIQUE NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE connecting (
id bigint AUTO_INCREMENT NOT NULL,
certificate_id bigint NOT NULL,
tag_id bigint NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (certificate_id) REFERENCES certificates (id) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (tag_id) REFERENCES tags (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE users (
id bigint auto_increment NOT NULL,
active boolean,
password VARCHAR(60) NOT NULL,
roles VARCHAR(30) NOT NULL,
user_name VARCHAR(30) UNIQUE NOT NULL,
PRIMARY KEY (id)
);

INSERT INTO users (active, password, roles, user_name) VALUES (true, '111', 'ROLE_ADMIN', 'admin');
INSERT INTO users (active, password, roles, user_name) VALUES (true, 'pass', 'ROLE_USER', 'user');

CREATE TABLE purchases
(
id bigint AUTO_INCREMENT NOT NULL,
user_id bigint NOT NULL,
certificate_id bigint NOT NULL,
price decimal(10,2) not null,
date_purchase timestamp not null,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES users (id),
FOREIGN KEY (certificate_id) REFERENCES certificates (id) ON UPDATE CASCADE ON DELETE CASCADE
);