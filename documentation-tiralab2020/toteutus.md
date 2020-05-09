# Toteutusdokumentti

## Ohjelman yleisrakenne
Ohjelma tekee valeruudukon, johon se pyytää ruutujen numerotiedot pääohjemalta. Päätokset siirroista tehdään kuitenkin valeruudukon avulla.
Luotu valeruudukko muodostuu omista yksittäisistä ruuduista johon kirjataan kaikki pelin aikana saatu tieto, esim. viereisten avattujen ruutujen tai laitettujen lippujen määrä.
Ohjelma antaa siirron pääohjelmalle ja käy läpi saadun vastauksen, eli lähinnä avatun ruudun miinamäärän ja miten se vaikuttaa ympäröiviin ruutuihin.
Ohjelma myös arvioi seuraavia siirtoja ainoastaan jo tehtyjen siirtojen vierestä, ellei siltä lopu varma tieto kesken jolloin siirrytään arvaamaan.

## Saavutetut aika- ja tilavaativuudet (m.m. O-analyysit pseudokoodista)
Tilavaativuus on laudan koosta riippuva ja kasvaa lineaarisesti laudan koon mukana. Laudan yksittäisillä ruuduilla on useita ylläpidettäviä tietoja, kymmenen kirjoitushetkellä.

Aikavaativuudesta on testausdokumentissa tarkempia arvoja. Kokonaisuudessaan vaikuttaisi siltä että botin versio 2 on jokseenkin lineaarinen aikavaativuudeltaan, ennen kuin se joutuu tekemään useampia arvauksia, 
jotka ovat kyseisessä versiossa erittäin hitaita. Ainakin käytetty aika lisääntyi vain noin 66%, kun laudan koko kasvoi melkein tuplaksi +88%, sekä miinojen määrä nousi verrattain 24%. 
On toisaalta hyvin mahdollista että testattavien lautojen koko on edelleen liian pieni ja ohjelmassa aikaa kuluu verrattaen johonkin muuhun enemmän. Tätä on kuitenkin tämän hetkisellä ohjelmalla vaikea testata, 
koska suorituskyky alkaa kärsimään laudan kokoa vielä kasvattamalla. Normaali vaikein taso kun kasvatetaan nelinkertaiseksi, niin onnistumisprosentti putoaa alle puoleen.

Ajatuskokeena edellisestä, jos vertaa lukuja: keskivaikeasta poistettu helpon aika 1,02 ja vaikeasta poistettu helpon aika 1,94, 
niin ohjelman käyttämä aika kasvaa noin 90% keskimmäisestä vaikeaan siirryttäessä. Näin ajateltuna olisi ehkä muu kuin laudan koko eliminoitu käytetystä ajasta.
Ohjelmaa parantaessa tämä ajankäytön lineaarisuus tulisi tietenkin nykytiedon valossa katoamaan, eikä tätä ole erityistä syytä epäillä.

Viimeisin botin versio ei aika ja tilavaativuuksiltaan juurikaan eroa aikaisemmista, jonkin verran siinä olisi optimoitavaa, mutta kokonaisuus on pysynyt lähes samana.

## Suorituskyky- ja O-analyysivertailu (mikäli työ vertailupainotteinen)
Tieto mitä löysin viittasi siihen että hyvin toteutettu miinaharava tekoäly pääsisi vaikealla laudalla noin kolmasosan läpäisyprosenttiin ja vielä arvailuja parantamalla noin 37% läpäisyyn.
Kyseistä tietoa on kuitekin hyvin vaikea validoida. Oman botin 4 versio pääsee noin 33% läpäisyyn kyseisellä laudalla. 
Parhaisiin löytämiini ratkaisijoihin käytetään verrattaen raskasta analyysiä.
Tähän verrattuna oma ohjelmani on kelvollinen, sen kevyen rakenteen ansiosta.

## Työn mahdolliset puutteet ja parannusehdotukset
Toistaiseksi botti ei laske pelin loppupuolella miinojen määrää ja käytä sitä apukeinona.
Arvauksissa on edelleen kehitettävää, valintoja pitäisi lopuksi tehdä muita avattuja lähellä oleviin ruutuihin jotta niistä saisi lisäapua jo saatuun tietoon. 
Lisäksi todennäköisyydet pitäisi laskea ainakin karkealla tasolla, nyt arvaus voi osua 50/50 ruutuun vaikka 20/80 tilanteita olisi tarjolla.

## Lähteet
Näin vapaamuotoisesti lähteet, kaksi alla olevaa ovat tekoälyn mahdollisen maksimisuorituksen arviointiin.

minesweepr, Drew Griscom Roos: http://mrgris.com/projects/minesweepr/

math.stackexchange:	https://math.stackexchange.com/questions/42494/odds-of-winning-at-minesweeper-with-perfect-play

Muut määrittelyn aikana käytetyt lähteet löytyvät määrittelydokumentista