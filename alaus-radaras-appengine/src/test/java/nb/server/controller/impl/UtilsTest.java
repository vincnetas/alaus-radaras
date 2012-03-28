package nb.server.controller.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import nb.shared.model.Place;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void testSortPlacesByCount() {
		List<Place> places = new ArrayList<Place>();
		Place place;
		
		place = new Place();
		place.setCity("Vilnius");
		places.add(place);

		place = new Place();
		place.setCity("Kaunas");
		places.add(place);

		SortedSet<SortedSet<Place>> sortPlacesByCount = Utils.sortPlacesByCount(places);
		assertEquals(2, sortPlacesByCount.size());		
	}

	@Test
	public void testSortPlacesByCity() {
		fail("Not yet implemented");
	}

}
