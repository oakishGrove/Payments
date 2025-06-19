package com.payments;

import com.payments.payment.aop.TestsPopulationHelper;
import com.payments.payment.repo.dao.CurrencyDao;
import com.payments.payment.repo.dao.TypeDao;
import com.payments.payment.services.domain.entities.Currency;
import com.payments.payment.services.domain.entities.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.List;

import static com.payments.payment.services.domain.entities.Currency.EUR;
import static com.payments.payment.services.domain.entities.Currency.USD;

@SpringBootApplication
public class PaymentsApplication {

//	@Autowired
//	private TestsPopulationHelper testsPopulationHelper;
//	@Autowired
//	private CurrencyDao currencyDao;

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
//		var context = SpringApplication.run(PaymentsApplication.class, args);

//		TestsPopulationHelper populationHelper = context.getBean(TestsPopulationHelper.class);


//		context
//
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
