alter table question
    add comment varchar(1000) null after comment_count;

alter table question
    add gmt_created datetime null;

alter table question
    add gmt_modified datetime null;

