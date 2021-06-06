package kodlamaio.hrms.entities.concretes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import kodlamaio.hrms.core.entities.User;
import lombok.Builder;
import lombok.Data;
//import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
//@EqualsAndHashCode
@Entity
@Table(name = "job_seekers")
@PrimaryKeyJoinColumn(name = "user_id")

public class JobSeeker extends User {

	@NotBlank
	@Column(name = "first_name")
	private String firstName;
	
	@NotBlank
	@Column(name = "last_name")
	private String lastName;

	@NotBlank
	@Column(name = "identity_number")
	private String identityNumber;

	@NotNull
	@Past
	@Column(name = "birth_date")
	private LocalDate birthDate;
	
	@Builder(builderMethodName = "childBuilder")
	public JobSeeker(@NotBlank @Email  final String email,
			@NotBlank  final String password, @NotBlank  final String firstName,
			@NotBlank  final String lastName,
			@NotBlank  final String identityNumber, @NotNull @Past final LocalDate birthDate) {
		super(email, password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.identityNumber = identityNumber;
		this.birthDate = birthDate;
	}
	
	
}