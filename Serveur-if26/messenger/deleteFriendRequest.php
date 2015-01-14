<?php
require_once('database/db.php');
require_once('model/user.php');
require_once('model/contact.php');
require_once('model/friend_request.php');

ini_set('display_errors','Off');
$parameters = array
(
		':id' => null,
);
foreach($_GET as $key => $value)
{
	$parameters[":$key"] = $value;
}
$json = array(
	'error' => true,
	'description' => ""
);

$config = require_once('config.php');
$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);

$requestParameters = array(
		array_shift(array_keys($parameters)) => array_shift($parameters)
);


$friendRequest = $db->delete('friend_request', 'id = :id', $requestParameters);
if($friendRequest !== false){
	$json = array(
				'error' => false,
			);
}
else{
			$json['description']="Error";

}

echo json_encode($json);