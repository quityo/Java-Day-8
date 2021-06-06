package kodlamaio.hrms.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "cities")
public class City {
	@Id
	@Column(name = "id")
	private short id;

	@NotBlank
	@Column(name = "name")
	private String name;

	@Builder
	public City(final short id, @NotBlank  final String name) {
		this.id = id;
		this.name = name;
	}

}