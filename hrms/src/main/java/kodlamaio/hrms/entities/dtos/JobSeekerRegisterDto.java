package kodlamaio.hrms.entities.dtos;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

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
public class JobSeekerRegisterDto implements Dto {
	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String confirmPassword;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String identityNumber;

	@NotNull
	@Past
	private LocalDate birthDate;

	@Builder
	public JobSeekerRegisterDto(@NotBlank @Email final String email,
			@NotBlank  final String password, @NotBlank  final String confirmPassword,
			@NotBlank final String firstName, @NotBlank final String lastName,
			@NotBlank  final String identityNumber, @NotNull @Past final LocalDate birthDate) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.identityNumber = identityNumber;
		this.birthDate = birthDate;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		
		
	}

}