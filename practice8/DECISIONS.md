# Решения

1. статус командировки нельзя задать напрямую. его меняют только методы submit, approve, reject, cancel и complete

2. минимальная длина причины отклонения хранится в одной константе BusinessTrip.MIN_REJECTION_REASON_LENGTH = 10. проверка выполняется только в reject