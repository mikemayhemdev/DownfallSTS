package charbosses.bosses.Silent.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Silent.ArchetypeBaseSilent;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnSadisticNature;
import charbosses.cards.colorless.EnShiv;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnRegret;
import charbosses.cards.green.*;
import charbosses.powers.bossmechanicpowers.SilentPoisonPower;
import charbosses.powers.bossmechanicpowers.SilentShivTimeEaterPower;
import charbosses.powers.general.PoisonProtectionPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct3PoisonNewAge extends ArchetypeBaseSilent {

    public ArchetypeAct3PoisonNewAge() {
        super("SI_POISON_ARCHETYPE", "Poison");

        maxHPModifier += 350;
        actNum = 3;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PoisonProtectionPower(p)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new SilentPoisonPower(AbstractCharBoss.boss)));

    }

    public void initialize() {

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        // addRelic(new CBR_BagOfPreparation());
        //addRelic(new CBR_Lantern());
        addRelic(new CBR_Lantern());
        addRelic(new CBR_FusionHammer());
        addRelic(new CBR_SneckoSkull());
        addRelic(new CBR_TwistedFunnel());
        // addRelic(new CBR_DreamCatcher());
        // addRelic(new CBR_Cleric()); // Cleric to remove +1 Strike
        // addRelic(new CBR_UpgradeShrine()); // To upgrade Infinite Blades
        // addRelic(new CBR_WeMeetAgain());

    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    //Turn 1
                    addToList(cardsList, new EnNoxiousFumes());
                    addToList(cardsList, new EnDeadlyPoison(), true);
                    addToList(cardsList, new EnCripplingCloud());
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnDeflect(), true);
                    addToList(cardsList, new EnNeutralize());
                    addToList(cardsList, new EnFootwork(), true);  //Removed
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnFootwork());
                    addToList(cardsList, new EnBurst());
                    addToList(cardsList, new EnDodgeAndRoll());
                    AbstractBossCard c = new EnDodgeAndRoll();
                    c.freeToPlayOnce = true;
                    c.costForTurn = 0;
                    addToList(cardsList, c);
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnBouncingFlask(), extraUpgrades);
                    addToList(cardsList, new EnCatalyst());
                    addToList(cardsList, new EnRegret());
                    turn++;
                    break;
                case 4:
                    //Turn 4
                    addToList(cardsList, new EnDeadlyPoison());
                    addToList(cardsList, new EnPoisonedStab());
                    addToList(cardsList, new EnNoxiousFumes());
                    turn = 0;
                    looped = true;
                    break;

            }
        } else {

            switch (turn) {
                case 0:
                    //Turn 4
                    addToList(cardsList, new EnDeadlyPoison());
                    addToList(cardsList, new EnPoisonedStab());
                    addToList(cardsList, new EnDeflect(), true);
                    turn++;
                    break;
                case 1:
                    //Turn 4
                    addToList(cardsList, new EnDeadlyPoison(), true);
                    addToList(cardsList, new EnNeutralize());
                    addToList(cardsList, new EnDodgeAndRoll());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnBurst());
                    addToList(cardsList, new EnBouncingFlask(), extraUpgrades);
                    AbstractBossCard c = new EnBouncingFlask();
                    c.freeToPlayOnce = true;
                    c.costForTurn = 0;
                    if (extraUpgrades) c.upgrade();
                    addToList(cardsList, c);
                    addToList(cardsList, new EnRegret());
                    turn = 0;
                    break;
            }
        }
        return cardsList;
    }


    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_OrangePellets());
    }
}