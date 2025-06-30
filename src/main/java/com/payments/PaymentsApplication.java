package com.payments;

import com.payments.payment.aop.TestsPopulationHelper;
import com.payments.payment.domain.entities.Type;
import com.payments.payment.repo.dao.CurrencyDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.List;

import static com.payments.payment.domain.entities.Currency.EUR;
import static com.payments.payment.domain.entities.Currency.USD;

@SpringBootApplication
public class PaymentsApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(PaymentsApplication.class);
		app.addListeners(new ApplicationListener<ApplicationReadyEvent>() {
			@Override
			public void onApplicationEvent(ApplicationReadyEvent event) {
				var context = event.getApplicationContext();
				TestsPopulationHelper populationHelper = context.getBean(TestsPopulationHelper.class);
				CurrencyDao dao = context.getBean(CurrencyDao.class);

				requirementsSetup(populationHelper, dao);
			}
		});

		app.run(args);
	}

	private static void requirementsSetup(TestsPopulationHelper populationHelper, CurrencyDao dao) {

		// migration logic :)
		if (!dao.findAll().isEmpty()) return;


		var persistedCurrency1 = populationHelper.persistCurrency(EUR.name());
		var persistedCurrency2 = populationHelper.persistCurrency(USD.name());

		populationHelper.persistType(Type.TYPE_1.name(), List.of(persistedCurrency1));
		populationHelper.persistType(Type.TYPE_2.name(), List.of(persistedCurrency2));
		populationHelper.persistType(Type.TYPE_3.name(), List.of(persistedCurrency1, persistedCurrency2));
	}
}
