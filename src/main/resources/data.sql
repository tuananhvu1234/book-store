/* users */
INSERT INTO `book_store`.`users`
    (`id`, `username`, `password`, `status`)
VALUES (1, 'author1', '$2a$12$hBeND2psIjCRKaC5gZvHbO9z53wknsapdnl5GqmuaFORmm3V5zFYq', 1),
       (2, 'author2', '$2a$04$MqyckI.KMbzQn72vhHZRB.Y0PFTLBMaH8prObHK/e1w1zXzrG1bZS', 1),
       (3, 'author3', '$2a$04$8UtG9/7EWYnSd5VwZqkP8udiHxJHb5x3zSNjzKNIzbDN6AHEbHtTK', 1);


/* authors */
INSERT INTO `book_store`.`authors`
    (`user_id`, `first_name`, `last_name`, `pen_name`)
VALUES (1, 'John', 'Adam', 'Jeff Mak'),
       (2, 'Janne', 'Wil', 'Windy'),
       (3, 'Jack', 'Jaz', 'Dark Washer');

/* categories */
INSERT INTO `book_store`.`categories`
    (`id`, `name`, `description`)
VALUES (1, 'Comedy', 'Describe the Comedy genre'),
       (2, 'Action', 'Describe the Action genre'),
       (3, 'Horror', 'Describe the Horror genre'),
       (4, 'Detective', 'Describe the Detective genre');

/* books */
INSERT INTO `book_store`.`books`
(`id`, `title`, `short_description`, `full_description`, `author_id`, `price`, `quantity`)
VALUES (1, 'Book 1', 'Book 1 Short Description', 'Book 1 Full Description', 1, 20000, 100),
       (2, 'Book 2', 'Book 2 Short Description', 'Book 2 Full Description', 2, 30000, 99),
       (3, 'Book 3', 'Book 3 Short Description', 'Book 3 Full Description', 3, 40000, 90);

/* category_book */
INSERT INTO `book_store`.`category_book`
    (`book_id`, `category_id`)
VALUES (1, 2),
       (1, 3),
       (1, 4),
       (2, 1),
       (2, 4),
       (3, 2),
       (3, 4);