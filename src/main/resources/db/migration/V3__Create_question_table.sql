create table question
(
    id            int auto_increment,
    title         varchar(50)   null,
    description   varchar(1000) null,
    tag           varchar(256)  null,
    interest      int default 0 null,
    comment_count int default 0 null,
    view_count    int default 0 null,
    red_heart     int default 0 null,
    constraint question_pk
        primary key (id)
);

