package kodlamaio.hrms.entities.dtos;

import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;

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
public class JobAdvertListDto implements Dto {
	@NotBlank
	private String companyName;

	@NotBlank
	private String title;

	@Positive
	private int numberOfOpenPositions;

	@Past
	private LocalDateTime createdAt;

	@Future
	private LocalDateTime applicationDeadline;

	@Builder
	public JobAdvertListDto(@NotBlank  final String companyName,
			@NotBlank  final String title, @Positive final int numberOfOpenPositions,
			@Past final LocalDateTime createdAt, @Future final LocalDateTime applicationDeadline) {
		this.companyName = companyName;
		this.title = title;
		this.numberOfOpenPositions = numberOfOpenPositions;
		this.createdAt = createdAt;
		this.applicationDeadline = applicationDeadline;
	}
}