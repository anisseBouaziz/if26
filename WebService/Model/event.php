<?php
class Message
{
	public $id;
	public $creator;
	public $description;
	public $date;
	public $guests[];

	public $sent = false;

	public function toDB()
	{
		$object = get_object_vars($this);
		unset($object['sent']);
		return $object;
	}
}