package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.business.BaseService;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.JobSeeker;
import kodlamaio.hrms.entities.dtos.JobSeekerRegisterDto;

public interface JobSeekerService extends BaseService<JobSeeker> {
	
	Result register(JobSeekerRegisterDto jobSeekerRegisterDto);
	Result isNotNationalIdentityExist(String identityNumber);
	DataResult<JobSeeker> getByIdentityNumber(String identityNumber);
	
}