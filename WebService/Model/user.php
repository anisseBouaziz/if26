<?php
class User
{
	public $id;
	public $first_name;
	public $last_name;
	public $email;
	public $password;
	public $token;

	public function toDB()
	{
		$object = get_object_vars($this);
		return $object;
	}
}