package kodlamaio.hrms.business.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.EmailActivationService;
import kodlamaio.hrms.business.abstracts.JobSeekerService;
import kodlamaio.hrms.business.abstracts.MernisActivationService;
import kodlamaio.hrms.business.abstracts.UserService;
import kodlamaio.hrms.business.adapters.PersonForValidateFromMernisService;
import kodlamaio.hrms.business.constants.Messages;
import kodlamaio.hrms.core.utilities.business.BusinessRules;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorDataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.JobSeekerDao;
import kodlamaio.hrms.entities.concretes.JobSeeker;
import kodlamaio.hrms.entities.concretes.MernisActivation;
import kodlamaio.hrms.entities.dtos.JobSeekerRegisterDto;

@Service
public class JobSeekerManager implements JobSeekerService {
	private final JobSeekerDao jobSeekerDao;
	private final UserService userService;
	private final MernisActivationService mernisActivationService;
	private final EmailActivationService emailActivationService;

	@Autowired
	public JobSeekerManager(final JobSeekerDao jobSeekerDao, final UserService userService,
			final MernisActivationService mernisActivationService,
			final EmailActivationService emailActivationService) {
		this.jobSeekerDao = jobSeekerDao;
		this.userService = userService;
		this.mernisActivationService = mernisActivationService;
		this.emailActivationService = emailActivationService;
	}

	@Override
	public Result add(final JobSeeker jobSeeker) {
		jobSeekerDao.save(jobSeeker);

		return new SuccessResult(Messages.jobSeekerAdded);
	}

	private Result arePasswordMatch(final String password, final String confirmPassword) {
		return password.equals(confirmPassword) ? new SuccessResult() : new ErrorResult(Messages.passwordsNotMatch);
	}

	@Override
	public Result delete(final JobSeeker jobSeeker) {
		jobSeekerDao.delete(jobSeeker);

		return new SuccessResult(Messages.jobSeekerDeleted);
	}

	@Override
	public DataResult<List<JobSeeker>> getAll() {
		final List<JobSeeker> jobSeekers = jobSeekerDao.findAll();

		return new SuccessDataResult<List<JobSeeker>>(jobSeekers);
	}

	@Override
	public DataResult<JobSeeker> getById(final int id) {
		final Optional<JobSeeker> jobSeeker = jobSeekerDao.findById(id);

		if (jobSeeker.isEmpty())
			return new ErrorDataResult<JobSeeker>(Messages.jobSeekerNotFound);

		return new SuccessDataResult<JobSeeker>(jobSeeker.get());
	}

	@Override
	public DataResult<JobSeeker> getByIdentityNumber(final String identityNumber) {
		final Optional<JobSeeker> jobSeeker = jobSeekerDao.findByIdentityNumber(identityNumber);

		if (jobSeeker.isEmpty())
			return new ErrorDataResult<JobSeeker>(Messages.jobSeekerNotFound);

		return new SuccessDataResult<JobSeeker>(jobSeeker.get());
	}

	@Override
	public Result isNotNationalIdentityExist(final String identityNumber) {
		return jobSeekerDao.findByIdentityNumber(identityNumber).isEmpty() ? new SuccessResult()
				: new ErrorResult(Messages.jobSeekerWithIdentityNumberAlreadyExits);
	}

	@Override
	public Result register(final JobSeekerRegisterDto jobSeekerRegisterDto) {
		final Result businessRulesResult = BusinessRules.run(
				userService.isNotEmailExist(jobSeekerRegisterDto.getEmail()),
				isNotNationalIdentityExist(jobSeekerRegisterDto.getIdentityNumber()),
				arePasswordMatch(jobSeekerRegisterDto.getPassword(), jobSeekerRegisterDto.getConfirmPassword()),
				mernisActivationService.check(new PersonForValidateFromMernisService(
						Long.parseLong(jobSeekerRegisterDto.getIdentityNumber()),
						jobSeekerRegisterDto.getFirstName(),
						jobSeekerRegisterDto.getLastName(),
						jobSeekerRegisterDto.getBirthDate().getYear())));
		if (!businessRulesResult.isSuccess())
			return businessRulesResult;

		final JobSeeker jobSeeker = JobSeeker.childBuilder()
				.email(jobSeekerRegisterDto.getEmail())
				.password(jobSeekerRegisterDto.getPassword())
				.firstName(jobSeekerRegisterDto.getFirstName())
				.lastName(jobSeekerRegisterDto.getLastName())
				.identityNumber(jobSeekerRegisterDto.getIdentityNumber())
				.birthDate(jobSeekerRegisterDto.getBirthDate())
				.build();
		jobSeekerDao.save(jobSeeker);

		emailActivationService.createAndSendActivationCodeByMail(jobSeeker, jobSeeker.getEmail());
		mernisActivationService.add(MernisActivation.builder().user(jobSeeker).build());

		return new SuccessResult(Messages.jobSeekerAdded);
	}

	@Override
	public Result update(final JobSeeker jobSeeker) {
		jobSeekerDao.save(jobSeeker);

		return new SuccessResult(Messages.jobSeekerUpdated);
	}

	

}
