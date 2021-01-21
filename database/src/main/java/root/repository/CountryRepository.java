package root.repository;

import org.springframework.data.repository.CrudRepository;
import root.entity.Country;

public interface CountryRepository extends CrudRepository<Country, Integer> {

    Country save(Country country);
}
