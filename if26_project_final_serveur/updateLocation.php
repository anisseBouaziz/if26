<?php
require_once('database/db.php');
require_once('model/contact.php');
require_once('model/message.php');
require_once('model/user.php');
require_once('model/notification.php');


ini_set('display_errors','Off');
$parameters = array
(
	':token' => null,
	':longitude' => null,
	':latitude' => null
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

$user = $db->find('User', 'user', 'token = :token', array(':token' => $parameters[':token']));

if($user !== false)
{
	$user->longitude=$_GET['longitude'];
	$user->latitude=$_GET['latitude'];
	print_r($user);
	$id=$db->update($user, 'user', 'token = :token', array(':token' =>  $parameters[':token']));

	$json = array(
		'error' => false,
	);
}
// echo json_encode($json, JSON_PRETTY_PRINT);            5.4 required!!
echo json_encode($json);