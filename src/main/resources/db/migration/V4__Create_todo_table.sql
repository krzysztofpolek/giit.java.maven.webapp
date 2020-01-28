create table todos (
    id int unsigned primary key auto_increment,
    text varchar(max) not null,
    done bit
);