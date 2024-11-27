package datamartapp.repositories.app;

import datamartapp.model.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetsRepository extends JpaRepository<Dataset, Long> {


}
