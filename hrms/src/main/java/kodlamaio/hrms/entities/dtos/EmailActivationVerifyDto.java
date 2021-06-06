package kodlamaio.hrms.entities.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import kodlamaio.hrms.core.dataAccess.Dto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EmailActivationVerifyDto implements Dto {
	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String activationCode;

	@Builder
	public EmailActivationVerifyDto(@NotBlank @Email final String email, @NotBlank final String activationCode) {
		this.email = email;
		this.activationCode = activationCode;
	}
}