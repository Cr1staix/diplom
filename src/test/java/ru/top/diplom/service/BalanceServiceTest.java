package ru.top.diplom.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.top.diplom.exception.balance.BalanceNotFoundException;
import ru.top.diplom.exception.balance.BalanceTooLowException;
import ru.top.diplom.model.Balance;
import ru.top.diplom.model.User;
import ru.top.diplom.repository.BalanceRepository;
import ru.top.diplom.utils.BalanceTestUtils;
import ru.top.diplom.utils.UserTestUtils;
import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BalanceService.class)
class BalanceServiceTest {

    @Autowired
    private BalanceService balanceService;

    @MockitoBean
    private BalanceRepository balanceRepository;

    @MockitoBean
    private CurrentUserService currentUserService;

    @Test
    void create_Success_WhenUserProvided() {
        //given
        User user = UserTestUtils.createTestUser();
        Balance balance = BalanceTestUtils.createTestBalance(user);

        when(balanceRepository.save(any(Balance.class))).thenReturn(balance);

        //when
        Balance result = balanceService.create(user);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getMoney()).isEqualByComparingTo(BigDecimal.ZERO);

        verify(balanceRepository, times(1)).save(any(Balance.class));
    }

    @Test
    void deposit_Success_WhenBalanceExists() {
        //given
        User user = UserTestUtils.createTestUser();
        Balance balance = BalanceTestUtils.createTestBalance(user);
        BigDecimal initialAmount = balance.getMoney();
        BigDecimal depositAmount = new BigDecimal("100.50");
        BigDecimal expectedAmount = initialAmount.add(depositAmount);

        when(currentUserService.findUser()).thenReturn(user);
        when(balanceRepository.findByUserId(user.getId())).thenReturn(Optional.of(balance));
        when(balanceRepository.save(balance)).thenReturn(balance);

        //when
        balanceService.deposit(depositAmount);

        //then
        assertThat(balance.getMoney()).isEqualByComparingTo(expectedAmount);

        verify(currentUserService, times(1)).findUser();
        verify(balanceRepository, times(1)).findByUserId(user.getId());
        verify(balanceRepository, times(1)).save(balance);
    }

    @Test
    void deposit_Failed_WhenBalanceNotFound() {
        //given
        User user = UserTestUtils.createTestUser();
        BigDecimal depositAmount = new BigDecimal("100.50");

        when(currentUserService.findUser()).thenReturn(user);
        when(balanceRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> balanceService.deposit(depositAmount))
                .isInstanceOf(BalanceNotFoundException.class);

        verify(currentUserService, times(1)).findUser();
        verify(balanceRepository, times(1)).findByUserId(user.getId());
        verify(balanceRepository, times(0)).save(any(Balance.class));
    }

    @Test
    void withdraw_Success_WhenSufficientBalance() {
        //given
        User user = UserTestUtils.createTestUser();
        Balance balance = BalanceTestUtils.createTestBalance(user);
        balance.setMoney(new BigDecimal("500.00"));

        user.setBalance(balance);

        BigDecimal priceOnHours = new BigDecimal("100.00");
        BigDecimal timesOnHours = new BigDecimal("3.00");
        BigDecimal expectedBalance = new BigDecimal("200.00");

        when(currentUserService.findUser()).thenReturn(user);
        when(balanceRepository.findByUserId(user.getId())).thenReturn(Optional.of(balance));
        when(balanceRepository.save(balance)).thenReturn(balance);

        //when
        balanceService.withdraw(priceOnHours, timesOnHours);

        //then
        assertThat(balance.getMoney().compareTo(expectedBalance)).isEqualTo(0);

        verify(currentUserService, times(1)).findUser();
        verify(balanceRepository, times(1)).findByUserId(user.getId());
        verify(balanceRepository, times(1)).save(balance);
    }

    @Test
    void withdraw_Failed_WhenBalanceNotFound() {
        //given
        User user = UserTestUtils.createTestUser();
        BigDecimal priceOnHours = new BigDecimal("100.00");
        BigDecimal timesOnHours = new BigDecimal("3.00");

        when(currentUserService.findUser()).thenReturn(user);
        when(balanceRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> balanceService.withdraw(priceOnHours, timesOnHours))
                .isInstanceOf(BalanceNotFoundException.class);

        verify(currentUserService, times(1)).findUser();
        verify(balanceRepository, times(1)).findByUserId(user.getId());
        verify(balanceRepository, times(0)).save(any(Balance.class));
    }

    @Test
    void withdraw_Failed_WhenBalanceTooLow() {
        //given
        User user = UserTestUtils.createTestUser();
        Balance balance = BalanceTestUtils.createTestBalance(user);
        balance.setMoney(new BigDecimal("200.00"));

        user.setBalance(balance);

        BigDecimal priceOnHours = new BigDecimal("100.00");
        BigDecimal timesOnHours = new BigDecimal("3.00");

        when(currentUserService.findUser()).thenReturn(user);
        when(balanceRepository.findByUserId(user.getId())).thenReturn(Optional.of(balance));

        //when then
        assertThatThrownBy(() -> balanceService.withdraw(priceOnHours, timesOnHours))
                .isInstanceOf(BalanceTooLowException.class);

        verify(currentUserService, times(1)).findUser();
        verify(balanceRepository, times(1)).findByUserId(user.getId());
        verify(balanceRepository, times(0)).save(any(Balance.class));
    }
}