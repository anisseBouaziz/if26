<?php
class Contact
{
	public $id;
	public $initiator;
	public $contact;

	public function toDB()
	{
		$object = get_object_vars($this);
		return $object;
	}
}