Предварительно запустить, настроить IntelliJ IDEA, DBeaver (либо другой инструмент администрирования базы данных), Docker Desktop, Google Chrome (либо другой браузер).

### Выполнение шагов для запуска авто-тестов:
##### 1) Клонировать репозиторий через команду:
 
        git clone git@github.com:Tanya174/Diploma-qa-45.git

##### 2) Перейти в папку Diploma

##### 3) Запустить контейнеры Docker через следующую команду:

        docker-compose up

##### 4) Запустить приложение через следующие команды:

         а. На базе данных MySQL: java -jar aqa-shop.jar -Dspring.datasource.url=jdbc:mysql://localhost:3306/app

         б. На базе данных PostgreSQL: java -jar aqa-shop.jar -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app 

##### 5) Запустить авто-тесты через следующие команды:

         а. На базе данных MySQL: .\gradlew test "-Ddb.url=jdbc:mysql://localhost:3306/app" "-Dapp.userDB=app" "-Dapp.password=pass"

         б. На базе данных PostgreSQL: .\gradlew test "-Ddb.url=jdbc:postgresql://localhost:5432/app" "-Dapp.userDB=app" "-Dapp.password=pass"

##### 6) Сформировать отчет с использованием Allure через команду: 
         .\gradlew allureReport

##### 7) Открыть отчет в браузере через команду: 
         .\gradlew allureServe
