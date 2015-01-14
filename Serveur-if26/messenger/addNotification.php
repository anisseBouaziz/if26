<?php
require_once('database/db.php');
require_once('model/user.php');
require_once('model/contact.php');
require_once('model/notification.php');

ini_set('display_errors','Off');
$parameters = array
(
		':message' => null,
		':description' => null,
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
$asker = $db->find('User', 'user', 'token = :token', $userParameters);

$userParameters = array(
		array_shift(array_keys($parameters)) => array_shift($parameters)
);
$receiver = $db->find('User', 'user', 'pseudo = :pseudo', $userParameters);
if($receiver === false){
	$userParameters = array(
			array_shift(array_keys($parameters)) => array_shift($parameters)
	);
	$receiver = $db->find('User', 'user', 'phoneNumber = :phoneNumber', $userParameters);
	if($receiver === false){
		$isUserFound = false;
		$json['description']="User not found";
	}
}
$continu=true;

if($isUserFound === true)
{
		if($asker->id == $receiver->id)
		{
			$continu=false;
			$json['description']="You can't send a friend request to yourself!";
		}
		else
		{
		$friendRequests = $db->search('FriendRequest', 'friend_request', 'asker = :id', array(':id' => $asker->id));
		foreach($friendRequests as $friendRequest)
		{
			if($friendRequest->receiver == $receiver->id)
			{
				$continu=false;
				$json['description']="You have already sent a friend request to this user";
			}
		}
		
		$friendRequests = $db->search('FriendRequest', 'friend_request', 'receiver = :id', array(':id' => $asker->id));
		foreach($friendRequests as $friendRequest)
		{
			if($friendRequest->asker == $receiver->id)
			{		
				$continu=false;
				$json['description']="This user has already sent you a friend request";
			}
		}
		}
		
		if($continu)
		{
			$friendRequest = new FriendRequest();
			$friendRequest->asker =$asker->id;
			$friendRequest->receiver =$receiver->id;

			$id = $db->insert($friendRequest, 'friend_request');
			if($id !== false)
			{		
				$json = array(
					'error' => false,
				);
			}
		}
		
	}
echo json_encode($json);