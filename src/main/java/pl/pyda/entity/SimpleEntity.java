package pl.pyda.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * @author jpyda on 25/03/2017.
 */
@Entity
@NoArgsConstructor
@Getter
@ToString
public class SimpleEntity extends BaseEntity {

	private String name;

	public SimpleEntity(String name, Long id) {
		this.id = id;
		this.name = name;
	}

	public SimpleEntity setId(Long id) {
		this.id = id;
		changedValues.put("id", this.id);
		return this;
	}

	public SimpleEntity setName(String name) {
		this.name = name;
		changedValues.put("name", this.name);
		return this;
	}
}
