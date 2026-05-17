package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

    private int attempt = 1;
    private static final int MAX_RETRY = 2; // Максимальное количество попыток

    @Override
    public boolean retry(ITestResult result) {
        if (attempt <= MAX_RETRY) {
            System.out.println("Тест '" + result.getName() + "' упал. Повторная попытка " + attempt + " из " + MAX_RETRY);
            attempt++;
            return true; // Запускаем тест заново
        }
        return false; // Не запускаем повторно
    }
}