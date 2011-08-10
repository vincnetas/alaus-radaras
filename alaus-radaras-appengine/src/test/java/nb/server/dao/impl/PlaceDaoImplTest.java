package nb.server.dao.impl;

import java.util.List;

import org.junit.Test;

import nb.server.dao.BaseDao;
import nb.server.dao.PlaceDao;
import nb.shared.model.Place;
import static junit.framework.Assert.*;

public class PlaceDaoImplTest extends BaseDaoImplTest<Place> {

	@Override
	public BaseDao<Place> getBaseDao() {
		return guice.getInstance(PlaceDao.class);
	}

	@Override
	public Place getSample() {
		Place result = new Place();
		
		return result;
	}
	
	@Test
	public void testFindBy() {		
		Place value = getSample();
		value.setObjectId("as");
		getBaseDao().create(value);
		
		List<Place> result = getBaseDao().findBy(value);
		assertEquals(1, result.size());
		
	}

}
