```mermaid
flowchart TD
    startNode[Получить заявку] --> personal{Есть персональные данные?}
    personal -->|да| security[SECURITY_REVIEW]
    personal -->|нет| director{Риск HIGH и сумма >= 225000?}
    director -->|да| directorRoute[DIRECTOR_REVIEW]
    director -->|нет| finance{Сумма >= 500000?}
    finance -->|да| financeRoute[FINANCE_REVIEW]
    finance -->|нет| urgent{Заявка срочная?}
    urgent -->|да| fast[FAST_TRACK]
    urgent -->|нет| standard[STANDARD]
```
