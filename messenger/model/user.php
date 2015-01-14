<?php
class User
{
	public $id;
	public $pseudo;
	public $email;
	public $password;
	public $token;
	public $longitude;
	public $latitude;

	public function toDB()
	{
		$object = get_object_vars($this);
		return $object;
	}
}