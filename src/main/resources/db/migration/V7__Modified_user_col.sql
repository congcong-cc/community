alter table user
    modify bio varchar(256) null after token;

alter table user
    modify avatar_url varchar(300) null after bio;

