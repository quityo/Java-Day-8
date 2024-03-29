package kodlamaio.hrms.business.abstracts;

import java.util.List;

import kodlamaio.hrms.core.business.BaseService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.JobAdvert;
import kodlamaio.hrms.entities.dtos.JobAdvertListDto;

public interface JobAdvertService extends BaseService<JobAdvert> {
	Result disableById(int id);

	DataResult<List<JobAdvertListDto>> getAllByIsActiveAndEmployer_CompanyNameForList(boolean isActive,
			String companyName);

	DataResult<List<JobAdvertListDto>> getAllByIsActiveForList(boolean isActive);

	DataResult<List<JobAdvertListDto>> getAllByIsActiveOrderByCreatedAtByForList(boolean isActive, String sort);
}