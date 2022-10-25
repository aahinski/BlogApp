DELETE FROM user_role;
DELETE FROM usr;

INSERT INTO usr(id, username, password, active) VALUE (1, 'bobby', 'fisher', true);

INSERT INTO user_role(user_id, roles) VALUE (1, 'USER');