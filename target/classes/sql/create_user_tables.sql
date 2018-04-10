-- -- @author XWGuan 
-- create at 2018-03-07

-- 创建user相关数据库表

-- 创建user表
CREATE TABLE IF NOT EXISTS `autofund`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `phone_number` VARCHAR(20) NULL,
  `email` VARCHAR(128) NULL,
  `user_type` TINYINT DEFAULT 0,
   `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_name` (`name` ASC),
  UNIQUE INDEX `uk_phone_number` (`phone_number` ASC),
  UNIQUE INDEX `uk_email` (`email` ASC),
  INDEX `idx_password` (`password` ASC),
  INDEX `idx_user_type` (`user_type` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


