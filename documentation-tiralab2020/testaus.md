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

Kuten tuloksista näkee, niin ohjelma on kyllä ihan nopea mutta valitettavan alkukantainen vielä.

#### Javan omilla listarakenteilla
Laudan koko | miinat | Onnistuneet ratkaisut | keskimääräinen ratkaisuaika |
------------|--------|-----------------------|-----------------------------|
10x10 | 10 | 96/100 | 5,79 ms |
16x16 | 40 | 57/100 | 14,09 ms |
16x30| 99 | 3/100 | 10,46‬ ms |

#### Itse tehdyllä ArrayDeque rakenteella, vielä jotain ArrayList ratkaisuja
Laudan koko | miinat | Onnistuneet ratkaisut | keskimääräinen ratkaisuaika |
------------|--------|-----------------------|-----------------------------|
10x10 | 10 | 94/100 | 28,07 ms |
16x16 | 40 | 52/100 | 50,09 ms |
16x30| 99 | 2/100 | 45,75‬ ms |
