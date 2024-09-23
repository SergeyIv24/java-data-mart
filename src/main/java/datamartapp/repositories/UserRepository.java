package datamartapp.repositories;

import datamartapp.model.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query(value = "SELECT * " +
            "FROM users AS u " +
            "WHERE (u.username ILIKE %?1% OR ?1 IS NULL)",
            nativeQuery = true)
    List<User> findByUserNameContains(String usernameSearch, Pageable pageable);





}
