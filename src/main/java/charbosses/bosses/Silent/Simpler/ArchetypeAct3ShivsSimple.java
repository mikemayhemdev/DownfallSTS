package charbosses.bosses.Silent.Simpler;

import charbosses.bosses.Silent.ArchetypeBaseSilent;
import charbosses.cards.green.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct3ShivsSimple extends ArchetypeBaseSilent {

    public ArchetypeAct3ShivsSimple() {
        super("SI_SHIV_ARCHETYPE", "Shivs");

        maxHPModifier += 350;
        actNum = 3;
    }


    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;

            switch (turn) {
                case 0:
                    //Turn 1
                    if (!looped) addToList(cardsList, new EnBagOfKnives());
                    addToList(cardsList, new EnAccuracy());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnCloakAndDagger(), true);

                    turn++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnPiercingWail());
                    addToList(cardsList, new EnPiercingWail());
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnBladeDance(),true);
                    turn++;
                    break;
                case 4:
                    //Turn 5
                    addToList(cardsList, new EnNeutralize(), true);
                    addToList(cardsList, new EnFinisher(2));

                    turn++;
                    break;
                case 5:
                    //Turn 6
                    if (looped){
                        addToList(cardsList, new EnPhantasmalKiller());
                    } else {
                        addToList(cardsList, new EnWraithForm());
                    }

                    turn++;
                    break;

                case 6:
                    //Turn 7
                    addToList(cardsList, new EnBladeDance(),true);
                    turn = 0;
                    looped = true;
                    break;



            }

        return cardsList;
    }

}
