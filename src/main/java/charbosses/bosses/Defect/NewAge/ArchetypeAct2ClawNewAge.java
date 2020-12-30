package charbosses.bosses.Defect.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.blue.*;
import charbosses.cards.colorless.EnPanicButton;
import charbosses.cards.colorless.EnSwiftStrike;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnShame;
import charbosses.monsters.BronzeOrbWhoReallyLikesDefectForSomeReason;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.powers.ConstructPower;

import java.util.ArrayList;

public class ArchetypeAct2ClawNewAge extends ArchetypeBaseDefect {

    private EnClaw c;
    private CharBossDefect cB;

    public ArchetypeAct2ClawNewAge() {
        super("DF_ARCHETYPE_CLAW", "Claw");
        bossMechanicName = bossMechanicString.DIALOG[2];
        bossMechanicDesc = bossMechanicString.DIALOG[3];

        maxHPModifier += 140;
        actNum = 2;
    }

    @Override
    public void addedPreBattle() {
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ConstructPower(p, p, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new BronzeOrbWhoReallyLikesDefectForSomeReason(-450, 250, 0), true));
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new BronzeOrbWhoReallyLikesDefectForSomeReason(-600, 0, 1), true));

    }

    public void initialize() {

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_Kunai());
        addRelic(new CBR_RedMask());
        addRelic(new CBR_Turnip());
        //addRelic(new CBR_BlueCandle());

    }


    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        if (cB == null){
            cB = (CharBossDefect) AbstractCharBoss.boss;
        }
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;

        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnBootSequence(), false);  // removed
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), extraUpgrades);
                    addToList(cardsList, new EnMachineLearning(), extraUpgrades);  // removed
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnLeap(), extraUpgrades);
                    addToList(cardsList, new EnColdSnap(), true);
                    addToList(cardsList, new EnStrikeBlue(), false);
                    addToList(cardsList, new EnClumsy(), false);  //removed
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnReprogram(), false);
                    AbstractBossCard c5 = new EnSwiftStrike();
                    c5.manualCustomDamageModifier = 2;
                    addToList(cardsList, c5, false);
                    AbstractBossCard c6 = new EnRebound();
                    c6.manualCustomDamageModifier = 2;
                    addToList(cardsList, c6, false);
                    c = new EnClaw(cB.clawsPlayed * 2);
                    c.manualCustomDamageModifier = 2;
                    addToList(cardsList, c, extraUpgrades);
                    //Kunai Proc
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), extraUpgrades);
                    addToList(cardsList, new EnChargeBattery(), false);
                    addToList(cardsList, new EnGeneticAlgorithm(14), true);  //removed
                    addToList(cardsList, new EnShame(), false);
                    turn++;
                    break;
                case 4:
                    //Turn 5
                    addToList(cardsList, new EnEchoForm(), true);  // removed
                    addToList(cardsList, new EnPanicButton(), extraUpgrades);  // removed
                    addToList(cardsList, new EnDefendBlue(), false);
                    addToList(cardsList, new EnDefendBlue(), false);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {

                case 0:
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), extraUpgrades);

                    AbstractBossCard c7= new EnClaw(cB.clawsPlayed * 2);
                    c7.manualCustomDamageModifier = 2;
                    addToList(cardsList, c7, extraUpgrades);
                    addToList(cardsList, new EnLeap(), extraUpgrades);
                    addToList(cardsList, new EnDefendBlue(), false);
                    addToList(cardsList, new EnStrikeBlue(), true);
                    //Kunai Proc
                    turn++;
                    break;
                case 1:
                    //Turn 3
                    addToList(cardsList, new EnColdSnap(), true);
                    AbstractBossCard c2 = new EnColdSnap();
                    c2.freeToPlayOnce = true;
                    c2.energyGeneratedIfPlayed = 1;
                    addToList(cardsList, c2, true);
                    addToList(cardsList, new EnReprogram(), false);
                    AbstractBossCard c4 = new EnSwiftStrike();
                    c4.manualCustomDamageModifier = 2;
                    addToList(cardsList, c4, false);
                    AbstractBossCard c3 = new EnRebound();
                    c3.manualCustomDamageModifier = 4;
                    addToList(cardsList, c3, false);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), true);
                    AbstractBossCard c= new EnClaw(cB.clawsPlayed * 2);
                    c.manualCustomDamageModifier = 2;
                    addToList(cardsList, c, true);
                    addToList(cardsList, new EnChargeBattery(), false);
                    addToList(cardsList, new EnDefendBlue(), false);
                    addToList(cardsList, new EnShame(), false);
                    //Kunai Proc
                    turn = 0;
                    break;
            }

        }

        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Mutagenic());
    }
}