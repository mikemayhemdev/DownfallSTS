package charbosses.bosses.Defect.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.blue.*;
import charbosses.cards.colorless.EnPanicButton;
import charbosses.cards.colorless.EnSwiftStrike;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnMalfunctioning;
import charbosses.cards.curses.EnShame;
import charbosses.monsters.BronzeOrbWhoReallyLikesDefectForSomeReason;
import charbosses.powers.bossmechanicpowers.DefectAncientConstructPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.NoBlockPower;
import guardian.powers.ConstructPower;

import java.util.ArrayList;

public class ArchetypeAct2ClawNewAge extends ArchetypeBaseDefect {

    private EnClaw c;
    private CharBossDefect cB;

    public ArchetypeAct2ClawNewAge() {
        super("DF_ARCHETYPE_CLAW", "Claw");

        maxHPModifier += 140;
        actNum = 2;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DefectAncientConstructPower(p, 1), 1));
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
                    addToList(cardsList, new EnPanicButton(), extraUpgrades);  // removed
                    addToList(cardsList, new EnStrikeBlue(), false);
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnReprogram(), false);
                    addToList(cardsList, new EnSwiftStrike(), false);
                    addToList(cardsList, new EnRebound(), false);
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), extraUpgrades);
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
                    addToList(cardsList, new EnHyperbeam(), true);  // removed
                    addToList(cardsList, new EnMalfunctioning(), false);
                    addToList(cardsList, new EnDefendBlue(), false);
                    addToList(cardsList, new EnClumsy(), false);  //removed
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {

                case 0:
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), extraUpgrades);
                    //Play Leap if Block can be gained, otherwise play Stirike
                    if (AbstractCharBoss.boss.hasPower(NoBlockPower.POWER_ID)){
                        addToList(cardsList, new EnColdSnap(), true);
                        addToList(cardsList, new EnStrikeBlue(), true);
                        addToList(cardsList, new EnLeap(), extraUpgrades);
                    } else {
                        addToList(cardsList, new EnColdSnap(), true);
                        addToList(cardsList, new EnLeap(), extraUpgrades);
                        addToList(cardsList, new EnStrikeBlue(), true);
                    }
                    //Kunai Proc
                    turn++;
                    break;
                case 1:
                    //Turn 3
                    addToList(cardsList, new EnHyperbeam(), false);
                    addToList(cardsList, new EnChargeBattery(), false);
                    addToList(cardsList, new EnShame(), false);
                    addToList(cardsList,  new EnRebound(), false);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnReprogram(), false);
                    addToList(cardsList, new EnClaw(cB.clawsPlayed * 2), true);
                    addToList(cardsList,  new EnDefendBlue(), false);
                    addToList(cardsList, new EnSwiftStrike(), false);
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