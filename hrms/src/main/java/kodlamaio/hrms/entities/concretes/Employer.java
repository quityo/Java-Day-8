package kodlamaio.hrms.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import kodlamaio.hrms.core.entities.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
//@EqualsAndHashCode
@Entity
@Table(name = "employers")
@PrimaryKeyJoinColumn(name = "user_id")
public class Employer extends User{
	
	@NotBlank
	@Column(name = "company_name")
	private String companyName;

	@NotBlank
	@Column(name = "website")
	private String website;

	@NotBlank
	@Column(name = "corporate_email")
	private String corporateEmail;

	@NotBlank
	@Column(name = "phone")
	private String phone;

	@Builder(builderMethodName = "childBuilder")
	public Employer(@NotBlank @Email  final String email,
			@NotBlank  final String password, @NotBlank  final String companyName,
			@NotBlank  final String website, @NotBlank  final String corporateEmail,
			@NotBlank  final String phone) {
		super(email, password);
		this.companyName = companyName;
		this.website = website;
		this.corporateEmail = corporateEmail;
		this.phone = phone;
	}
}