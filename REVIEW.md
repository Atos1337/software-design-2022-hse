# Реализация новых комманд

Всё очень удобно, так как для добавления новой команды требуется всего лишь создать реализацию интерфейса Command и добавить создание новой команды в CommandBuilder.

Конкретно для ls и cd пришлось только дополнительно расширить Environment для работы с текущей рабочей директорией, что не вызвало никаких сложностей.

Также было удобно, что при работе с файлами везде создавался Path, поэтому не пришлось делать лишние методы в Environment или сильно менять код существующих команд.

# Недостатки

Почему не Kotlin?)

Command::execute принимает на вход строчку и возвращает строчку, а не какой-нибудь InputStream и OutputStream, что при развитии приложения может вызвать трудности, если понадобится, например,
реализовать пайпы, как в баше.

Не очень красивым показалась регистрация команд через множественные if/else.

# Тесты

Тесты хорошо покрывают функциональность, удобно по ним перемещаться и оценивать на сколько качественно протестирован код, также удобно добавлять новые тесты.