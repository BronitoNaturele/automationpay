#!/bin/bash

# Создаём виртуальное окружение, если его нет
if [ ! -d "testit-venv" ]; then
    echo "Создаём виртуальное окружение..."
    python -m venv testit-venv
fi

# Активируем окружение
source testit-venv/bin/activate

# Устанавливаем зависимости
echo "Устанавливаем Test IT CLI..."
pip install -r requirements.txt

echo "Виртуальное окружение готово к использованию."