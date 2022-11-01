package champ;

import champ.cards.AbstractChampCard;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.UltimateStance;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static champ.ChampMod.FINISHER;

public class ChampTextHelper {
    protected static String[] ifStr = CardCrawlGame.languagePack.getUIString(AbstractChampCard.makeID("highlightCombos")).TEXT;

    public static void calculateTagText(AbstractChampCard c) {
        String prefixTech = "";
        String prefixFin = "";
        /*
        if (c.hasTag(TECHNIQUE)) {
            prefixTech = ChampChar.characterStrings.TEXT[29];
            if (AbstractDungeon.player != null) {
                if (AbstractDungeon.player.stance instanceof DefensiveStance) {
                    prefixTech = ChampChar.characterStrings.TEXT[31];
                    prefixTech = prefixTech + DefensiveStance.amount();
                    prefixTech = prefixTech + ChampChar.characterStrings.TEXT[53];
                } else if (AbstractDungeon.player.stance instanceof BerserkerStance) {
                    prefixTech = ChampChar.characterStrings.TEXT[31];
                    prefixTech = prefixTech + BerserkerStance.amount();
                    prefixTech = prefixTech + ChampChar.characterStrings.TEXT[54];
                } else if (AbstractDungeon.player.stance instanceof UltimateStance) {
                    prefixTech = ChampChar.characterStrings.TEXT[33];
                }
            }
            if (c.upgraded && c.UPGRADE_DESCRIPTION != null) {
                if (c.techniqueLast) {
                    c.rawDescription = c.UPGRADE_DESCRIPTION + " NL " + prefixTech;
                    c.rawDescription.substring(0,c.rawDescription.length()-4); // trim the trailing NL
                } else {
                    c.rawDescription = prefixTech + c.UPGRADE_DESCRIPTION;
                }
            } else {
                if (c.techniqueLast) {
                    c.rawDescription = c.DESCRIPTION + " NL " + prefixTech;
                    c.rawDescription.substring(0,c.rawDescription.length()-4); // trim the trailing NL
                } else {
                    c.rawDescription = prefixTech + c.DESCRIPTION;
                }
            }
        }
        }
         */
        if (c.hasTag(FINISHER)) {
            prefixFin = ChampChar.characterStrings.TEXT[34];
            if (AbstractDungeon.player != null) {
                if (AbstractDungeon.player.stance instanceof DefensiveStance) {
                    prefixFin = ChampChar.characterStrings.TEXT[36] + DefensiveStance.finisherAmount() + ChampChar.characterStrings.TEXT[57];
                } else if (AbstractDungeon.player.stance instanceof BerserkerStance) {
                    prefixFin = ChampChar.characterStrings.TEXT[37];
                } else if (AbstractDungeon.player.stance instanceof UltimateStance) {
                    prefixFin = ChampChar.characterStrings.TEXT[38];
                }
            }
            if (c.upgraded && c.UPGRADE_DESCRIPTION != null) {
                c.rawDescription = prefixTech + c.UPGRADE_DESCRIPTION + prefixFin;
            } else {
                c.rawDescription = prefixTech + c.DESCRIPTION + prefixFin;
            }
        }
    }

    public static void colorCombos(AbstractChampCard card, boolean resetColors) {
        if (AbstractDungeon.player != null) {
            if (card.rawDescription.contains(ifStr[0])) {
                if (AbstractChampCard.bcombo() && !resetColors) {
                    card.rawDescription = card.rawDescription.replace(ifStr[0], ifStr[1]);
                }
            } else if (card.rawDescription.contains(ifStr[1])) {
                if (!AbstractChampCard.bcombo() || resetColors) {
                    card.rawDescription = card.rawDescription.replace(ifStr[1], ifStr[0]);
                }
            }

            if (card.rawDescription.contains(ifStr[2])) {
                if (AbstractChampCard.dcombo() && !resetColors) {
                    card.rawDescription = card.rawDescription.replace(ifStr[2], ifStr[3]);
                }
            } else if (card.rawDescription.contains(ifStr[3])) {
                if (!AbstractChampCard.dcombo() || resetColors) {
                    card.rawDescription = card.rawDescription.replace(ifStr[3], ifStr[2]);
                }
            }
        }
        card.initializeDescription();
    }
}
