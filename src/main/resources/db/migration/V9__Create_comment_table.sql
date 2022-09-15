create table comment
(
    id           bigint auto_increment,
    parent_id    bigint           not null comment '父类id',
    type         int              not null comment '父类型',
    commentator  int              not null comment '评论人id',
    like_count   bigint default 0 not null comment '点赞数',
    content      varchar(1024)    not null comment '评论内容',
    gmt_created  datetime         not null,
    gmt_modified datetime         not null,
    constraint comment_pk
        primary key (id)
);