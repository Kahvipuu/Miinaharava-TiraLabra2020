# Toteutusdokumentti

## Ohjelman yleisrakenne
Ohjelma tekee valeruudukon, johon se pyytää ruutujen numerotiedot pääohjemalta. Päätokset siirroista tehdään kuitenkin valeruudukon avulla.
Luotu valeruudukko muodostuu omista yksittäisistä ruuduista johon kirjataan kaikki pelin aikana saatu tieto, esim. viereisten avattujen ruutujen tai laitettujen lippujen määrä.
Ohjelma antaa siirron pääohjelmalle ja käy läpi saadun vastauksen, eli lähinnä avatun ruudun miinamäärän ja miten se vaikuttaa ympäröiviin ruutuihin.
Ohjelma myös arvioi seuraavia siirtoja ainoastaan jo tehtyjen siirtojen vierestä, ellei siltä lopu varma tieto kesken jolloin siirrytään arvaamaan.

## Saavutetut aika- ja tilavaativuudet (m.m. O-analyysit pseudokoodista)
Tilavaativuus on laudan koosta riippuva ja kasvaa lineaarisesti laudan koon mukana. Laudan yksittäisillä ruuduilla on useita ylläpidettäviä tietoja, kymmenen kirjoitushetkellä.

Aikavaativuudesta on testausdokumentissa tarkempia arvoja. 
Näyttäisi siltä että aikavaativuus kasvaa syötteen kokoa nopeammin, viimeisimmässä versiossa kun laudan koon nelinkertaistaa niin ajankäyttö noin 12 kertaistuu.
Varsinaisesti ohjelmassa ei ole muita laudan koosta exponentiaalisesti riippuvaisia toistettavia silmukoita kuin arvaaminen, joka ei ole erityisen hyvin toteutettu.
Luonnollisesti koska miinaharava on todistettu np-täydelliseksi, niin tulos oli ennalta arvattava.
Viimeisin botin versio ei aika ja tilavaativuuksiltaan juurikaan eroa aikaisemmista, jonkin verran siinä olisi optimoitavaa, mutta kokonaisuus on pysynyt lähes samana.

## Suorituskyky- ja O-analyysivertailu (mikäli työ vertailupainotteinen)
Tieto mitä löysin viittasi siihen että hyvin toteutettu miinaharava tekoäly pääsisi vaikealla laudalla noin kolmasosan läpäisyprosenttiin ja vielä arvailuja parantamalla noin 37% läpäisyyn.
Kyseistä tietoa on kuitekin hyvin vaikea validoida. Oman botin 4 versio pääsee noin 33% läpäisyyn kyseisellä laudalla. 
Parhaisiin löytämiini ratkaisijoihin käytetään verrattaen raskasta analyysiä ja tähän verrattuna oma ohjelmani on hyvin kevyt.

## Työn mahdolliset puutteet ja parannusehdotukset
Toistaiseksi botti ei laske pelin loppupuolella miinojen määrää ja käytä sitä apukeinona.
Arvauksissa on edelleen kehitettävää, valintoja pitäisi lopuksi tehdä muita avattuja lähellä oleviin ruutuihin jotta niistä saisi lisäapua jo saatuun tietoon. 
Lisäksi todennäköisyydet pitäisi laskea ainakin karkealla tasolla, nyt arvaus voi osua 50/50 ruutuun vaikka 20/80 tilanteita olisi tarjolla.

## Lähteet
Näin vapaamuotoisesti lähteet, kaksi alla olevaa ovat tekoälyn mahdollisen maksimisuorituksen arviointiin.

minesweepr, Drew Griscom Roos: http://mrgris.com/projects/minesweepr/

math.stackexchange:	https://math.stackexchange.com/questions/42494/odds-of-winning-at-minesweeper-with-perfect-play

Muut määrittelyn aikana käytetyt lähteet löytyvät määrittelydokumentista