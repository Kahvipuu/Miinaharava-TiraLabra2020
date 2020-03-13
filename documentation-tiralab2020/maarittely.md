# Määrittelydokumentti

## Mitä algoritmeja ja tietorakenteita toteutat työssäsi
Toistaiseksi en ole käynyt kunnolla läpi miinaharavaohjelman antamaa tietoa läpi, mutta aloitan tekemällä kopion pelilaudasta (suuntaamattomana) verkkona, johon kerätty tieto tallennetaan.
Algoritmia kuvailisin syvyyshaku tyyliseksi, siten että aina viimeisin uusi tieto käsitellään ensimmäiseksi ja saadun tiedon avulla lisätään pinoon käsiteltäviä solmuja. Tarkoituksena on myös poistaa luodusta verkosta jo käsitellyt solmut ja päivittää solmun naapureille tietoa tarpeen mukaan. Solmut joihin ei ole lisätty tietoa on aluksi passiivisia. Tällä rakenteella ohjelma tietää että kun pino tyhjenee niin on pakko arvata seuraava siirto. Pinon toteutan niin että solmu tietää onko se jo pinossa, jottei pinon koko kasva kaksoiskappaleiden takia.
Toistaiseksi en osaa sanoa onko tämän tyylinen ratkaisumalli liian raskas, mutten helpompaakaan keksi.

## Mitä ongelmaa ratkaiset ja miksi valitsit kyseiset algoritmit/tietorakenteet
Ongelmana miinaharavan ratkaiseminen, kuitenkin niin että hyväksyn sen ettei läheskään kaikkia konfiguraatioita pysty ratkaisemaan. Lisäksi on monia tilanteita joissa joutuu arvaamaan.
Kyseiset metodit on valittu siksi, että suurena ongelmana tehokkuudelle näen kulloinkin käsiteltävänä olevien solmujen määrän, jota tulee vähentää merkittävästi. Lisäksi miinaharavassa solmun naapurit ja solmun oma arvo on ainoa tieto mitä on olemassa, ja nämä tiedot täytyy linkittää jotenkin.

## Mitä syötteitä ohjelma saa ja miten näitä käytetään
Tekoälyohjelma saa miinaharavaohjelmalta tilapäivitykset, joita käytetään seuraavan siirron päättämiseen. Päätös tehdään käymällä solmuja läpi ja tiettyjen ehtojen täyttyessä annetaan peli-ohjelmalle uusi siirto.
Läpikäytävien solmujen määrää on tarkoitus vähentää käymällä merkitsevämmät solmut ensin läpi ja merkitsemällä jo käytyjä solmuja 

## Tavoitteena olevat aika- ja tilavaativuudet (m.m. O-analyysit)

### Aikavaativuus
Aikavaativuudessa olisi tarkoitus pysyä maksimissaan neliöllisenä O(n^2), täysin en vielä hahmota miten raskaan algoritmista joutuu tekemään. Kuitenkin jokaisen solmun kohdalla joudutaan tarkistamaan kaikki naapurit ja solmut voidaan joutua tarkastamaan muutaman kerran. Kuitenkaan yhden solmun takia ei pitäisi joutua tarkistamaan koko laudallista solmuja missään tapauksessa.

### Tilavaativuus
Tilaa tarvitsen pelilaudalliselle olioita, ja olioihin kirjataan tarpeellinen tieto. Tarpeellisen tiedon määrä ei ole vielä täysin arvioitavissa, mutta tilavaativuuden pitäisi pysyä lineaarisena O(n). (Tai miten merkintä nyt halutaan; pelilaudan kokoon n verrattuna pienehkö luonnollinen luku x, x*n)

## Lähteet
Antti Laaksonen, Tietorakenteet ja algoritmit, 2019 (Tirakirja, https://github.com/pllk/tirakirja)

Richard Kaye, Some Minesweeper Configurations, 2007 (http://web.mat.bham.ac.uk/R.W.Kaye/minesw/minesw.pdf)

Richard Kaye, Minesweeper and NP-completeness (Richard Kaye's Minesweeper Pages, http://web.mat.bham.ac.uk/R.W.Kaye/minesw/minesw.htm)

Code-Bullet, minesweeper AI, 2018 (https://github.com/Code-Bullet/minesweeper-AI)
