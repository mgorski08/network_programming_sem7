package cf.mgorski.networkprogramming.task2.repository;

import cf.mgorski.networkprogramming.task2.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {
    Optional<City> findByName(String name);
}
