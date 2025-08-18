package charbosses.bosses.Watcher.Simpler;

import charbosses.bosses.Watcher.ArchetypeBaseWatcher;
import charbosses.cards.purple.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct2StanceDanceSimple extends ArchetypeBaseWatcher {


    public ArchetypeAct2StanceDanceSimple() {
        super("WA_ARCHETYPE_RETAIN", "Retain");

        maxHPModifier += 198;
        maxHPModifierAsc = 20;
        actNum = 2;
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
            switch (turn) {
                case 0:
                    //Turn 1
                    if (!looped) addToList(cardsList, new EnEyeForAnEye());
                    if (!looped) addToList(cardsList, new EnSteady());
                    addToList(cardsList, new EnLikeWater());
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    if (!looped) {
                        addToList(cardsList, new EnReachHeaven());
                    } else {
                        addToList(cardsList, new EnThroughViolence());
                    }
                    addToList(cardsList, new EnHalt());
                    //addToList(cardsList, new EnHalt());
                    turn++;
                    break;
                case 2:
                    //Turn 3
                   // addToList(cardsList, new EnPanacea());
                    addToList(cardsList, new EnMentalFortress());
                    addToList(cardsList, new EnFasting());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnWallop());
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnSashWhip());
                    addToList(cardsList, new EnCrushJoints());
                    turn++;
                    break;
                case 5:
                    if (looped){
                        addToList(cardsList, new EnExpunger());
                    } else {
                        addToList(cardsList, new EnConjurBlade());
                    }
                    turn = 0;
                    looped = true;
                    break;
            }


        return cardsList;
    }
}