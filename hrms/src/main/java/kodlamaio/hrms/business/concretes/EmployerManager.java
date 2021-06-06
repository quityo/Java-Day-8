package kodlamaio.hrms.business.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.CompanyStaffVerificationService;
import kodlamaio.hrms.business.abstracts.EmailActivationService;
import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.business.abstracts.UserService;
import kodlamaio.hrms.business.constants.Messages;
import kodlamaio.hrms.core.utilities.business.BusinessRules;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorDataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.entities.concretes.CompanyStaffVerification;
import kodlamaio.hrms.entities.concretes.Employer;
import kodlamaio.hrms.entities.dtos.EmployerRegisterDto;

@Service
public class EmployerManager implements EmployerService {
	private final EmployerDao employerDao;
	private final UserService userService;
	private final EmailActivationService emailActivationService;
	private final CompanyStaffVerificationService companyStaffVerificationService;

	@Autowired
	public EmployerManager(final EmployerDao employerDao, final UserService userService,
			final EmailActivationService emailActivationService,
			final CompanyStaffVerificationService companyStaffVerificationService) {
		this.employerDao = employerDao;
		this.userService = userService;
		this.emailActivationService = emailActivationService;
		this.companyStaffVerificationService = companyStaffVerificationService;
	}

	@Override
	public Result add(final Employer employer) {
		employerDao.save(employer);
		return new SuccessResult(Messages.employerAdded);
	}

	private Result arePasswordMatch(final String password, final String confirmPassword) {
		return password.equals(confirmPassword) ? new SuccessResult() : new ErrorResult(Messages.passwordsNotMatch);
	}

	@Override
	public Result delete(final Employer employer) {
		employerDao.delete(employer);
		return new SuccessResult(Messages.employerDeleted);
	}

	@Override
	public DataResult<List<Employer>> getAll() {
		final List<Employer> employers = employerDao.findAll();
		return new SuccessDataResult<List<Employer>>(employers);
	}

	@Override
	public DataResult<Employer> getById(final int id) {
		final Optional<Employer> employer = employerDao.findById(id);

		if (employer.isEmpty())
			return new ErrorDataResult<Employer>(Messages.employerNotFound);

		return new SuccessDataResult<Employer>(employer.get());
	}

	private Result isCorporateEmail(final String email, final String website) {
		return email.split("@")[1].equals(website) ? new SuccessResult() : new ErrorResult(Messages.emailNotCorporate);
	}

	@Override
	public Result isNotCorporateEmailExist(final String corporateEmail) {
		return employerDao.findByCorporateEmail(corporateEmail).isEmpty() ? new SuccessResult()
				: new ErrorResult(Messages.employerWithCorporateEmailAlreadyExits);
	}

	@Override
	public Result register(final EmployerRegisterDto employerRegister) {
		final Result businessRulesResult = BusinessRules.run(
				userService.isNotEmailExist(employerRegister.getEmail()),
				isNotCorporateEmailExist(employerRegister.getCorporateEmail()),
				arePasswordMatch(employerRegister.getPassword(), employerRegister.getConfirmPassword()),
				isCorporateEmail(employerRegister.getCorporateEmail(), employerRegister.getWebsite()));
		if (!businessRulesResult.isSuccess())
			return businessRulesResult;

		final Employer employer = Employer.childBuilder()
				.email(employerRegister.getEmail())
				.password(employerRegister.getPassword())
				.companyName(employerRegister.getCompanyName())
				.website(employerRegister.getWebsite())
				.corporateEmail(employerRegister.getCorporateEmail())
				.phone(employerRegister.getPhone())
				.build();
		add(employer);

		emailActivationService
				.createAndSendActivationCodeByMail(employer, employer.getEmail(), employer.getCorporateEmail());
		companyStaffVerificationService.add(CompanyStaffVerification.builder().user(employer).build());

		return new SuccessResult(Messages.employerRegistered);
	}

	@Override
	public Result update(final Employer employer) {
		employerDao.save(employer);
		return new SuccessResult(Messages.employerUpdated);
	}

}