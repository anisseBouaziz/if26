<?php

return array
(
	'dsn' => 'mysql:dbname=%MYSQL_DB%;host=%MYSQL_HOST%',
	'username' => '%MYSQL_USER%',
	'password' => '%MYSQL_PWD%',
	'options' => array
	(
		PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES 'utf8'"
	)
);
