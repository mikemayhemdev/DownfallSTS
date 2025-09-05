package charbosses.bosses.Ironclad.Simpler;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.red.*;
import charbosses.monsters.Fortification;
import charbosses.powers.bossmechanicpowers.IroncladFortificationPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BarricadePower;

import java.util.ArrayList;
import java.util.Collections;

public class ArchetypeAct3BlockSimple extends ArchetypeBaseIronclad {
    public static final int FORTIFICATION_AMOUNT = 10;
    private boolean lastTurnWasBodySlam;
    private int trueTurn=0;
    private ArrayList<Integer> turnOrder;

    public ArchetypeAct3BlockSimple() {
        super("IC_BLOCK_ARCHETYPE", "Block");

        maxHPModifier += 300;
        maxHPModifierAsc = 30;
        actNum = 3;
        bossMechanicName = IroncladFortificationPower.NAME;
        bossMechanicDesc = IroncladFortificationPower.DESC[0] + FORTIFICATION_AMOUNT + IroncladFortificationPower.DESC[1];
        turnOrder = new ArrayList<>();
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BarricadePower(p)));
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new Fortification(), true));
        resetMidTurnOrder();
    }

    public void resetMidTurnOrder(){
        ArrayList<Integer> newTurnOrder = new ArrayList<>();
        newTurnOrder.add(1);
        newTurnOrder.add(2);
        newTurnOrder.add(3);
        Collections.shuffle(newTurnOrder, AbstractDungeon.monsterRng.random);
        turnOrder.addAll(newTurnOrder);
        advanceTurn();
    }

    public void advanceTurn(){
        turn = turnOrder.get(trueTurn);
        trueTurn++;
        if (trueTurn == 4){
            trueTurn = 0;
            looped = true;
        }
    }



    public void initialize() {

        addRelic(new CBR_Calipers());
        if (AbstractDungeon.ascensionLevel >= 19){
            addRelic(new CBR_Anchor());
        }
    }



    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;


        if (AbstractCharBoss.boss.currentBlock >= 10 && !lastTurnWasBodySlam){
            lastTurnWasBodySlam = true;
            addToList(cardsList, new EnIronWave());
            addToList(cardsList, new EnBodySlam());

            return cardsList;
        }

        switch (turn) {
            case 0:
                if (extraUpgrades && !looped) //Metallicize Potion
                addToList(cardsList, new EnImpervious(), false);
                addToList(cardsList, new EnIntimidate());
                advanceTurn();
                lastTurnWasBodySlam = false;
                break;
            case 1:
                if (extraUpgrades && !looped) //Metallicize Potion
                addToList(cardsList, new EnMetallicize(), false);
                addToList(cardsList, new EnRampage());
                advanceTurn();
                lastTurnWasBodySlam = false;
                break;
            case 2:
                if (extraUpgrades && !looped) //Metallicize Potion
                addToList(cardsList, new EnGhostlyArmor());
                addToList(cardsList, new EnFlameBarrier());
                advanceTurn();
                lastTurnWasBodySlam = false;
                break;
        }


        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_SelfFormingClay());
    }
}