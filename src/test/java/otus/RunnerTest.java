package otus;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)

@CucumberOptions(

        plugin = {"html:target/cucumber"},
        features = {"src/test/resources/features/"}, // путь к папке с .feature файлами
        glue = {"otus.steps", "otus.hooks"}, // пакет, в котором находятся классы с реализацией шагов и «хуков»
       // tags = {"@"}, // фильтр запускаемых тестов по тэгам
        dryRun = false, // если true, то сразу после запуска теста фреймворк проверяет, все ли шаги теста разработаны, если нет, то выдает предупреждение
        //strict = false, // если true, то при встрече неразработанного шага тест остановится с ошибкой
        snippets = CucumberOptions.SnippetType.CAMELCASE // формат генерации кода степов кукумбера
)
public class RunnerTest {


}
