<?php
require_once('database/db.php');
require_once('model/contact.php');
require_once('model/message.php');
require_once('model/user.php');
require_once('model/friend_request.php');

ini_set('display_errors','Off');
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

	$friendRequests = $db->search('FriendRequest', 'friend_request', 'receiver = :id', array(':id' => $user->id));
	foreach($friendRequests as $friendRequest)
	{
			$friendRequest->asker = $db->find('User', 'user', 'id = :id', array(':id' => $friendRequest->asker));

			unset($friendRequest->asker->password);
			unset($friendRequest->asker->token);
			unset($friendRequest->asker->email);
			unset($friendRequest->asker->phoneNumber);
			unset($friendRequest->asker->id);

			unset($friendRequest->receiver);

		$friendRequest->id = (int) $friendRequest->id;
		


	}
	$json = array(
		'error' => false,
		'requests' => $friendRequests
	);
}
// echo json_encode($json, JSON_PRETTY_PRINT);            5.4 required!!
echo json_encode($json);