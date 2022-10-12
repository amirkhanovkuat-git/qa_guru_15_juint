package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import guru.qa.data.ProgrammingLanguage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class WebTest {

    @BeforeAll
    static void setUp(){
        Configuration.browserPosition = "0x0";
        Configuration.browserSize = "1280x800";

    }
    @Disabled("Disabled until bug #50 has been fixed")
    @DisplayName("Check text in Javascript Tutorial")
    @Test
    void checkTextInJavascriptTutorial() {
        open("https://www.w3schools.com/");
        $("#search2").setValue("JavaScript Tutorial").pressEnter();
        $(".w3-panel.w3-info.intro")
                    .shouldHave(text("JavaScript is the world's most popular programming language."),
                    text("JavaScript is the programming language of the Web."),
                    text("JavaScript is easy to learn."),
                    text("This tutorial will teach you JavaScript from basic to advanced."));
    }

    @ValueSource(strings = {"HTML Certificate", "JavaScript Certificate"})
    @ParameterizedTest(name = "Checking the 'Add to cart' button text for a request {0}")
    void checkingButtonTextAddToCart(String testData) {
        open("https://www.w3schools.com/");
        $("#search2").setValue(testData).pressEnter();
        $(".atc-button--text").shouldHave(text("Add to cart"));
    }

    @CsvSource({
            "HTML Certificate, HTML Certification Exam",
            "JavaScript Certificate, JavaScript Certification Exam"
            })
    @ParameterizedTest(name = "Checking the 'Add to cart' button text for a request {0}")
    void checkingButtonTextAddToCartWithDifferentTitles(String searchText, String certificateText) {
        open("https://www.w3schools.com/");
        $("#search2").setValue(searchText).pressEnter();
        $(".product-title").shouldHave(text(certificateText));
        $(".atc-button--text").shouldHave(text("Add to cart"));
    }

    static Stream<Arguments> checkLanguageTexts() {
        return Stream.of(
                Arguments.of(
                        ProgrammingLanguage.SQL,
                        List.of("SQL is a standard language for storing, manipulating and retrieving data in databases.",
                                "Our SQL tutorial will teach you how to use SQL in: MySQL, SQL Server, MS Access, Oracle, Sybase, Informix, Postgres, and other database systems.")),
                Arguments.of(
                        ProgrammingLanguage.PYTHON,
                        List.of("Python is a popular programming language.",
                                "Python can be used on a server to create web applications.")),
                Arguments.of(
                        ProgrammingLanguage.PHP,
                        List.of("PHP is a server scripting language, and a powerful tool for making dynamic and interactive Web pages.",
                                "PHP is a widely-used, free, and efficient alternative to competitors such as Microsoft's ASP."))
        );

    }
    @MethodSource
    @ParameterizedTest
    void checkLanguageTexts(ProgrammingLanguage programmingLanguage, List<String> descriptionTexts) {
        open("https://www.w3schools.com/html/default.asp");
        $$(".w3-bar.w3-left a").find(text(programmingLanguage.name())).click();
        $$(".w3-panel.w3-info.intro p").filter(visible).shouldHave(CollectionCondition.texts(descriptionTexts));
    }

} 
