package pl.pyda.dirtyChecking;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.CustomEntityDirtinessStrategy;
import org.hibernate.Session;
import org.hibernate.persister.entity.EntityPersister;

/**
 * @author jpyda on 25/03/2017.
 */
@Slf4j
public class Strategy implements CustomEntityDirtinessStrategy {
	public static final Strategy INSTANCE = new Strategy();

	private int canDirtyCheckCount = 0;
	private int isDirtyCount = 0;
	private int resetDirtyCount = 0;
	private int findDirtyCount = 0;

	public boolean canDirtyCheck(Object entity, EntityPersister persister, Session session) {
		canDirtyCheckCount++;
		return CustomDirtyCheckable.class.isInstance(entity);
	}

	public boolean isDirty(Object entity, EntityPersister persister, Session session) {
		isDirtyCount++;
		boolean isDirty = !CustomDirtyCheckable.class.cast(entity).getChangedValues().isEmpty();
		log.info(entity + ": isDirty = " + isDirty);
		return isDirty;
	}

	public void resetDirty(Object entity, EntityPersister persister, Session session) {
		resetDirtyCount++;
		log.info(entity + ": resetDirty");
		CustomDirtyCheckable.class.cast(entity).getChangedValues().clear();
	}

	public void findDirty(final Object entity, EntityPersister persister,
	                      Session session, DirtyCheckContext dirtyCheckContext) {
		findDirtyCount++;
		log.info(entity + ": findDirty");
		dirtyCheckContext.doDirtyChecking(
				attributeInformation -> {
					String attrName = attributeInformation.getName();
					CustomDirtyCheckable checkable = CustomDirtyCheckable.class.cast(entity);
					boolean isDirty = checkable.getChangedValues().containsKey(attrName);
					log.info(entity + ": isDirty for " + attrName + " = " + isDirty);
					return isDirty;
				}
		);
	}

	void resetState() {
		canDirtyCheckCount = 0;
		isDirtyCount = 0;
		resetDirtyCount = 0;
		findDirtyCount = 0;
	}

	@Override
	public String toString() {
		return "Strategy [canDirtyCheckCount=" + canDirtyCheckCount + ", isDirtyCount=" + isDirtyCount
				+ ", resetDirtyCount=" + resetDirtyCount + ", findDirtyCount=" + findDirtyCount + "]";
	}
}
