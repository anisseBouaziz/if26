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

	$contacts = $db->search('Contact', 'contact', 'initiator = :id OR contact = :id', array(':id' => $user->id));

	foreach($contacts as $contact)
	{
		if($user->id != $contact->initiator)
		{
			$contact->contact = $db->find('User', 'user', 'id = :id', array(':id' => $contact->initiator));

			unset($contact->contact->password);
			unset($contact->contact->token);
		}
		elseif($user->id != $contact->contact)
		{
			$contact->contact = $db->find('User', 'user', 'id = :id', array(':id' => $contact->contact));
			
			unset($contact->contact->password);
			unset($contact->contact->token);
		}
		$contact->contact->id = (int) $contact->contact->id;
		$contact->id = (int) $contact->id;
		
		


		$contact->message = $db->find('Message', 'message', 'contact = :contact', array(':contact' => (int) $contact->id), 'date desc');
		
		if(!$contact->message)
		{
			unset($contact->message);
		}
		else
		{
			$contact->message->id = (int) $contact->message->id;

			unset($contact->message->contact);
			unset($contact->message->user);
		}
		
		$messages = $db->search('Message', 'message', 'contact = :contact', array(':contact' => $contact->id));
		foreach($messages as $message)
		{
			$message->id = (int) $message->id;
			if($message->user == $user->id)
			{
				
			}
			else
			{
				if($message->hasBeenReceived == false)
				{
					
					$message->hasBeenReceived=true;
					$notification = new Notification();
					if($contact->initiator == $user->id)
					{
						$notification->user = $contact->contact->id;
					}
					else
					{					

						$notification->user = $contact->initiator;
					}
					$notification->message = $message->id;
					$notification->description= "The message : \"".$message->message."\" has been received by ".$user->pseudo;

					$id = $db->insert($notification, 'notification');

					unset($message->sent);
					$id=$db->update($message, 'message', 'id = :id', array(':id' => $message->id));
				}
			}
			
			
		}
	}
	$json = array(
		'error' => false,
		'contacts' => $contacts
	);
}
// echo json_encode($json, JSON_PRETTY_PRINT);            5.4 required!!
echo json_encode($json);