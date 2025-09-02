package charbosses.bosses.Silent.Simpler;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Silent.ArchetypeBaseSilent;
import charbosses.cards.curses.EnCurseOfTheBell;
import charbosses.cards.green.*;
import charbosses.powers.bossmechanicpowers.SilentDelayedWraithPower;
import charbosses.powers.bossmechanicpowers.SilentShivTimeEaterPower;
import charbosses.relics.*;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Blur;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

public class ArchetypeAct3ShivsSimple extends ArchetypeBaseSilent {

    private ArrayList<Integer> turnOrder;
    public ArchetypeAct3ShivsSimple() {
        super("SI_SHIV_ARCHETYPE", "Shivs");

        maxHPModifier += 350;
        maxHPModifierAsc = 30;
        actNum = 3;
        turnOrder = new ArrayList<>();
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new SilentShivTimeEaterPower(AbstractCharBoss.boss)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new SilentDelayedWraithPower(AbstractCharBoss.boss)));

        resetMidTurnOrder();
    }



    public void initialize() {

        addRelic(new CBR_CallingBell());
        addRelic(new CBR_OrnamentalFan());
        addRelic(new CBR_OddlySmoothStone());
        addRelic(new CBR_BagOfMarbles());
        if (AbstractDungeon.ascensionLevel >= 19){
            addRelic(new CBR_Shuriken());
        }
    }


    public void resetMidTurnOrder(){
        ArrayList<Integer> newTurnOrder = new ArrayList<>();
        newTurnOrder.add(1);
        newTurnOrder.add(2);
        newTurnOrder.add(3);
        if (!turnOrder.isEmpty()) {
            newTurnOrder.remove(turnOrder.get(0));
        }
        Collections.shuffle(newTurnOrder, AbstractDungeon.monsterRng.random);
        turnOrder.addAll(newTurnOrder);
    }

    public void advanceTurn(){
        switch (turn) {
            case 0:
            case 1:
            case 2:
            case 3:
                if (turnOrder.size() == 1){
                    turn = 4;
                    resetMidTurnOrder();
                } else {
                    turn = turnOrder.get(0);
                    turnOrder.remove(0);
                }
                break;
            case 4:
                //Turn 5
                turn=0;
                looped = true;
                break;

        }
    }


    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
            switch (turn) {
                case 0:
                    if (extraUpgrades) //Duplication Potion
                    addToList(cardsList, new Blur(), true);
                    addToList(cardsList, new EnCloakAndDagger(), false);
                    advanceTurn();
                    break;
                case 1:
                    addToList(cardsList, new EnPiercingWail());
                    addToList(cardsList, new EnPiercingWail());

                    advanceTurn();
                    break;
                case 2:
                    addToList(cardsList, new EnBladeDance(),false);
                    addToList(cardsList, new EnCurseOfTheBell());

                    advanceTurn();
                    break;
                case 3:
                    addToList(cardsList, new EnAccuracy(), extraUpgrades);
                    addToList(cardsList, new EnNeutralize(), false);

                    advanceTurn();
                    break;
                case 4:
                    //Turn 5
                    addToList(cardsList, new EnMalaise(), false);
                    addToList(cardsList, new EnDeflect(), false);

                    advanceTurn();
                    break;

            }

        return cardsList;
    }

}
