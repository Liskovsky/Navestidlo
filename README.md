# Mobilní Výuková Aplikace "Návěstidlo"

Tato bakalářská práce se zaměřuje na modernizaci a implementaci mobilní aplikace **"Návěstidla"**, která slouží k procvičování **železničních návěstidel**. Aplikace podporuje vzdělávání budoucích strojvedoucích moderními výukovými přístupy.

Původní verze aplikace byla vytvořena ve vývojovém prostředí Kodular, ale kvůli nevhodným technologiím byla **převedena do jazyka Kotlin** v prostředí **Android Studia**.

---

## Cíle a Požadavky

Cílem bylo vytvořit flexibilnější a robustnější řešení s možností dalšího rozšiřování, včetně jazykových mutací a nových moderních funkcí. Nová verze usnadňuje osvojení složité teorie železničních návěstidel, která často kombinují až osm světelných znaků s doplňkovými tabulkami.

### Klíčové požadavky:

* **Funkčnost a obsah:** Zahrnout moduly **Trenažer, Kvíz a Test** pro interaktivní trénink a ověřování znalostí.
* **Uživatelský design:** Intuitivní a snadno ovladatelné rozhraní, vizuálně inspirované železničním prostředím.
* **Technické požadavky:** Optimalizace pro **Android 14 a vyšší** a **offline dostupnost** obsahu.
* **Podpora a aktualizace:** Snadná správa a aktualizace obsahu prostřednictvím integrace s **cloudovým řešením Firestore**.
* **Dostupnost:** Aplikace bude dostupná **zdarma**.
* **Zpětná vazba:** Okamžitá a srozumitelná zpětná vazba uživatelům, včetně sledování pokroku.

---

## Technická Architektura a Implementace

### Architektura Aplikace
Architektura vychází z návrhového vzoru **Model-View-ViewModel (MVVM)**, který zajišťuje jasné oddělení logiky (ViewModel) od uživatelského rozhraní (UI).

* **UI (Jetpack Compose):** Reaguje na vstupy od uživatele a zobrazuje data.
* **ViewModel:** Obsahuje veškerou logiku aplikace, spravuje stav a transformuje data.
* **Data (NavestiRepository):** Načítá data z **Firebase Firestore**.


### Použité Technologie
* **Jazyk:** Kotlin.
* **UI Framework:** **Jetpack Compose** (Deklarativní tvorba uživatelského rozhraní).
* **Databáze:** **Firebase Firestore** (Cloudová databáze pro ukládání kombinací signálů a automatickou synchronizaci dat).
* **Monetizace (Experimentální):** Google Mobile Ads SDK.

### Funkční Moduly

| Modul | Účel | Primární Funkčnost |
| :--- | :--- | :--- |
| **Trenažer** | **Procvičování** | Simuluje reálné podmínky, zobrazuje popis vybrané návěsti. |
| **Kvíz** | **Interaktivní učení** | Uživatel nastavuje návěstidlo podle náhodného zadání. Poskytuje okamžitou zpětnou vazbu s detailním popisem chyb. |
| **Test** | **Ověření znalostí** | Test s výběrem ze tří odpovědí, sleduje progres a procentuální úspěšnost. |

---

## Testování a Optimalizace

Pro zajištění spolehlivosti a výkonu byly provedeny výkonnostní, integrační a uživatelské testy.

* **Výkonnostní testy:** Ověřena plynulost animací (`Test zátěže animací`) a rychlá odezva databáze (`Test odezvy Firestore`), kde průměrný čas spuštění aplikace (_app_start) dosáhl 882 ms.
* **Integrační testy:** Provedeno testování komunikace mezi **View Modely** a **Navesti Repository** pomocí mockovaných datových sad (**Mockito**), což zajistilo správnou funkčnost aplikační logiky.
* **Uživatelské testování:** Během otevřeného testování (zahájeno 27. února 2025) byly vydány čtyři aktualizace s opravami. Zjištěné nedostatky se týkaly grafického designu a jedné funkční chyby spojené s čitelností textu v rozevíracím menu.

---

Aplikace **"Návěstidlo"** splnila svůj cíl a slouží jako moderní a užitečný nástroj pro výuku železničních návěstidel.

Aplikace je dostupná ke stažení na Google Play.
