DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order`
(
    `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    `creator`      VARCHAR(16)     NOT NULL DEFAULT 'admin' COMMENT '创建人',
    `editor`       VARCHAR(16)     NOT NULL DEFAULT 'admin' COMMENT '修改人',
    `create_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `edit_time`    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `version`      BIGINT          NOT NULL DEFAULT 1 COMMENT '版本号',
    `deleted`      TINYINT         NOT NULL DEFAULT 0 COMMENT '软删除标识',
    `order_id`     VARCHAR(32)     NOT NULL COMMENT '订单ID',
    `amount`       DECIMAL(10, 2)  NOT NULL DEFAULT 0 COMMENT '订单金额',
    `payment_time` DATETIME        NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '支付时间',
    `order_status` TINYINT         NOT NULL DEFAULT 0 COMMENT '订单状态,0:处理中,1:支付成功,2:支付失败',
    UNIQUE uniq_order_id (`order_id`),
    INDEX idx_payment_time (`payment_time`)
) COMMENT '订单表';