<?php
require_once('database/db.php');
require_once('model/user.php');
require_once('model/contact.php');

ini_set('display_errors','Off');
$parameters = array
(
		':token' => null,
		':pseudo' => null,
);
foreach($_GET as $key => $value)
{
	$parameters[":$key"] = $value;
}

$json = array(
	'error' => true,
	'description' => ""
);

$isUserFound = true;


$config = require_once('config.php');
$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);

$userParameters = array(
		array_shift(array_keys($parameters)) => array_shift($parameters)
);
$initiator = $db->find('User', 'user', 'token = :token', $userParameters);

$userParameters = array(
		array_shift(array_keys($parameters)) => array_shift($parameters)
);
$user = $db->find('User', 'user', 'pseudo = :pseudo', $userParameters);
if($user === false){
	$userParameters = array(
			array_shift(array_keys($parameters)) => array_shift($parameters)
	);
	$user = $db->find('User', 'user', 'phoneNumber = :phoneNumber', $userParameters);
	if($user === false){
		$isUserFound = false;
		$json['description']="User not found";
	}
}

if($isUserFound === true)
{
		$contact = new Contact();
		$contact->initiator =$initiator->id;
		$contact->contact =$user->id;

		$id = $db->insert($contact, 'contact');
		if($id !== false)
		{		
			$json = array(
				'error' => false,
				'contactId'=>$id,
				'pseudo'=>$_GET['pseudo'],
			);
		}
	}
echo json_encode($json);