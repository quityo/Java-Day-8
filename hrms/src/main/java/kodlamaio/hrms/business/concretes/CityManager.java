package kodlamaio.hrms.business.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.CityService;
import kodlamaio.hrms.business.constants.Messages;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorDataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CityDao;
import kodlamaio.hrms.entities.concretes.City;

@Service
public class CityManager implements CityService {
	private CityDao cityDao;

	@Autowired
	public CityManager(final CityDao cityDao) {
		this.cityDao = cityDao;
	}

	@Override
	public Result add(final City city) {
		cityDao.save(city);
		return new SuccessResult(Messages.CityAdded);
	}

	@Override
	public Result delete(final City city) {
		cityDao.delete(city);
		return new SuccessResult(Messages.CityDeleted);
	}

	@Override
	public DataResult<List<City>> getAll() {
		final List<City> cities = cityDao.findAll();
		return new SuccessDataResult<List<City>>(cities);
	}

	@Override
	public DataResult<City> getById(final int id) {
		final Optional<City> city = cityDao.findById((short) id);

		if (city.isEmpty())
			return new ErrorDataResult<City>(Messages.CityNotFound);

		return new SuccessDataResult<City>(city.get());
	}

	@Override
	public DataResult<City> getByName(final String name) {
		final Optional<City> city = cityDao.findByName(name);

		if (city.isEmpty())
			return new ErrorDataResult<City>(Messages.CityNotFound);

		return new SuccessDataResult<City>(city.get());
	}

	@Override
	public DataResult<List<City>> getByNameContains(final String name) {
		final Optional<List<City>> city = cityDao.findByNameContains(name);

		return new SuccessDataResult<List<City>>(city.get());
	}

	@Override
	public Result update(final City city) {
		cityDao.save(city);
		return new SuccessResult(Messages.CityUpdated);
	}

}