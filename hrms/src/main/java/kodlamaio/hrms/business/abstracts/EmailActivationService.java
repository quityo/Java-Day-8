package kodlamaio.hrms.business.abstracts;

import kodlamaio.hrms.core.business.BaseService;
import kodlamaio.hrms.core.entities.User;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.entities.concretes.EmailActivation;
import kodlamaio.hrms.entities.dtos.EmailActivationVerifyDto;

public interface EmailActivationService extends BaseService<EmailActivation> {
	Result createAndSendActivationCodeByMail(User user, String... emails);

	Result verify(EmailActivationVerifyDto emailActivationVerifyDto);
}
