package ru.top.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.top.diplom.service.BalanceService;
import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/balance")
public class BalanceController {

    private final BalanceService balanceService;

    @PatchMapping("/deposit")
    public ResponseEntity<String> deposit(BigDecimal amount) {

        balanceService.deposit(amount);

        return ResponseEntity.ok("Баланс пополнен на сумму: " + amount);
    }

    @PatchMapping("/withdraw")
    public ResponseEntity<String> withdraw(BigDecimal priceOnHours, BigDecimal timesOnHours) {

        balanceService.withdraw(priceOnHours, timesOnHours);

        return ResponseEntity.ok("Заказ на сумму: " + priceOnHours.multiply(timesOnHours) + " оплачен");
    }

}
