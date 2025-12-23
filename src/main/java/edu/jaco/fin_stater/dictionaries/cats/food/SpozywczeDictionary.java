package edu.jaco.fin_stater.dictionaries.cats.food;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum SpozywczeDictionary {
    BIEDRONKA ("biedronka"),
    LEWIATAN ("koral sp.j."),
    LEWIATAN_WIARUSA ("lokietek"),
    LEWIATAN_WIARUSA_2 ("wiarusa 15"),
    ZABKA ("zabka"),
    AUCHAN ("auchan"),
    LIDL ("lidl"),
    AWITEKS ("awiteks"),
    KRAKOWSKIE_WYPIEKI_GALERIA ("krk wypieki stawowa"),
    WARZYWNIAK_LOKIETKA ("firma handlowa manhatta"),
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
    TRATTORIA_WYKI ("trattoria wyki"),
    TRATTORIA_DUE_TAVOLI ("trattoria due tavoli"),
    BOSFOR_BISTRO ("bosfor bistro zielonki"),
    BURGER_KING_WEGRZCE ("bk krakow wegrzce"),
    BURGER_KING_OSWIECIM ("bk oswiecim"),
    BURGER_KING_KRAKOW ("bk krakow sliwkowa"),
    JADLODAJNIA_MALA_CHATKA ("mala chatk"),
    WILCZY_APETYT_GIEBULTOW ("zajazd wilczy apetyt"),
    STREFA_BISTRO ("strefa bistro"),
    WILCZY_GLOD ("oberza wilczy glod"),
    LODOVE_TUTKI ("lodziarnie firmowe"),
    GOOD_LOOD ("good lood"),
    GOOD_LOOD_2 ("goodlood.com"),
    CZULY_LODZIARZ ("czuly lodziarz"),
    LODY_U_MISKOW ("lody u miskow"),
    LODOWA_CHATKA ("lodowa chatka zielonki"),
    SPAR_KOLO_EPAMU_NA_OPOLSKIEJ ("spar"),
    BISTRO_OPOLSKA ("bistro opolska"),
    EPAM_FITBOX_1 ("epam, fb-133"),
    EPAM_FITBOX_2 ("epam - ff0174"),
    EPAM_FITBOX_3 ("fitboxy.com"),
    FOODTRUCK ("wanesa katniak pb burge"),
    MARCHE_I_SEVI_KEBAB ("kuchnia marche/sevi keb"),
    MARCHE_I_SEVI_KEBAB_2 ("kuchnia marche sevi kebab"),
    SEVI_KEBAB ("sevi kebab"),
    NAKIELNY ("nakielny"),
    AKASAKA ("akasaka"),
    TCHIBO ("tchibo"),
    CH_ZAKOPIANKA ("olimp/ch zakopianka"),
    CUKIERNIA_ROZA ("cukiernia roza"),
    COSTA_COFFEE ("costa coffee"),
    MASSOLIT ("massolit"),
    PRIMA_GUSTO ("prima gusto"),
    PIZZA_HUT ("pizza hut"),
    GIRO_PIZZA ("giro pizza"),
    MALINOWY_ANIOL ("malinowy aniol"),
    HINDUS ("hindus food"),
    RETO_CULINARIA ("reto culinaria"),
    M_EATING_POINT_TISCHNERA ("meating point tischnera"),
    ALEJA_SMAKOW ("aleja smakow"),
    PIEKARNIA_CUKIERNIA_STESKAL ("fph steskal"),
    BUCZEK ("buczek"),
    PIEKARNIA_LAJKONIK ("lajkonik"),
    CARREFOUR ("carrefour"),
    FOODSI ("www.foodsi.pl"),
    ZMACZENI ("zmaczeni"),
    LUNCH_BAR_WOLANSKI ("lunch bar wolanski"),
    GLOVO ("glovoapp.com");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(SpozywczeDictionary.values())
                .anyMatch(dictIt -> {
                    boolean foundMatch = transaction.getAdditional_info()
                                            .toLowerCase()
                                            .contains(dictIt.sentence) ||
                                         transaction.getAdditional_info_2()
                                            .toLowerCase()
                                            .contains(dictIt.sentence) ||
                                         transaction.getDescription()
                                            .toLowerCase()
                                            .contains((dictIt.sentence));

                    transaction.setCategoryMatchKeyword(dictIt.sentence);

                    return foundMatch;
                });
    }
}
