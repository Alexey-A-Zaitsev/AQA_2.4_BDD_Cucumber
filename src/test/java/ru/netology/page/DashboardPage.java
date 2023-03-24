package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

//    public DashboardPage() {
//        heading.shouldBe(visible);
//    }


    public void verifyIsDashboardPage(){
        heading.shouldBe(visible);
    }

    public int getCardBalance(int index) {
        var text = cards.get(index - 1).text();
        return extractBalance(text);
    }

    public TransferPage selectCardToTransfer(int index) {
        cards.get(index - 1).$("button").click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        return Integer.parseInt(text.substring(start + balanceStart.length(), finish));
    }

}
