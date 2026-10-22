package com.algaworks.algashop.billing.infrastructure.creditcard.fastpay;

import com.algaworks.algashop.billing.domain.model.creditcard.LimitedCreditCard;
import com.algaworks.algashop.billing.infrastructure.AbastracFastpayIT;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.Year;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Import(FastpayCreditCardTokenizationAPIClientConfig.class)
class CreditCardProviderServiceFastpayImplIT extends AbastracFastpayIT {

    @Autowired
    private CreditCardProviderServiceFastpayImpl creditCardProvider;

    @Test
    public void shouldRegisterCreditCard() {
        LimitedCreditCard limitedCreditCard = registerCard();
        Assertions.assertThat(limitedCreditCard.getGatewayCode()).isNotBlank();
    }

    @Test
    public void shouldFindRegisteredCreditCard() {
        LimitedCreditCard limitedCreditCard = registerCard();

        LimitedCreditCard limitedCreditCardFound = creditCardProvider.findById(limitedCreditCard.getGatewayCode()).orElseThrow();

        Assertions.assertThat(limitedCreditCardFound.getGatewayCode()).isEqualTo(limitedCreditCard.getGatewayCode());
    }

    @Test
    public void shouldDeleteRegisteredCreditCard() {
        LimitedCreditCard limitedCreditCard = registerCard();

        creditCardProvider.delete(limitedCreditCard.getGatewayCode());

        Optional<LimitedCreditCard> possibleCreditCard = creditCardProvider.findById(limitedCreditCard.getGatewayCode());

        Assertions.assertThat(possibleCreditCard).isEmpty();
    }

}