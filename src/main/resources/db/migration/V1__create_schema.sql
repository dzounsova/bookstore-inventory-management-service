-- create genre table
CREATE SEQUENCE genre_id_seq INCREMENT 1 START 1;
CREATE TABLE genre
(
    id          BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('genre_id_seq'),
    name        TEXT   NOT NULL UNIQUE,
    description TEXT
);

-- create author table
CREATE SEQUENCE author_id_seq INCREMENT 1 START 1;
CREATE TABLE author
(
    id                BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('author_id_seq'),
    first_name        TEXT   NOT NULL,
    last_name         TEXT   NOT NULL,
    display_name      TEXT,
    display_name_auto BOOLEAN                     DEFAULT TRUE,
    isni              TEXT,
    viaf              TEXT,
    image_url         TEXT,
    description       TEXT,
    CONSTRAINT chk_external_id CHECK (isni IS NOT NULL OR viaf IS NOT NULL)
);
CREATE UNIQUE INDEX idx_isni ON author (isni) WHERE isni IS NOT NULL;
CREATE UNIQUE INDEX idx_viaf ON author (viaf) WHERE viaf IS NOT NULL;

-- create publisher table
CREATE SEQUENCE publisher_id_seq INCREMENT 1 START 1;
CREATE TABLE publisher
(
    id          BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('publisher_id_seq'),
    name        TEXT   NOT NULL UNIQUE,
    description TEXT
);

-- create book table
CREATE SEQUENCE book_id_seq INCREMENT 1 START 1;
CREATE TABLE book
(
    id             BIGINT  NOT NULL PRIMARY KEY DEFAULT nextval('book_id_seq'),
    isbn           TEXT    NOT NULL UNIQUE,
    title          TEXT    NOT NULL,
    description    TEXT,
    edition        INTEGER                      DEFAULT 1,
    format         TEXT    NOT NULL,
    page_count     INTEGER NOT NULL,
    language_code  TEXT    NOT NULL,
    image_url      TEXT,
    price          NUMERIC NOT NULL,
    quantity       INTEGER NOT NULL,
    published_date DATE    NOT NULL,
    publisher_id   BIGINT  NOT NULL REFERENCES publisher (id) ON DELETE CASCADE,
    genre_id       BIGINT  NOT NULL REFERENCES genre (id) ON DELETE CASCADE,
    CONSTRAINT chk_price CHECK (price >= 0),
    CONSTRAINT chk_quantity CHECK (quantity >= 0)
);
CREATE INDEX idx_publisher_id ON book (publisher_id);
CREATE INDEX idx_genre_id ON book (genre_id);

-- create relationship table between book and author
CREATE TABLE book_author
(
    book_id   BIGINT NOT NULL REFERENCES book (id) ON DELETE CASCADE,
    author_id BIGINT NOT NULL REFERENCES author (id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, author_id)
);