package charbosses.bosses.Crowbot.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Crowbot.ArchetypeBaseCrowbot;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnShiv;
import charbosses.cards.crowbot.*;
import charbosses.cards.curses.EnDecay;
import charbosses.cards.curses.EnDoubt;
import charbosses.cards.green.*;
import charbosses.powers.bossmechanicpowers.CrowbotRitualPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeMBSlugNewAge extends ArchetypeBaseCrowbot {
    private boolean ammoUpgraded = false;


    public ArchetypeMBSlugNewAge() {
        super("CB_SLUG_ARCHETYPE", "Slug");

        maxHPModifier += 100;
        actNum = 1;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CrowbotRitualPower(p)));
    }

    public void initialize() {

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_Orichalcum());
        if (AbstractDungeon.ascensionLevel >= 4)
            addRelic(new CBR_Girya(2));
        else
            addRelic(new CBR_Girya(1));
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    //Turn 1
                    addToList(cardsList, new EnBoom());

                    AbstractBossCard c = new EnSlug();
                    c.cost = 0;
                    c.freeToPlayOnce = true;
                    c.modifyCostForCombat(-1);
                    c.manualCustomDamageModifierMult = 2;

                    addToList(cardsList, c);
                    addToList(cardsList, new EnPellet(), extraUpgrades);
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnBarrier());
                    addToList(cardsList, new EnDefend_Crowbot());  //Not played here
                    addToList(cardsList, new EnDoubt());  //Not played here
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnRicochet());

                    AbstractBossCard c2 = new EnHeavySlug();
                    c2.cost = 0;
                    c2.freeToPlayOnce = true;
                    c2.modifyCostForCombat(-1);
                    addToList(cardsList, c2);

                    addToList(cardsList, new EnSlug());

                    turn++;
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnFullMetalJacket(false)); //Removed
                    addToList(cardsList, new EnCannonball()); //Removed
                    addToList(cardsList, new EnDefend_Crowbot(), true);  //Not played here

                    turn++;

                    break;
                case 4:
                    //Turn 5
                    addToList(cardsList, new EnBeam());
                    addToList(cardsList, new EnFanTheHammer());
                    addToList(cardsList, new EnCompressionMold());
                    addToList(cardsList, new EnDesperado(), extraUpgrades);
                    addToList(cardsList, new EnDefend_Crowbot());  //Not played here
                    addToList(cardsList, new EnDefend_Crowbot());  //Not played here

                    turn = 0;
                    looped = true;
                    break;

            }
        } else {

            switch (turn) {
                case 0:
                    AbstractBossCard c0 = new EnBoom();
                    c0.energyGeneratedIfPlayed = 1;

                    addToList(cardsList, c0);

                    AbstractBossCard c = new EnCannonball();
                    c.cost = 0;
                    c.freeToPlayOnce = true;
                    c.modifyCostForCombat(-1);
                    c.manualCustomDamageModifierMult = 2;

                    addToList(cardsList, c, ammoUpgraded);
                    addToList(cardsList, new EnBarrier());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnPellet(), extraUpgrades);
                    addToList(cardsList, new EnDefend_Crowbot());
                    addToList(cardsList, new EnDefend_Crowbot());
                    turn++;
                    break;
                case 2:

                    addToList(cardsList, new EnFanTheHammer());

                    AbstractBossCard c2 = new EnSlug();
                    c2.cost = 0;
                    c2.freeToPlayOnce = true;
                    c2.modifyCostForCombat(-1);

                    addToList(cardsList, c2, ammoUpgraded);
                    addToList(cardsList, c2.makeSameInstanceOf(), ammoUpgraded);

                    AbstractBossCard c3 = new EnFullMetalJacket();
                    c3.cost = 0;
                    c3.freeToPlayOnce = true;
                    c3.modifyCostForCombat(-1);
                    c3.energyGeneratedIfPlayed = 1;

                    addToList(cardsList, c3, ammoUpgraded);

                    addToList(cardsList, new EnDefend_Crowbot(), true);
                    addToList(cardsList, new EnDefend_Crowbot());
                    turn++;

                    break;
                case 3:
                    addToList(cardsList, new EnRicochet());
                    addToList(cardsList, new EnDesperado(), extraUpgrades);
                    addToList(cardsList, new EnDoubt());
                    turn = 0;
                    ammoUpgraded = true;
                    break;
            }
        }
        return cardsList;
    }


    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_OrnamentalFan());
    }
}