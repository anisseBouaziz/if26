<?php
class Notification
{
	public $id;
	public $user;
	public $description;
	public $message;

	public function toDB()
	{
		$object = get_object_vars($this);
		return $object;
	}
}