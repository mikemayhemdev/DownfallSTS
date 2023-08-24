package charbosses.bosses.Silent.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Silent.ArchetypeBaseSilent;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnShiv;
import charbosses.cards.green.*;
import charbosses.powers.bossmechanicpowers.SilentPoisonPower;
import charbosses.powers.bossmechanicpowers.SilentShivTimeEaterPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Dash;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct3ShivsNewAge extends ArchetypeBaseSilent {

    public ArchetypeAct3ShivsNewAge() {
        super("SI_SHIV_ARCHETYPE", "Shivs");

        maxHPModifier += 350;
        actNum = 3;
        bossMechanicName =SilentShivTimeEaterPower.NAME;
        bossMechanicDesc = SilentShivTimeEaterPower.DESC[0];
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new SilentShivTimeEaterPower(AbstractCharBoss.boss)));
    }

    public void initialize() {

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_FusionHammer());
        addRelic(new CBR_MummifiedHand());
        addRelic(new CBR_Shuriken());
        addRelic(new CBR_OrnamentalFan());

    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    //Turn 1
                    addToList(cardsList, new EnPredator());
                    addToList(cardsList, new EnFlyingKnee());
                    addToList(cardsList, new EnBurst());
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnFlechettes());
                    addToList(cardsList, new EnLegSweep());
                    addToList(cardsList, new EnCloakAndDagger());
                    addToList(cardsList, new EnNeutralize());
                    addToList(cardsList, new EnBlur());  //Removed

                    turn++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnEndlessAgony());
                    addToList(cardsList, new EnEndlessAgony());
                    addToList(cardsList, new EnFinisher(2), extraUpgrades);
                    addToList(cardsList, new EnDefendGreen());
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnBladeDance());
                    addToList(cardsList, new EnSuckerPunch());
                    addToList(cardsList, new EnDaggerSpray());
                    turn++;
                    break;
                case 4:
                    //Turn 4
                    addToList(cardsList, new EnPiercingWail());
                    addToList(cardsList, new EnAfterImage());
                    AbstractBossCard c = new EnDash();
                    c.cost = 0;
                    c.freeToPlayOnce = true;
                    c.modifyCostForCombat(-2);
                    addToList(cardsList, c);
                    turn = 0;
                    looped = true;
                    break;

            }
        } else {

            switch (turn) {
                case 0:
                    //Turn 4
                    //addToList(cardsList, new EnShiv());
                    addToList(cardsList, new EnBurst());
                    addToList(cardsList, new EnCloakAndDagger());
                    AbstractBossCard c = new EnCloakAndDagger();
                    c.freeToPlayOnce = true;
                    c.costForTurn = 0;
                    addToList(cardsList, c);
                    addToList(cardsList, new EnSuckerPunch());
                    turn++;
                    break;
                case 1:
                    //Turn 4
//                    //addToList(cardsList, new EnShiv());
                    addToList(cardsList, new EnPredator());
                    addToList(cardsList, new EnDaggerSpray());
                    addToList(cardsList, new EnDefendGreen());
                    turn++;
                    break;
                case 2:
//                    //addToList(cardsList, new EnShiv());
                    addToList(cardsList, new EnFlyingKnee());
                    addToList(cardsList, new EnFlechettes());
                    addToList(cardsList, new EnFinisher(2), extraUpgrades);
                    addToList(cardsList, new EnLegSweep());
                    addToList(cardsList, new EnBlur());
                    turn++;
                    break;
                case 3:
//                    //addToList(cardsList, new EnShiv());
                    addToList(cardsList, new EnDash());
                    addToList(cardsList, new EnNeutralize());
                    addToList(cardsList, new EnBladeDance());
                    turn = 0;
                    break;
            }
        }
        return cardsList;
    }


    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Kunai());
    }
}
