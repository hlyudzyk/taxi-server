package com.taxiapp.main.persistance.repositories;

import com.taxiapp.main.persistance.entities.Message;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Message, UUID> {

}
