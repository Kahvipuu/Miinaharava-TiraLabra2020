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

#### Botti2, turhasta karsittu versio
Laudan koko | miinat | Onnistuneet ratkaisut | keskimääräinen ratkaisuaika | Aika yhteensä |
------------|--------|-----------------------|-----------------------------|---------------|
10x10 | 10 | 99/100 | 1,26 ms | |
16x16 | 40 | 82/100 | 2,28 ms | |
16x30 | 99 | 28/100 | 3,2‬0 ms | |
32x60 | 396 | 14/100 | 18,6‬0 ms | |
16x16 | 40 | 8057/10000 | 0,92 ms | Total 9216 ms |
16x30 | 99 | 2525/10000 | 1,53 ms | Total 15341 ms |
32x60 | 396 | 1112/10000 | 18,56 ms | Total 185674 ms x12 aika kun x4 koko |
32x60 | 396 | 1019/10000 | 17,68 ms | Total 176837 ms |
32x60 | 396 | 1122/10000 | 17,95 ms | Total  179538 ms |
32x60 | 396 | 1091/10000 | 18,46 ms | Total 184620 ms ArrayCopy |


#### Botti2, Viimeisin versio botista, ylimääräistä kuormaa vielä mukana
Laudan koko | miinat | Onnistuneet ratkaisut | keskimääräinen ratkaisuaika |
------------|--------|-----------------------|-----------------------------|
10x10 | 10 | 95/100 | 3,02 ms |
16x16 | 40 | 80/100 | 7,99 ms |
16x30 | 99 | 28/100 | 12,14‬ ms |

#### Botti2, Suoritukyvyn parantamista viimeiseen versioon, lähinnä ylimääräisen printtailun poistamisena
Laudan koko | miinat | Onnistuneet ratkaisut | keskimääräinen ratkaisuaika |
------------|--------|-----------------------|-----------------------------|
32x32 | 160 | 64/100 | 31,16 ms |
32x32 | 160 | 69/100 | 10,03 ms |
32x60 | 396 | 14/100 | 20,15‬ ms |
32x60 | 396 | 13/100 | ‬17,83 ms |

#### Botti1, Itse tehdyllä listarakenteilla, ei ylimääräistä laudan läpikäyntiä
Laudan koko | miinat | Onnistuneet ratkaisut | keskimääräinen ratkaisuaika |
------------|--------|-----------------------|-----------------------------|
10x10 | 10 | 93/100 | 14,03 ms |
16x16 | 40 | 53/100 | 32,74 ms |
16x30| 99 | 1/100 | 26,43‬ ms |

#### Botti1, Javan omilla listarakenteilla, koko laudan läpikäynti ennen siirtoa
Laudan koko | miinat | Onnistuneet ratkaisut | keskimääräinen ratkaisuaika |
------------|--------|-----------------------|-----------------------------|
10x10 | 10 | 96/100 | 5,79 ms |
16x16 | 40 | 57/100 | 14,09 ms |
16x30| 99 | 3/100 | 10,46‬ ms |

#### Botti1, Itse tehdyllä ArrayDeque rakenteella, vielä jotain ArrayList ratkaisuja, koko laudan läpikäynti ennen siirtoa
Laudan koko | miinat | Onnistuneet ratkaisut | keskimääräinen ratkaisuaika |
------------|--------|-----------------------|-----------------------------|
10x10 | 10 | 94/100 | 28,07 ms |
16x16 | 40 | 52/100 | 50,09 ms |
16x30| 99 | 2/100 | 45,75‬ ms |

#### Botti1, Vertailukelpoiset ajat omilla listarakenteilla, koko laudan läpikäynti ennen siirtoa
Laudan koko | miinat | Onnistuneet ratkaisut | keskimääräinen ratkaisuaika |
------------|--------|-----------------------|-----------------------------|
10x10 | 10 | 88/100 | 68,33 ms |
16x16 | 40 | 51/100 | 397,53 ms |
16x30| 99 | 1/100 | 742,42‬ ms |


