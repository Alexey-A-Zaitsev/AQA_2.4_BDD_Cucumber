package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.SessionStorage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement amountInput = $("[data-test-id='amount'] input");
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement transferHead = $(byText("Пополнение карты"));

    public TransferPage() {
        transferHead.shouldBe(visible);
    }

    public DashboardPage makeTransfer(String amountToTransfer, String cardNumber) {
        amountInput.setValue(amountToTransfer);
        fromInput.setValue(cardNumber);
        transferButton.click();
        return new DashboardPage();
    }

}
