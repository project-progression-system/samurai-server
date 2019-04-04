package edu.cnm.deepdive.teamsamurai.model.dao;

import edu.cnm.deepdive.teamsamurai.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {

  List<User> findAllByOrderByNameAsc();

  Optional<User> findFirstByIdAndType(UUID id, User.Type type);
}
