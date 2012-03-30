package nb.server.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import nb.server.dao.BaseDao;
import nb.server.dao.BaseHistoryDao;
import nb.server.guice.NbServletModule;
import nb.shared.model.BaseHistoryObject;
import nb.shared.model.BaseHistoryObject.State;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import alaus.radaras.server.Stat;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class BaseHistoryDaoImplTest<T extends BaseHistoryObject> {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
	public abstract BaseHistoryDao<T> getBaseDao();
	
	public abstract T getSample();
	
	protected Injector getGuice() {
		return Stat.getGuice();
	}
	
    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
        
    }

	@Test
	public void testCreate() {
		T result = getBaseDao().create(getSample());
		T saved = getBaseDao().read(result.getId());
		assertEquals(result, saved);		
	}

	@Test
	public void testUpdate() {
		T value = getSample();
		value.setState(State.DELETED);
		T result = getBaseDao().create(value);
		result.setState(State.CURRENT);
		getBaseDao().update(result);
		T saved = getBaseDao().read(result.getId());
		assertEquals(State.CURRENT, saved.getState());
	}
	
	@Test
	public void testUpdateNonExistingItem() {
		T value = getSample();
		try {
			getBaseDao().update(value);
			fail("Should have failed because null id");
		} catch (DaoError error) {
		}
		
		value.setId("non existing id");
		try {
			getBaseDao().update(value);
			fail("Should have failed because non existing id");			
		} catch (DaoError error) {
		}
	}

	@Test
	public void testDelete() {
		T value = getSample();
		T result = getBaseDao().create(value);		
		getBaseDao().delete(result.getId());		
		assertEquals(null, getBaseDao().read(value.getId()));		
	}
	
	@Test
	public void testDeleteNonExistingItem() {
		try {
			getBaseDao().delete("non existing id");
			fail("Should have failed because of non existing idS");
		} catch (Error error) {
			
		}
	}

	@Test
	public void testReadCurrent() {
		T value = getSample();
		value.setState(State.CURRENT);
		value.setObjectId("objectId");
		T result = getBaseDao().create(value);
		T saved = getBaseDao().readCurrent(result.getObjectId());
		assertEquals(result.getId(), saved.getId());
		assertEquals(State.CURRENT, saved.getState());
	}
	
	@Test
	public void testReadNonExistingCurrent() {
		T value = getSample();
		value.setState(State.SUGGESTION);
		value.setObjectId("objectId");
		T result = getBaseDao().create(value);
		T saved = getBaseDao().readCurrent(result.getObjectId());
		assertNull(saved);
	}

	@Test
	public void testReadDeletedString() {
		T value = getSample();
		value.setState(State.DELETED);
		value.setObjectId("objectId");
		T result = getBaseDao().create(value);
		T saved = getBaseDao().readDeleted(result.getObjectId());
		assertEquals(result.getId(), saved.getId());
		assertEquals(State.DELETED, saved.getState());
	}

	@Test
	public void testReadDeletedDate() {		
		T value = getSample();
		value.setState(State.DELETED);
		value.setDeletionDate(new Date(0));
		getBaseDao().create(value);
		
		value.setDeletionDate(new Date(1000));
		value.setObjectId("To be returned");
		getBaseDao().create(value);
		
		List<T> deleted = getBaseDao().readDeleted(new Date(500));
		assertEquals(1, deleted.size());
		assertEquals("To be returned", deleted.get(0).getObjectId());
	}

	@Test
	public void testReadUpdated() {
		T value = getSample();
		value.setState(State.CURRENT);
		value.setApprovalDate(new Date(0));
		getBaseDao().create(value);
		
		value.setApprovalDate(new Date(1000));
		value.setObjectId("To be returned");
		getBaseDao().create(value);
		
		List<T> updated = getBaseDao().readUpdated(new Date(500));
		assertEquals(1, updated.size());
		assertEquals("To be returned", updated.get(0).getObjectId());
	}

	@Test
	public void testReadStringState() {
		T value = getSample();
		value.setState(State.CURRENT);
		value.setObjectId("To be returned");
		value.setApprovalDate(new Date(0));
		getBaseDao().create(value);
		
		value.setApprovalDate(new Date(1000));
		getBaseDao().create(value);
		
		List<T> updated = getBaseDao().read("To be returned", State.CURRENT);
		assertEquals(2, updated.size());
		assertEquals("To be returned", updated.get(0).getObjectId());
	}
	
	@Test
	public void testGetFirstInstance() {
		T value = getSample();
		value.setObjectId("qq");
		value.setCreationDate(new Date(500));
		value.setFirstCreationDate(new Date(500));
		getBaseDao().create(value);
		
		value.setCreationDate(new Date(1000));
		getBaseDao().create(value);
		T firstInstance = getBaseDao().getFirstInstance("qq");
		
		List<T> all = getBaseDao().readAll("qq");
		System.out.println(all);
		
		assertNotNull(firstInstance);
		assertEquals("qq", firstInstance.getObjectId());		
	}

	@Test
	public void testReadAll() {
		T value = getSample();
		value.setObjectId("qq");
		getBaseDao().create(value);
		getBaseDao().create(value);
		List<T> all = getBaseDao().readAll("qq");		
		assertEquals(2, all.size());
	}
}
