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
public class EmployerRegisterDto implements Dto {
	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String confirmPassword;

	@NotBlank
	private String companyName;

	@NotBlank
	private String website;

	@NotBlank
	private String corporateEmail;

	@NotBlank
	private String phone;

	@Builder
	public EmployerRegisterDto(@NotBlank  final String companyName,
			@NotBlank  final String website,
			@NotBlank  final String phone,
			@NotBlank  final String corporateEmail,
			@NotBlank @Email  final String email,
			@NotBlank  final String password, @NotBlank  final String confirmPassword	 
			 ) {
		this.companyName = companyName;
		this.website = website;
		this.phone = phone;
		this.corporateEmail = corporateEmail;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

}