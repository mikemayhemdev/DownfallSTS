package champ;

import champ.cards.AbstractChampCard;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.UltimateStance;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static automaton.FunctionHelper.WITH_DELIMITER;
import static champ.ChampMod.FINISHER;
import static champ.ChampMod.TECHNIQUE;

public class ChampTextHelper {
    private static String THIS_EFFECT_WILL_ACTIVATE_COLOR = "[#5ebf2a]";

    public static void calculateTagText(AbstractChampCard c) {
        String prefixTech = "";
        String prefixFin = "";
        if (c.hasTag(TECHNIQUE)) {
            prefixTech = ChampChar.characterStrings.TEXT[29];
            if (AbstractDungeon.player != null) {
                if (AbstractDungeon.player.stance instanceof DefensiveStance) {
                    prefixTech = ChampChar.characterStrings.TEXT[31];
                    prefixTech = prefixTech + DefensiveStance.amount();
                    prefixTech = prefixTech + ChampChar.characterStrings.TEXT[53];
                } else if (AbstractDungeon.player.stance instanceof BerserkerStance) {
                    prefixTech = ChampChar.characterStrings.TEXT[32];
                    prefixTech = prefixTech + BerserkerStance.amount();
                    prefixTech = prefixTech + ChampChar.characterStrings.TEXT[52];
                } else if (AbstractDungeon.player.stance instanceof UltimateStance) {
                    prefixTech = ChampChar.characterStrings.TEXT[33];
                }
            }
            if (c.upgraded && c.UPGRADE_DESCRIPTION != null) {
                c.rawDescription = prefixTech + c.UPGRADE_DESCRIPTION;
            } else {
                c.rawDescription = prefixTech + c.DESCRIPTION;
            }
        }
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

    public static void colorCombos(AbstractChampCard card) {
        if (card.rawDescription.contains("*Berserker champ:Combo:")) {
            if (AbstractChampCard.bcombo()) {
                String[] including_delim = card.rawDescription.split(String.format(WITH_DELIMITER, "*Berserker champ:Combo:"));
                including_delim[1] = including_delim[1].replace("\\*", THIS_EFFECT_WILL_ACTIVATE_COLOR);
                card.rawDescription = including_delim[0] + including_delim[1] + including_delim[2];
            }
        } else if (card.rawDescription.contains(THIS_EFFECT_WILL_ACTIVATE_COLOR + "Berserker champ:Combo:")) {
            if (!AbstractChampCard.bcombo()) {
                String[] including_delim = card.rawDescription.split(String.format(WITH_DELIMITER, (THIS_EFFECT_WILL_ACTIVATE_COLOR + "Berserker champ:Combo:")));
                including_delim[1] = including_delim[1].replace(THIS_EFFECT_WILL_ACTIVATE_COLOR, "\\*");
                card.rawDescription = including_delim[0] + including_delim[1] + including_delim[2];
            }
        }

        if (card.rawDescription.contains("*Defensive champ:Combo:")) {
            if (AbstractChampCard.dcombo()) {
                String[] including_delim = card.rawDescription.split(String.format(WITH_DELIMITER, "*Defensive champ:Combo:"));
                including_delim[1] = including_delim[1].replace("\\*", THIS_EFFECT_WILL_ACTIVATE_COLOR);
                card.rawDescription = including_delim[0] + including_delim[1] + including_delim[2];
            }
        } else if (card.rawDescription.contains(THIS_EFFECT_WILL_ACTIVATE_COLOR + "Berserker champ:Combo:")) {
            if (!AbstractChampCard.dcombo()) {
                String[] including_delim = card.rawDescription.split(String.format(WITH_DELIMITER, (THIS_EFFECT_WILL_ACTIVATE_COLOR + "Defensive champ:Combo:")));
                including_delim[1] = including_delim[1].replace(THIS_EFFECT_WILL_ACTIVATE_COLOR, "\\*");
                card.rawDescription = including_delim[0] + including_delim[1] + including_delim[2];
            }
        }
    }
}
