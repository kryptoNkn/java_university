# Activity-диаграмма

```mermaid
flowchart TD
    start([Начало]) --> read["Прочитать строку Scanner: quantity;unitPrice;discountPercent"]
    read --> missing{"A1. Строка пустая, файл отсутствует или поле между ; пустое?"}
    missing -->|да| msgMissing["Сообщение: отсутствует значение"]
    missing -->|нет| format{"Три поля разбираются как int, double и double?"}
    format -->|нет| msgFormat["Сообщение: неверный формат"]
    format -->|да| qty{"A1. Количество от 1 до 10000 включительно?"}
    qty -->|нет| msgQty["Сообщение: некорректное количество"]
    qty -->|да| price{"A1. Цена больше 0 и не больше 5000000?"}
    price -->|нет| msgPrice["Сообщение: некорректная цена"]
    price -->|да| discount{"A2. Скидка от 0 до 45 процентов включительно?"}
    discount -->|нет| msgDiscount["Сообщение: некорректная скидка"]
    discount -->|да| base["База = quantity * unitPrice"]
    base --> after["Сумма после скидки = база * (1 - скидка / 100)"]
    after --> vat["НДС = сумма после скидки * 20 / 100"]
    vat --> total["Итог = сумма после скидки + НДС"]
    total --> out["Вывод итога: два знака после запятой и руб."]
    msgMissing --> stop([Конец])
    msgFormat --> stop
    msgQty --> stop
    msgPrice --> stop
    msgDiscount --> stop
    out --> stop
```

A1 — это `isValidQuantity`, `isValidUnitPrice` и разбор пустой строки в `evaluateLine`. A2 — это `isValidDiscount`: скидка 0–45%. После ветки «да» код идёт в `calculateTotal`: база, скидка, затем НДС. Повтор той же строки считается отдельным заказом и проходит тот же путь.
