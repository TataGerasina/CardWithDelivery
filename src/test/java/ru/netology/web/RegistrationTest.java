package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class RegistrationTest {

    private String selectionDate(int addDate, String pattern) {
        return LocalDate.now().plusDays(addDate).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldRegisterByAccountNumberDOMModification() {
        open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue("Казань");
        String planDate = selectionDate(4,"dd.mm.yyyy");

        $("[data-test-id='date'] input").sendKeys(Keys.CLEAR);
        $("[data-test-id='date'] input").setValue(planDate);
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='phone' input]").setValue("+79000000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planDate));
    }
}
