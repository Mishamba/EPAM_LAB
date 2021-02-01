CREATE TABLE gift_certificate (
	id INT AUTO_INCREMENT NOT NULL UNIQUE,
    certificate_name varchar(30) NOT NULL,
    certificate_description varchar(255) NOT NULL,
    price INT NOT NULL,
    duration INT NOT NULL,
    create_date VARCHAR(23) NOT NULL,
    last_update_date varchar(23) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE tag (
	id INT AUTO_INCREMENT NOT NULL UNIQUE,
    _name varchar(30) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE certificate_tags (
	certificate_id INT NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY (certificate_id) REFERENCES gift_certificate(id),
    FOREIGN KEY (tag_id) REFERENCES tag(id)
);