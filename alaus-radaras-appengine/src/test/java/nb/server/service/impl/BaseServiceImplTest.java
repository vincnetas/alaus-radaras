package nb.server.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.List;

import nb.server.guice.NbServletModule;
import nb.server.service.BaseService;
import nb.server.service.UserService;
import nb.shared.model.BaseObject;
import nb.shared.model.BaseObject.State;
import nb.shared.model.User;
import nb.shared.model.User.Role;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class BaseServiceImplTest<T extends BaseObject> {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
	protected Injector guice = Guice.createInjector(new NbServletModule());
	
	public abstract BaseService<T> getBaseService();
		
	public abstract T getSample();
	
    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    protected User getSimpleUser() {
    	User result = new User();
    	result.setOwnerForCompanies(new HashSet<String>());
    	result.setOwnerForPlaces(new HashSet<String>());
    	result.setRole(Role.USER);
    	result.setUserId("userId");
    	
    	return result;
    }
    
    protected User getAdminUser() {
    	User result = new User();
    	result.setOwnerForCompanies(new HashSet<String>());
    	result.setOwnerForPlaces(new HashSet<String>());
    	result.setRole(Role.ADMIN);
    	result.setUserId("userId");
    	
    	return result;
    }
    
    private T add(T object, boolean approve) {
		BaseServiceImpl<T> bs = (BaseServiceImpl<T>) getBaseService();
		UserService userService = Mockito.mock(UserService.class);
		if (approve) {
			Mockito.when(userService.getCurrentUser()).thenReturn(getAdminUser());
		} else {
			Mockito.when(userService.getCurrentUser()).thenReturn(getSimpleUser());
		}
		bs.setUserService(userService);
		
		bs.suggest(object);

		return object;
    }
    
	@Test
	public void testSuggestUser() {
		T sample = getSample();
		sample.setObjectId("qq");

		add(sample, false);
		add(sample, false);
		
		List<T> suggestions = getBaseService().getSuggestions("qq");
		assertEquals(2, suggestions.size());
		assertEquals(0, getBaseService().getSuggestions("non").size());
	}

	@Test
	public void testSuggestAdmin() {
		T sample = getSample();
		sample.setObjectId("qq");

		add(sample, true);
		add(sample, true);
		
		List<T> suggestions = getBaseService().getSuggestions("qq");
		assertEquals(0, suggestions.size());
		assertNotNull(getBaseService().getCurrent("qq"));
	}

	
	@Test
	public void testApproveUser() {
		T value = getSample();
		value.setObjectId("qq");
		
		BaseServiceImpl<T> bs = (BaseServiceImpl<T>) getBaseService();
		UserService userService = Mockito.mock(UserService.class);
		Mockito.when(userService.getCurrentUser()).thenReturn(getSimpleUser());
		bs.setUserService(userService);

		bs.suggest(value);
		List<T> suggestions = bs.getSuggestions("qq");
		
		assertEquals(1, suggestions.size());
		T suggestion = suggestions.get(0);
		assertEquals(State.SUGGESTION, suggestion.getState());
		try {
			bs.approve(suggestion.getId());
			fail("Can't approve.");
		} catch (Error error) {
			/*
			 * Should fail
			 */
		}
	}

	@Test
	public void testApproveAdmin() {
		T sample = getSample();
		sample.setObjectId("qq");

		BaseServiceImpl<T> bs = (BaseServiceImpl<T>) getBaseService();
		UserService userService = Mockito.mock(UserService.class);
		Mockito.when(userService.getCurrentUser()).thenReturn(getSimpleUser());
		bs.setUserService(userService);

		bs.suggest(sample);
		List<T> suggestions = bs.getSuggestions("qq");
		
		assertEquals(1, suggestions.size());
		T suggestion = suggestions.get(0);
		assertEquals(State.SUGGESTION, suggestion.getState());
		
		Mockito.when(userService.getCurrentUser()).thenReturn(getAdminUser());
		
		bs.approve(suggestion.getId());
		T current = bs.getCurrent("qq");
		assertNotNull(current);
		assertEquals(State.CURRENT, current.getState());
	}

	@Test
	public void testReject() {
		T sample = getSample();
		sample.setObjectId("qq");
		
		BaseServiceImpl<T> bs = (BaseServiceImpl<T>) getBaseService();
		UserService userService = Mockito.mock(UserService.class);
		Mockito.when(userService.getCurrentUser()).thenReturn(getSimpleUser());
		bs.setUserService(userService);

		bs.suggest(sample);
		List<T> suggestions = bs.getSuggestions("qq");
		
		assertEquals(1, suggestions.size());
		T suggestion = suggestions.get(0);
		assertEquals(State.SUGGESTION, suggestion.getState());
		
		Mockito.when(userService.getCurrentUser()).thenReturn(getAdminUser());
		
		bs.reject(suggestion.getId());
		T current = bs.getCurrent("qq");
		assertNull(current);
	}

	@Test
	public void testDelete() {
		T sample = getSample();
		sample.setObjectId("qq");

		add(sample, true);
		T current = getBaseService().getCurrent("qq");
		getBaseService().delete("qw");
		
		
	}

	@Test
	public void testGetDeleted() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUpdated() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSuggestions() {
		fail("Not yet implemented");
	}

}
