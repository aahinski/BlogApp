INSERT INTO usr(username, password, active) VALUE ('bobby', 'fisher', true);

SELECT @user := id FROM usr WHERE username='bobby';

INSERT INTO user_role(user_id, roles) VALUE (@user, 'USER');