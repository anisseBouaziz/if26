<?php
require_once('database/db.php');
require_once('model/user.php');
ini_set('display_errors','Off');
$parameters = array
(
	':pseudo' => null,
	':email' => null,
	':password' => null
);
foreach($_GET as $key => $value)
{
	$parameters[":$key"] = $value;
}
$userParameters = array(
	array_shift(array_keys($parameters)) => array_shift($parameters)
);

$json = array(
	'error' => true,
	'description' => ""
);

$config = require_once('config.php');
$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);

$continue = true;

$user = $db->find('User', 'user', 'pseudo = :pseudo', $userParameters);
if($user !== false){
	$continue = false;
	$json['description'] ="Pseudo already used";
}

$userParameters = array(
		array_shift(array_keys($parameters)) => array_shift($parameters)
);
$user = $db->find('User', 'user', 'email = :email', $userParameters);
if($user !== false){
	$continue = false;
	$json['description'] ="Email already used";
}

if($continue !== false)
{
		$user = new User();
		$user->email = $_GET['email'];
		$user->pseudo =$_GET['pseudo'];
		$user->password = $_GET['password'];
		$user->phoneNumber = "";
		$token = md5(time() . $user->email . $user->password);
		$user->token = $token;
		

		$id = $db->insert($user, 'user');
		if($id !== false)
		{		
			$json = array(
				'error' => false,
				'token' => $token
			);
		}
	}
echo json_encode($json);