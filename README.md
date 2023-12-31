# JavaConsoleGame

1. Размер поля, количество препятствий и количество врагов вводятся в программу с помощью параметров командной строки (их наличие гарантируется):<br>
`$ java -jar game.jar --enemiesCount=10 --wallsCount=10 --size=30 --profile=production`
2. Производится проверка, можно ли разместить указанное количество врагов и препятствий на карте заданного размера. Если входные данные неверны, программа выдаст непроверенное исключение IllegalParametersException и завершит работу.
3. Враги, препятствия, игрок и целевая точка располагаются на поле случайным образом.
4. При создании карты враги, игрок, препятствия и целевая точка не должны перекрываться.
5. В начале игры карта должна быть сгенерирована так, чтобы игрок мог достичь целевой точки (игрок не должен быть заблокирован стенами и краем карты в исходной позиции).
6. Для совершения хода игроку необходимо ввести в консоль число, соответствующее направлению движения A, W, D, S (влево, вверх, вправо, вниз).
7. Если игрок не может сделать ход в указанном направлении, вводится другое число (направление).
8. Если в начале или середине игры игрок понимает, что целевая точка недостижима, он должен закончить игру, введя цифру 9 (игрок проигрывает).
9. Как только игрок сделал ход, наступает очередь его противника сделать ход в сторону игрока.
10. В режиме развития каждый шаг врага должен быть подтвержден игроком путем ввода 8.
11. При каждом шаге любого участника карта должна перерисовываться в консоли. В режиме разработки карта отображается без обновления экрана.
12. Алгоритм преследования должен учитывать местоположение целевого объекта на каждом этапе.

**Архитектура**:
1. Реализованы два проекта: Game (содержит игровую логику, точку входа в приложение, функциональность вывода и т. д.) и ChaseLogic (содержит реализацию алгоритма преследования).
2. Оба являются проектами maven, и ChaseLogic добавлен в качестве зависимости к pom.xml внутри игры.
3. Архив Game.jar переносимый: JCommander и JCDP непосредственно включены в архив. При этом все библиотеки, подключенные к проекту, объявлены как maven-зависимые.

Файл конфигурации с именем application-production.properties.

Запуск программы осуществляется командой make из корня папки Game.