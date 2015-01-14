<?php
require_once('database/db.php');
require_once('model/contact.php');
require_once('model/message.php');
require_once('model/user.php');
ini_set('display_errors','Off');
$parameters = array
(
	':token' => null,
	':contact' => null,
	':message' => null
);
foreach($_GET as $key => $value)
{
	$parameters[":$key"] = $value;
}
$userParameters = array(
	array_shift(array_keys($parameters)) => array_shift($parameters)
);
$contactParameters = array(
	array_shift(array_keys($parameters)) => array_shift($parameters)
);

$json = array(
	'error' => true
);

$config = require_once('config.php');
$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);

$user = $db->find('User', 'user', 'token = :token', $userParameters);

if($user !== false)
{
	$contact = $db->find('Contact', 'contact', 'id = :contact', $contactParameters);

	if($contact != null)
	{
		$message = new Message();
		$message->contact = $contact->id;
		$message->message = $parameters[':message'];
		$message->date = date('Y-m-d H:i:s');
		$message->user = $user->id;
		$message->hasBeenRead = false;
		$message->hasBeenReceived = false;

		$id = $db->insert($message, 'message');
		if($id !== false)
		{
			$message->id = (int) $id;

			unset($message->contact);
			unset($message->user);
			 $message->sent=true;
			$json = array(
				'error' => false,
				'message' => $message
			);
		}
	}
}
// echo json_encode($json, JSON_PRETTY_PRINT);            5.4 required!!
echo json_encode($json);