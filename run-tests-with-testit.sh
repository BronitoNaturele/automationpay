#!/bin/bash

# Загрузка переменных окружения из testit.properties, если файл существует
if [ -f testit.properties ]; then
    echo "Загружаем переменные окружения из testit.properties..."
    set -a
    source testit.properties
    set +a
else
    echo "Предупреждение: файл testit.properties не найден, используем переменные окружения из системы"
fi

# Проверка обязательных переменных
REQUIRED_VARS=("TMS_URL" "TMS_PROJECT_ID" "TMS_CONFIGURATION_ID" "TMS_TOKEN")
for VAR in "${REQUIRED_VARS[@]}"; do
    if [ -z "${!VAR}" ]; then
        echo "Ошибка: переменная окружения $VAR не задана!"
        exit 1
    fi
done

# Активация виртуального окружения
source testit-venv/bin/activate

# Запуск тестов
echo "Запускаем тесты..."
./gradlew test

# Сохраняем код возврата тестов
TEST_EXIT_CODE=$?

# Проверяем успешность тестов
if [ $TEST_EXIT_CODE -ne 0 ]; then
    echo "Тесты завершились с ошибками (код: $TEST_EXIT_CODE), но продолжаем загрузку результатов в Test IT"
else
    echo "Тесты завершены успешно"
fi

# Загрузка результатов в Test IT
echo "Загружаем результаты в Test IT..."
testit results import \
  --url "${TMS_URL}" \
  --project-id "${TMS_PROJECT_ID}" \
  --configuration-id "${TMS_CONFIGURATION_ID}" \
  --testrun-name "Kotlin Selenide Tests - $(date +%Y-%m-%d_%H-%M-%S)" \
  --results "build/test-results/junit/TEST-*.xml"

# Сохраняем код возврата команды загрузки
UPLOAD_EXIT_CODE=$?

# Деактивация окружения
deactivate

# Финальная проверка и вывод результата
echo "Процесс завершён."
if [ $TEST_EXIT_CODE -ne 0 ]; then
    echo "⚠️ Внимание: тесты завершились с ошибками (код: $TEST_EXIT_CODE)"
fi
if [ $UPLOAD_EXIT_CODE -ne 0 ]; then
    echo "❌ Ошибка: не удалось загрузить результаты в Test IT (код: $UPLOAD_EXIT_CODE)"
    exit $UPLOAD_EXIT_CODE
fi

echo "✅ Все операции выполнены успешно"