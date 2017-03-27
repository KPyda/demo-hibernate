package pl.pyda.entity;

import lombok.Getter;
import pl.pyda.dirtyChecking.CustomDirtyCheckable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author jpyda on 25/03/2017.
 */
@Getter
@MappedSuperclass
public class BaseEntity implements CustomDirtyCheckable {

	@Id
//	@GeneratedValue
	protected Long id;
	protected transient Map<String, Object> changedValues = new HashMap<>();
	private String uuid = UUID.randomUUID().toString();

	public int hashCode() {
		return Objects.hashCode(uuid);
	}

	public boolean equals(Object that) {
		return this == that || that instanceof BaseEntity && Objects.equals(uuid, ((BaseEntity) that).uuid);
	}

	public Map<String, Object> getChangedValues() {
		return changedValues;
	}
}
