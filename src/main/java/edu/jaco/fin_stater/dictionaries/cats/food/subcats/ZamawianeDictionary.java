package edu.jaco.fin_stater.dictionaries.cats.food.subcats;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum ZamawianeDictionary {
    PYSZNE ("pyszne.pl"),
    MAXI_PIZZA ("maxipizza"),
    RESTAUMATIC ("www.restaumatic.com"),
    RESTAUMATIC_2 ("restaumatic"),
    QUATTRO ("pizzeria quattro"),
    FAMILY_BISTRO ("familybistro"),
    MCDONALDS ("mcdonalds"),
    KFC ("kfc"),
    ANCYMON ("ancymon kebab"),
    GOOD_BURGER ("tm group tomasz chrobak"),
    GOOD_BURGER_2 ("good burger"),
    KAVOVA ("kavova"),
    TRATTORIA_DUE_TAVOLI ("trattoria due tavoli"),
    BOSFOR_BISTRO ("bosfor bistro zielonki"),
    BURGER_KING_WEGRZCE ("bk krakow wegrzce"),
    BURGER_KING_OSWIECIM ("bk oswiecim"),
    BURGER_KING_KRAKOW ("bk krakow sliwkowa"),
    JADLODAJNIA_MALA_CHATKA ("mala chatk"),
    STREFA_BISTRO ("strefa bistro"),
    BISTRO_OPOLSKA ("bistro opolska"),
    EPAM_FITBOX_1 ("epam, fb-133"),
    EPAM_FITBOX_2 ("epam - ff0174"),
    EPAM_FITBOX_3 ("fitboxy.com"),
    FOODTRUCK ("wanesa katniak pb burge"),
    MARCHE_I_SEVI_KEBAB ("kuchnia marche/sevi keb"),
    MARCHE_I_SEVI_KEBAB_2 ("kuchnia marche sevi kebab"),
    SEVI_KEBAB ("sevi kebab"),
    AKASAKA ("akasaka"),
    PRIMA_GUSTO ("prima gusto"),
    PIZZA_HUT ("pizza hut"),
    GIRO_PIZZA ("giro pizza"),
    MALINOWY_ANIOL ("malinowy aniol"),
    HINDUS ("hindus food"),
    ALEJA_SMAKOW ("aleja smakow"),
    LUNCH_BAR_WOLANSKI ("lunch bar wolanski");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(ZamawianeDictionary.values())
                .anyMatch(dictIt ->
                    transaction.getAdditional_info()
                            .toLowerCase()
                            .contains(dictIt.sentence) ||
                    transaction.getAdditional_info_2()
                            .toLowerCase()
                            .contains(dictIt.sentence) ||
                    transaction.getDescription()
                            .toLowerCase()
                            .contains((dictIt.sentence))
                );
    }
}
