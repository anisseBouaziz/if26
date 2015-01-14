<?php
require_once('database/db.php');
require_once('model/user.php');
require_once('model/contact.php');
require_once('model/friend_request.php');

//ini_set('display_errors','Off');
$parameters = array
(
		':token' => null,
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

$user = $db->find('User', 'user', 'token = :token', array(':token' => $parameters[':token']));

if($user !== false){
	
	$registeredUsers = $db->searchForAll('User', 'user');
	$i=0;
	foreach($registeredUsers as $userToAnalyse ){
		unset($userToAnalyse->password);
		unset($userToAnalyse->email);
		$friendRequest = $db->find('FriendRequest', 'friend_request', 'asker = :id AND receiver = :id2', array(':id' => $userToAnalyse->id,':id2' => $user->id));
		//unset($userToAnalyse->id);
		if($friendRequest==false){
			$friendRequest = $db->find('FriendRequest', 'friend_request', 'asker = :id2 AND receiver = :id', array(':id' => $userToAnalyse->id,':id2' => $user->id));
			if($friendRequest==false && $userToAnalyse->token !== $user->token && $userToAnalyse->latitude !== null && $userToAnalyse->longitude !== null){
						$distance=get_distance_m($user->latitude,$user->longitude,$userToAnalyse->latitude,$userToAnalyse->longitude);
						if($distance>1000){
							unset($registeredUsers[$i]);
						}
						else{
							unset($userToAnalyse->token);
							unset($userToAnalyse->latitude);
							unset($userToAnalyse->longitude);
						}
					}
					else{
						unset($registeredUsers[$i]);
					}
		}
		else{
						unset($registeredUsers[$i]);
					}
		$i++;
	}
	$registeredUsers = array_values($registeredUsers);
	$json = array(
		'error' => false,
		'users' => $registeredUsers
	);
}


echo json_encode($json);


function get_distance_m($lat1, $lng1, $lat2, $lng2) {
  $earth_radius = 6378137;   // Terre = sphère de 6378km de rayon
  $rlo1 = deg2rad($lng1);
  $rla1 = deg2rad($lat1);
  $rlo2 = deg2rad($lng2);
  $rla2 = deg2rad($lat2);
  $dlo = ($rlo2 - $rlo1) / 2;
  $dla = ($rla2 - $rla1) / 2;
  $a = (sin($dla) * sin($dla)) + cos($rla1) * cos($rla2) * (sin($dlo) * sin($dlo
));
  $d = 2 * atan2(sqrt($a), sqrt(1 - $a));
  return ($earth_radius * $d);
}