package com.jmurphey.eventmanager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jmurphey.eventmanager.models.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
	
}
