package cn.sparrowmini.common.service.client.listener;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class HibernateListener {
	private final EntityManagerFactory entityManagerFactory;
//	private final InsertEventListener insertEventListenerClass;
//	private final IndexEventListener indexEventListenerClass;
//	private final UpdateEventListener updateEventListener;
	private final DeleteEventListener deleteEventListener;
//	private final DeleteLogEventListener deleteLogEventListener;
//	private final ReadEventListener readEventListener;

	@PostConstruct
	private void init() {
		SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
		EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
//		registry.getEventListenerGroup(EventType.PRE_INSERT).prependListener(insertEventListenerClass);
//		registry.getEventListenerGroup(EventType.POST_COMMIT_INSERT).prependListener(indexEventListenerClass);
//		registry.getEventListenerGroup(EventType.PRE_UPDATE).prependListener(updateEventListener);
		registry.getEventListenerGroup(EventType.PRE_DELETE).prependListener(deleteEventListener);
//		registry.getEventListenerGroup(EventType.POST_COMMIT_DELETE).prependListener(deleteLogEventListener);
//		registry.getEventListenerGroup(EventType.PRE_LOAD).prependListener(readEventListener);
	}
}
