drop table if exists t_order;

create table t_order
(
    id          bigint unsigned not null auto_increment primary key,
    order_id    varchar(64) not null comment '订单ID',
    create_time datetime not null default current_timestamp,
    edit_time   datetime not null default current_timestamp on update current_timestamp
) comment '订单表';