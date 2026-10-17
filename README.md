# SmartShop autotests

Многомодульный Gradle-проект для тестового стенда SmartShop.

| Модуль | Назначение |
| --- | --- |
| `common` | Единый конфиг `TestConfig` и `test.properties` — адреса, учётные данные, API-маршруты, тестовые данные, селекторы и тексты интерфейса. |
| `api-tests` | API-слой: `RestApiBuilder` → `BasicAuthApi`/`BasicGoodsApi` → `ApiAssert`; тесты всех доступных эндпоинтов товаров. |
| `ui-tests` | UI-кейсы вебинара VI на Selenide, Page Object и assertion-классах с Allure-аннотациями. |

Запуск smoke-наборов из корня проекта:

```powershell
.\gradlew.bat smokeApi
.\gradlew.bat smokeUi
.\gradlew.bat smoke
```

Значения из `common/src/main/resources/test.properties` можно переопределять JVM-свойствами, например:

```powershell
.\gradlew.bat smokeApi -Dapi.url=http://localhost:8080
```
