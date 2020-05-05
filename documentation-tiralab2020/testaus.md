# Testausdokumentti

## Mitä on testattu, miten tämä tehtiin
Yksikkötestejä ja manuaalista testausta varsinaisen toiminnallisuuden osalta. 
Suorituskykytestausta automatisoidusti.

## Minkälaisilla syötteillä testaus tehtiin
Ohjelman ajoa bottipelinä sekä ilman gui:ta automaattisesti useampia pelejä.

## Miten testit voidaan toistaa
Ohjelmassa mahdollisuus bottipeliin, jota voi käyttää gui:ssa.
TestApp luokkaan voi muuttaa haluamansa määrän testattavia pelejä sekä laudan koon ja miinamäärän. Tällä testasin tekoälyn tehokkuutta.

## Ohjelman toiminnan empiirisen testauksen tulosten esittäminen graafisessa muodossa
Ensimmäisissä tuloksissa on mukana debuggaukseen käytetty kaikkien siirtojen etsintä jokaisen siirtopäätöksen aluksi. 
Viimeiset tulokset ovat ilman ylimääräistä kuormaa ohjelmalle ja tästä erosta näkee jotakuinkin saavutetun edun siihen, 
ettei käy kuin tarpeellisen osan pelilaudasta läpi ennen siirtopäätöstä.
Toistaiseksi tuloksissa näkyy ettei botti osaa ratkaista vaikeita pelejä, joten nopea häviö parantaa kokonaistulosta.

#### Javan omilla listarakenteilla, koko laudan läpikäynti ennen siirtoa
Laudan koko | miinat | Onnistuneet ratkaisut | keskimääräinen ratkaisuaika |
------------|--------|-----------------------|-----------------------------|
10x10 | 10 | 96/100 | 5,79 ms |
16x16 | 40 | 57/100 | 14,09 ms |
16x30| 99 | 3/100 | 10,46‬ ms |

#### Itse tehdyllä ArrayDeque rakenteella, vielä jotain ArrayList ratkaisuja, koko laudan läpikäynti ennen siirtoa
Laudan koko | miinat | Onnistuneet ratkaisut | keskimääräinen ratkaisuaika |
------------|--------|-----------------------|-----------------------------|
10x10 | 10 | 94/100 | 28,07 ms |
16x16 | 40 | 52/100 | 50,09 ms |
16x30| 99 | 2/100 | 45,75‬ ms |

#### Vertailukelpoiset ajat omilla listarakenteilla, koko laudan läpikäynti ennen siirtoa
Laudan koko | miinat | Onnistuneet ratkaisut | keskimääräinen ratkaisuaika |
------------|--------|-----------------------|-----------------------------|
10x10 | 10 | 88/100 | 68,33 ms |
16x16 | 40 | 51/100 | 397,53 ms |
16x30| 99 | 1/100 | 742,42‬ ms |

#### Itse tehdyllä listarakenteilla, ei ylimääräistä laudan läpikäyntiä
Laudan koko | miinat | Onnistuneet ratkaisut | keskimääräinen ratkaisuaika |
------------|--------|-----------------------|-----------------------------|
10x10 | 10 | 93/100 | 14,03 ms |
16x16 | 40 | 53/100 | 32,74 ms |
16x30| 99 | 1/100 | 26,43‬ ms |

