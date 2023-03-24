package ru.netology.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;


public class TemplateSteps {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;

    @Пусть("открыта страница с формой авторизации {string}")
    public void openAuthPage(String url) {
        loginPage = open(url, LoginPage.class);
    }

    @Когда("пользователь пытается авторизоваться с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        verificationPage = loginPage.validLogin(login, password);
    }

    @И("пользователь вводит проверочный код 'из смс' {string}")
    public void setValidCode(String verificationCode) {
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Тогда("происходит успешная авторизация и пользователь попадает на страницу 'Личный кабинет'")
    public void verifyDashboardPage() {
        dashboardPage.verifyIsDashboardPage();
    }

    @Тогда("появляется ошибка о неверном коде проверки")
    public void verifyCodeIsInvalid() {
        verificationPage.verifyCodeIsInvalid();
    }

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void userIsLoggedIn(String login, String password) {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var verificationPage = loginPage.validLogin(login,password);
        dashboardPage = verificationPage.validVerify(DataHelper.getVerificationCode().getCode());
    }

    @Когда("он переводит {string} рублей с карты с номером {string} на свою {int} карту с главной страницы")
    public void makeTransfer(String amount, String debitCardNumber, int creditCardNumber) {
        var transferPage = dashboardPage.selectCardToTransfer(creditCardNumber);
        dashboardPage = transferPage.makeTransfer(amount, debitCardNumber);
    }

    @Тогда("баланс его {} карты из списка на главной странице должен стать {} рублей")
    public void verifyCreditBalance(int creditCardNumber, int expectedCreditBalance) {
        var actualCreditBalance = dashboardPage.getCardBalance(creditCardNumber);
        Assertions.assertEquals(expectedCreditBalance, actualCreditBalance);
    }
}
