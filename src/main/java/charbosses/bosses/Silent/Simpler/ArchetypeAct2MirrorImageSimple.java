package charbosses.bosses.Silent.Simpler;

import charbosses.bosses.Silent.ArchetypeBaseSilent;
import charbosses.cards.colorless.EnJAX;
import charbosses.cards.green.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct2MirrorImageSimple extends ArchetypeBaseSilent {

    public ArchetypeAct2MirrorImageSimple() {
        super("SI_MIRROR_ARCHETYPE", "Mirror");

        maxHPModifier += 240;
        actNum = 2;
    }


    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;

        switch (turn) {
            case 0:
                //Turn 1
                addToList(cardsList, new EnHallunication());
                addToList(cardsList, new EnBackstab(), extraUpgrades);
                turn++;
                break;
            case 1:
                addToList(cardsList, new EnJAX(), true);
                addToList(cardsList, new EnDaggerSpray());
                turn++;
                break;
            case 2:
                //Turn 3
                addToList(cardsList, new EnBlur(), extraUpgrades);
                addToList(cardsList, new EnBlur());
                turn++;
                break;
            case 3:
                //Turn 4
                addToList(cardsList, new EnDash(), extraUpgrades);

                turn++;
                break;
            case 4:
                //Turn 4
                addToList(cardsList, new EnRiddleWithHoles());

                turn++;
                break;
            case 5:
                //Turn 4
                addToList(cardsList, new EnAfterImage());
                addToList(cardsList, new EnAfterImage());
                addToList(cardsList, new EnAfterImage());
                turn = 1;
                break;


        }

        return cardsList;
    }


}