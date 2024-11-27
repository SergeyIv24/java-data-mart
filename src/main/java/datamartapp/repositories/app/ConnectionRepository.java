package datamartapp.repositories.app;

import org.springframework.data.jpa.repository.JpaRepository;
import datamartapp.model.Connection;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends JpaRepository <Connection, Long> {

}
