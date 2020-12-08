package me.nurio.events;

import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventHandler;
import me.nurio.events.handler.EventListener;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

/**
 * This class proves the behavior of the EventManager registered listeners listing.
 */
public class EventManagerRegisteredListenersListingTest {

    private EventManager eventManager;
    private EventManagement eventManagement;

    @Before
    public void registerEventManager() throws NoSuchFieldException, IllegalAccessException {
        eventManager = new EventManager();
        eventManagement = getEventManagement();
    }

    @Test
    public void getRegisteredListeners_shouldNotReturnDuplicatedEntries_whenRegisteredListenerHasMultipleHandlers() {
        // Register events
        eventManager.registerEvents(new FirstTestListener());

        // Assert that they are two different event handlers registered.
        assertEquals(2, eventManagement.getRegisteredEvents().size());

        // Should return only one listener instance.
        assertEquals(1, eventManager.getRegisteredListeners().size());
    }

    @Test
    public void getRegisteredListeners_shouldReturnDuplicatedEntries_whenRegisteredListenerHasRegisteredTwice() {
        // Register events
        eventManager.registerEvents(new FirstTestListener());
        eventManager.registerEvents(new FirstTestListener());

        // Assert that they are four different event handlers registered.
        assertEquals(4, eventManagement.getRegisteredEvents().size());

        // Should return only one listener instance.
        assertEquals(2, eventManager.getRegisteredListeners().size());
    }

    @Test
    public void getRegisteredListeners_shouldReturnTwoEntries_whenThereAreTwoRegisteredListeners() {
        // Register events
        eventManager.registerEvents(new FirstTestListener());
        eventManager.registerEvents(new SecondTestListener());

        // Assert that they are three different event handlers registered.
        assertEquals(3, eventManagement.getRegisteredEvents().size());

        // Should return only one listener instance.
        assertEquals(2, eventManager.getRegisteredListeners().size());
    }

    private EventManagement getEventManagement() throws NoSuchFieldException, IllegalAccessException {
        Field field = eventManager.getClass().getDeclaredField("eventManagement");
        field.setAccessible(true);
        return (EventManagement) field.get(eventManager);
    }

    /**
     * This event listener will be used to test registered listeners listing.
     */
    private static class FirstTestListener implements EventListener {
        @EventHandler
        public void firstMethod(TestEvent event) {}

        @EventHandler
        public void secondMethod(TestEvent event) {}
    }

    /**
     * This event listener will be used to test registered listeners listing with multiple registered listeners.
     */
    private static class SecondTestListener implements EventListener {
        @EventHandler
        public void thirdMethod(TestEvent event) {}
    }

    /**
     * This event is used to create event handlers for testing registered event listeners listing.
     */
    private static class TestEvent extends Event {}

}