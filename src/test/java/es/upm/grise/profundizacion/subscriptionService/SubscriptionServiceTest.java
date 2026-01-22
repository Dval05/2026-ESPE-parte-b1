package es.upm.grise.profundizacion.subscriptionService;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import es.upm.grise.profundizacion.exceptions.NullUserException;
import es.upm.grise.profundizacion.exceptions.ExistingUserException;
import es.upm.grise.profundizacion.exceptions.LocalUserDoesNotHaveNullEmailException;

public class SubscriptionServiceTest {
	
	@Test
	public void testUsuarioNuloLanzaExcepcion() {
		SubscriptionService service = new SubscriptionService();
		
		assertThrows(NullUserException.class, () -> {
			service.addSubscriber(null);
		});
	}
	
	@Test
	public void testUsuarioExistenteLanzaExcepcion() throws Exception {
		SubscriptionService service = new SubscriptionService();
		User user = new User();
		user.setEmail("andrade@gmail.com");
		user.setDelivery(Delivery.DO_NOT_DELIVER);
		service.addSubscriber(user);
		assertThrows(ExistingUserException.class, () -> {
			service.addSubscriber(user);
		});
	}
	
	@Test
	public void testUsuarioLocalConEmailLanzaExcepcion() {
		SubscriptionService service = new SubscriptionService();
		User user = new User();
		user.setEmail("andrade@gmail.com");	
		user.setDelivery(Delivery.LOCAL);
		assertThrows(LocalUserDoesNotHaveNullEmailException.class, () -> {
			service.addSubscriber(user);
		});
	}

	@Test
	public void testUsuarioValidoSeAnadeCorrectamente() throws Exception {
		SubscriptionService service = new SubscriptionService();
		User user = new User();
		user.setEmail("andrade@gmail.com");	
		user.setDelivery(Delivery.DO_NOT_DELIVER);
		service.addSubscriber(user);
		assertTrue(service.getSubscribers().contains(user));
	}
	
	@Test
	public void testUsuarioLocalSinEmailSeAgrega() throws Exception {
		SubscriptionService service = new SubscriptionService();
		User user = new User();
		user.setEmail(null);
		user.setDelivery(Delivery.LOCAL);
		service.addSubscriber(user);
		assertEquals(1, service.getSubscribers().size());
		assertTrue(service.getSubscribers().contains(user));
	}

	@Test
	public void testUsuarioConEmailDeliveryLocalLanzaExcepcion() {
		SubscriptionService service = new SubscriptionService();
		User user = new User();
		user.setEmail("jenna@gmail.com");
		user.setDelivery(Delivery.LOCAL);
		assertThrows(LocalUserDoesNotHaveNullEmailException.class, () -> {
			service.addSubscriber(user);
		});
	}
	
	@Test
	public void testUsuarioDoNotDeliverSeAgrega() throws Exception {
		SubscriptionService service = new SubscriptionService();
		User user = new User();
		user.setEmail("andrade@gmail.com");
		user.setDelivery(Delivery.DO_NOT_DELIVER);

		service.addSubscriber(user);
		
		assertEquals(1, service.getSubscribers().size());
		assertTrue(service.getSubscribers().contains(user));
	}

	@Test
	public void testUsuarioEmailNullDeliveryDoNotDeliverSeAgrega() throws Exception {
		SubscriptionService service = new SubscriptionService();
		User user = new User();
		user.setEmail(null);
		user.setDelivery(Delivery.DO_NOT_DELIVER);

		service.addSubscriber(user);
		
		assertEquals(1, service.getSubscribers().size());
		assertTrue(service.getSubscribers().contains(user));
	}

	@Test
	public void testAgregarVariosUsuariosValidos() throws Exception {
		SubscriptionService service = new SubscriptionService();
		
		User user1 = new User();
		user1.setEmail("ana@gmail.com");
		user1.setDelivery(Delivery.DO_NOT_DELIVER);
		service.addSubscriber(user1);
		User user2 = new User();
		user2.setEmail(null);
		user2.setDelivery(Delivery.LOCAL);
		service.addSubscriber(user2);
		User user3 = new User();
		user3.setEmail("jose@gmial.com");
		user3.setDelivery(Delivery.DO_NOT_DELIVER);
		service.addSubscriber(user3);
		assertEquals(3, service.getSubscribers().size());
		assertTrue(service.getSubscribers().contains(user1));
		assertTrue(service.getSubscribers().contains(user2));	
		assertTrue(service.getSubscribers().contains(user3));
	}

	public void testAgregarUsuarioExistenteDespuesDeAgregarVariosLanzaExcepcion() throws Exception {
		SubscriptionService service = new SubscriptionService();
		
		User user1 = new User();
		user1.setEmail("anita@gmail.com");
		user1.setDelivery(Delivery.DO_NOT_DELIVER);
		service.addSubscriber(user1);
		User user2 = new User();
		user2.setEmail(null);
		user2.setDelivery(Delivery.LOCAL);
		service.addSubscriber(user2);
		User user3 = new User();
		user3.setEmail("jen@gmail.com");
		user3.setDelivery(Delivery.DO_NOT_DELIVER);
		service.addSubscriber(user3);
		assertThrows(ExistingUserException.class, () -> {
			service.addSubscriber(user2);
		});
	}	
}
