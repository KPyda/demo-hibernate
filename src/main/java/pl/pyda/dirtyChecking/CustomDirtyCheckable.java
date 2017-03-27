package pl.pyda.dirtyChecking;

import java.util.Map;

/**
 * @author jpyda on 25/03/2017.
 */
public interface CustomDirtyCheckable {
	Map<String, Object> getChangedValues();
}
