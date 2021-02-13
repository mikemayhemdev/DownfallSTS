package charbosses.bosses.Watcher.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Watcher.ArchetypeBaseWatcher;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.purple.*;
import charbosses.powers.bossmechanicpowers.WatcherCripplePower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct2CalmNewAge extends ArchetypeBaseWatcher {

    private AbstractBossCard theVeryImportantBlasphemy = null;
    private AbstractBossCard theVeryImportantFlyingSleeves = null;

    public ArchetypeAct2CalmNewAge() {
        super("WA_ARCHETYPE_CALM", "Calm");
        maxHPModifier += 198;
        actNum = 2;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WatcherCripplePower(p, 120), 100));

    }

    public void initialize() {
       /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_SmoothStone());
        addRelic(new CBR_TeardropLocket());
        addRelic(new CBR_IncenseBurner());
       // addRelic(new CBR_MercuryHourglass());
      //  addRelic(new CBR_ArtOfWar());
        //addRelic(new CBR_Duplicator());
       // addRelic(new CBR_Nest());
        //addRelic(new CBR_PhilosopherStone());

    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    if (AbstractDungeon.ascensionLevel >= 19) {
                        AbstractCard c = new EnLikeWater();
                        c.freeToPlayOnce = true;
                        addToList(cardsList, new EnLikeWater(), false);  //removed
                    }
                    addToList(cardsList, new EnLikeWater(), false);  //removed
                    addToList(cardsList, new EnDefendPurple(), false);
                    addToList(cardsList, new EnRagnarok(), true);  //not used
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnStrikePurple(), false);
                    addToList(cardsList, new EnFollowUp(), extraUpgrades);
                    addToList(cardsList, new EnDefendPurple(), false);  //not used
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnWaveOfTheHand(), false);
                    addToList(cardsList, new EnLikeWater(), false);
                    addToList(cardsList, new EnDefendPurple(), true);  //not used
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnWallop(), true);
                    theVeryImportantBlasphemy = new EnBlasphemy();
                    theVeryImportantBlasphemy.newPrio = 1;
                    addToList(cardsList, theVeryImportantBlasphemy, true);
                    theVeryImportantFlyingSleeves = new EnFlyingSleeves();
                    theVeryImportantFlyingSleeves.newPrio = 1;
                    addToList(cardsList, theVeryImportantFlyingSleeves, true);
                    turn++;
                    break;
                case 4:
                    theVeryImportantBlasphemy.newPrio = -2;
                    theVeryImportantFlyingSleeves.newPrio = 0;
                    theVeryImportantFlyingSleeves.lockIntentValues = false;
                    AbstractBossCard c = new EnWish();
                    c.newPrio = -1;
                    addToList(cardsList, c, extraUpgrades);  //removed
                    addToList(cardsList, new EnTranquility(), true);  //not used
                    addToList(cardsList, new EnVigilance());
                    turn++;
                    AbstractCharBoss.boss.powerhouseTurn = true;
                    break;
                case 5:
                    addToList(cardsList, new EnEmptyBody(), false);  //not used
                    addToList(cardsList, new EnWishPlated(), false);  //removed
                    addToList(cardsList, new EnConsecrate(), true);
                    turn=0;
                    looped = true;
                    AbstractCharBoss.boss.powerhouseTurn = false;
                    break;
            }

        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnVigilance());
                    addToList(cardsList, new EnConsecrate(), true);
                    addToList(cardsList, new EnDefendPurple(), false);
                    AbstractCharBoss.boss.powerhouseTurn = false;
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnFollowUp(), extraUpgrades);
                    addToList(cardsList, new EnFlyingSleeves(), true);
                    addToList(cardsList, new EnStrikePurple(), false);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnWaveOfTheHand(), false);
                    addToList(cardsList, new EnDefendPurple(), false);
                    addToList(cardsList, new EnWallop(), true);
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnEmptyBody(), false);
                    addToList(cardsList, new EnRagnarok(), false);
                    addToList(cardsList, new EnDefendPurple(), true);
                    AbstractCharBoss.boss.powerhouseTurn = true;
                    turn = 0;
                    break;
            }
        }

        if (AbstractCharBoss.boss.powerhouseTurn){
            AbstractCharBoss.boss.getPower(WatcherCripplePower.POWER_ID).onSpecificTrigger();
        }
        return cardsList;

    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Enchiridon());
    }

    /*
    AbstractBossCard c = new EnRitualDagger();  //removed
                    c.misc += 5;
                    c.genPreview();
                    c.baseDamage = c.misc;
                    c.isDamageModified = false;
                    addToDeck(c, true);
     */
}