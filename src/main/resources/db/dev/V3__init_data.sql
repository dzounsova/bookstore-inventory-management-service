-- Genres categorization reflects Amazon
INSERT INTO genre (name)
VALUES ('Comics & Manga'),
       ('LGBTQIA+'),
       ('Literature & Fiction'),
       ('Mystery, Thriller & Suspense'),
       ('Romance'),
       ('Science Fiction & Fantasy'),
       ('Teen & Young Adult'),
       ('Arts & Photography'),
       ('Biographies & Memoirs'),
       ('Business & Money'),
       ('Computers & Technology'),
       ('Cookbooks, Food & Wine'),
       ('Crafts, Hobbies & Home'),
       ('Education & Teaching'),
       ('Engineering & Transportation'),
       ('Health, Fitness & Dieting'),
       ('History'),
       ('Humor & Entertainment'),
       ('Law'),
       ('Medical Books'),
       ('Parenting & Relationships'),
       ('Politics & Social Sciences'),
       ('Reference'),
       ('Religion & Spirituality'),
       ('Science & Math'),
       ('Self-help'),
       ('Sports & Outdoors'),
       ('Travel'),
       ('Children''s Books');

INSERT INTO publisher(name)
VALUES ('O''Reilly Media'),
       ('Random House Worlds'),
       ('Laguna');
INSERT
INTO author(first_name, last_name, display_name, isni, viaf, description)
VALUES ('Robert', 'Martin', 'Robert C. Martin', '0000000117730278', '85276018',
        'Robert Cecil Martin (colloquially known as Uncle Bob) is an American software engineer and author. He is a co-author of the Agile Manifesto.'),
       ('George', 'Martin', 'George R. R. Martin', '0000000077784510', null,
        'George R.R. Martin is the globally bestselling author of many fine novels, including A Game of Thrones, A Clash of Kings, A Storm of Swords, A Feast for Crows, and A Dance with Dragons, which together make up the series A Song of Ice and Fire, on which HBO based the world’s most-watched television series, Game of Thrones.'),
       ('Ivo', 'Andrić', null, null, '97177322',
        'Ivo Andrić was a Yugoslav novelist, poet and short story writer who won the Nobel Prize in Literature in 1961.');