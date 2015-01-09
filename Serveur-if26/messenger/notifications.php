<?php
require_once('database/db.php');
require_once('model/contact.php');
require_once('model/message.php');
require_once('model/user.php');
require_once('model/notification.php');

$parameters = array
(
	':token' => null
);
foreach($_GET as $key => $value)
{
	$parameters[":$key"] = $value;
}

$json = array(
	'error' => true
);

$config = require_once('config.php');
$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);

$user = $db->find('User', 'user', 'token = :token', $parameters);

if($user !== false)
{
	$user->id = (int) $user->id;

	$notifications = $db->search('Notification', 'notification', 'user = :id', array(':id' => $user->id));

	$json = array(
		'error' => false,
		'notifications' => $notifications
	);
}
// echo json_encode($json, JSON_PRETTY_PRINT);            5.4 required!!
echo json_encode($json);