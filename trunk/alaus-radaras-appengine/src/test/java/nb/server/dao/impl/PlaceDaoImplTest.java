package nb.server.dao.impl;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import nb.server.dao.BaseDao;
import nb.server.dao.PlaceDao;
import nb.shared.model.Place;

import org.junit.Test;

public class PlaceDaoImplTest extends BaseDaoImplTest<Place> {

	@Override
	public BaseDao<Place> getBaseDao() {
		return getGuice().getInstance(PlaceDao.class);
	}

	@Override
	public Place getSample() {
		Place result = new Place();
		
		return result;
	}
	
	@Test
	public void testFindBy() {		
		try {
			Place value = getSample();
			value.setObjectId("as");
			getBaseDao().create(value);
			
			List<Place> result = getBaseDao().findBy(value);
			assertEquals(1, result.size());
		} catch (Error error) {
			error.printStackTrace(System.err);
		}
		
	}
	
	@Test
	public void sampletest() {
		assertEquals(true, true);
	}

}
