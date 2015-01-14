<?php
class FriendRequest
{
	public $id;
	public $asker;
	public $receiver;

	public function toDB()
	{
		$object = get_object_vars($this);
		return $object;
	}
}