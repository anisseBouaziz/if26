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
	':contact' => null
);
foreach($_GET as $key => $value)
{
	$parameters[":$key"] = $value;
}
$userParameters = array(
	array_shift(array_keys($parameters)) => array_shift($parameters)
);
$json = array(
	'error' => true
);

$config = require_once('config.php');
$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);

$user = $db->find('User', 'user', 'token = :token', $userParameters);
$userParameters = array(
	array_shift(array_keys($parameters)) => array_shift($parameters)
);
$contact = $db->find('Contact', 'contact', 'id = :contact', $userParameters);

if($user !== false)
{
	$messages = $db->search('Message', 'message', 'contact = :contact', $userParameters);
	foreach($messages as $message)
	{

		$message->id = (int) $message->id;
		if($message->user == $user->id)
		{
			$message->sent = true;
		}
		else
		{
			if($message->hasBeenRead == false)
			{
				$message->hasBeenReceived=true;
				$message->hasBeenRead=true;
				$notification = $db->find('Notification', 'notification', 'message = :message', array(':message' => $message->id));
				if($notification == false){
					$notification = new Notification();
					$notification->user = $_GET["contact"];
					$notification->description= "The message : \"".$message->message."\" has been read by ".$user->pseudo;
					$notification->message = $message->id;
					$id = $db->insert($notification, 'notification');			
				}
				else{
					$notification->description= "The message : \"".$message->message."\" has been read by ".$user->pseudo;
					$id=$db->update($notification, 'notification', 'id = :id', array(':id' => $notification->id));
				}
				

				unset($message->sent);
				$id=$db->update($message, 'message', 'id = :id', array(':id' => $message->id));
			}
			$message->sent=false;
		}
		

		unset($message->contact);
	}
	$json = array(
		'error' => false,
		'messages' => $messages
	);
}
// echo json_encode($json, JSON_PRETTY_PRINT);            5.4 required!!
echo json_encode($json);