package cf.mgorski.networkprogramming.task2.repository;

import cf.mgorski.networkprogramming.task2.model.City;
import cf.mgorski.networkprogramming.task2.model.SampledWeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface WeatherDataRepository extends JpaRepository<SampledWeatherData, Long> {
    Optional<SampledWeatherData> findFirstByOrderByTimestampAsc();

    Optional<SampledWeatherData> findFirstByOrderByTimestampDesc();

    Optional<SampledWeatherData> findFirstByCityOrderByTimestampDesc(City city);

    Iterable<SampledWeatherData> findByCityAndTimestampAfter(City city, Timestamp timestamp);

    Iterable<SampledWeatherData> findByTimestampAfter(Timestamp timestamp);
}
