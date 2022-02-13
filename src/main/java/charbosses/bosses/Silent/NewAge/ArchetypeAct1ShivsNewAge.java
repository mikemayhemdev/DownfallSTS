package charbosses.bosses.Silent.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Silent.ArchetypeBaseSilent;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnShiv;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnDecay;
import charbosses.cards.green.*;
import charbosses.powers.bossmechanicpowers.SilentPoisonPower;
import charbosses.powers.bossmechanicpowers.SilentShivTimeEaterPower;
import charbosses.powers.general.PoisonProtectionPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.DeadlyPoison;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct1ShivsNewAge extends ArchetypeBaseSilent {

    public ArchetypeAct1ShivsNewAge() {
        super("SI_SHIV_ARCHETYPE", "Shivs");

        maxHPModifier += 60;
        actNum = 1;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PoisonProtectionPower(p)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new SilentPoisonPower(AbstractCharBoss.boss)));
//        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SilentShivTimeEaterPower(p)));
    }

    public void initialize() {

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_TwistedFunnel());
        addRelic(new CBR_HornCleat());

    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    //Turn 1
                    addToList(cardsList, new EnCripplingCloud()); //Removed
                    addToList(cardsList, new EnSurvivor());
                    addToList(cardsList, new EnBurst());
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnPoisonedStab());
                    addToList(cardsList, new EnDodgeAndRoll());
                    addToList(cardsList, new EnStrikeGreen());
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnBane(), extraUpgrades);
                    addToList(cardsList, new EnDefendGreen());
                    addToList(cardsList, new EnDeflect());
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnFootwork()); //Removed
                    addToList(cardsList, new EnNoxiousFumes()); //Removed
                    addToList(cardsList, new EnDeadlyPoison());
                    turn = 0;
                    looped = true;
                    break;

            }
        } else {

            switch (turn) {
                case 0:
                    addToList(cardsList, new EnDeadlyPoison());
                    addToList(cardsList, new EnStrikeGreen());
                    addToList(cardsList, new EnSurvivor());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnBurst());
                    addToList(cardsList, new EnDodgeAndRoll());
                    AbstractBossCard c = new EnDodgeAndRoll();
                    c.cost = 0;
                    c.freeToPlayOnce = true;
                    c.modifyCostForCombat(-1);
                    addToList(cardsList, c);
                    addToList(cardsList, new EnDeflect());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnBane());
                    addToList(cardsList, new EnPoisonedStab());
                    addToList(cardsList, new EnDefendGreen());
                    turn = 0;
                    break;
            }
        }
        return cardsList;
    }


    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Lantern());
    }
}