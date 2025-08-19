package charbosses.bosses.Ironclad.Simpler;

import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.red.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct1StatusesSimple extends ArchetypeBaseIronclad {


    public ArchetypeAct1StatusesSimple() {
        super("IC_STATUS_ARCHETYPE", "Status");

        maxHPModifier += 70;
        maxHPModifierAsc = 10;
        actNum = 1;
    }


    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;

        switch (turn) {
            case 0:
                addToList(cardsList, new EnStatusMirror());
                addToList(cardsList, new EnRecklessCharge());
                addToList(cardsList, new EnRecklessCharge());
                turn++;
                break;
            case 1:
                addToList(cardsList, new EnPowerThrough(true), extraUpgrades);
                turn++;
                break;
            case 2:
                addToList(cardsList, new EnBash(), extraUpgrades);
                turn++;
                break;
            case 3:
                addToList(cardsList, new EnImmolate(), extraUpgrades);
                turn++;
                break;
            case 4:
                addToList(cardsList, new EnMetallicize(), true);
                addToList(cardsList, new EnInflame());
                turn=1;
                break;
        }
        return cardsList;
    }
}