package charbosses.bosses.Watcher.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Watcher.ArchetypeBaseWatcher;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.purple.*;
import charbosses.powers.bossmechanicpowers.WatcherCripplePower;
import charbosses.powers.cardpowers.EnemyFearNoEvilPower;
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
        bossMechanicName = WatcherCripplePower.NAME;
        bossMechanicDesc = WatcherCripplePower.DESCRIPTIONS[0] + WatcherCripplePower.LOSE_1_STRENGTH_PER_X_HP  + WatcherCripplePower.DESCRIPTIONS[1];
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WatcherCripplePower(p, 75), 25));

    }

    public void initialize() {

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_VioletLotus());
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
        AbstractCharBoss b = AbstractCharBoss.boss;
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    if (AbstractDungeon.ascensionLevel >= 19) {
                        AbstractBossCard c = new EnLikeWater();
                        c.freeToPlayOnce = true;
                        c.costForTurn = 0;
//                        c.energyGeneratedIfPlayed = 1;
                        addToList(cardsList, c, false); 
                    }
                    addToList(cardsList, new EnMentalFortress (), true); 
                    addToList(cardsList, new EnPressurePoints(), true);
                    this.addToList(cardsList, new EnRagnarok(), false);
                    turn++;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                    break;
                case 1:
                    addToList(cardsList, new EnFearNoEvil(), false);
                    addToList(cardsList, new EnFollowUp(), false);
                    addToList(cardsList, new EnWreathOfFlame(), false);
                    turn++;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                    break;
                case 2:
                    addToList(cardsList, new EnTantrum(), true,3);
                    addToList(cardsList, new EnClumsy(), false);
                    addToList(cardsList, new EnVigilance(), false);
                    turn++;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                    break;
                case 3:
                    addToList(cardsList, new EnLessonLearned(), true);
                    theVeryImportantBlasphemy = new EnBlasphemy();
                    theVeryImportantBlasphemy.newPrio = 1;
                    addToList(cardsList, theVeryImportantBlasphemy, true,6);
                    theVeryImportantFlyingSleeves = new EnFlyingSleeves();
                    theVeryImportantFlyingSleeves.newPrio = 1;
                    addToList(cardsList, theVeryImportantFlyingSleeves, extraUpgrades);
                    turn++;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                    break;
                case 4:
                    theVeryImportantBlasphemy.newPrio = -2;
                    theVeryImportantBlasphemy.lockIntentValues = false;
                    theVeryImportantFlyingSleeves.newPrio = 0;
                    theVeryImportantFlyingSleeves.lockIntentValues = false;
                    AbstractBossCard c = new EnWish();
                    c.newPrio = -1;
                    addToList(cardsList, c, false);
                    addToList(cardsList, new EnRagnarok(), false);
                    addToList(cardsList, new EnTranquility(), true);
                    turn++;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                    break;
                case 5:
                    addToList(cardsList, new EnEmptyBody(), true,3);
                    addToList(cardsList, new EnDevaForm(),  false);
                    addToList(cardsList, new EnLikeWater(), true);
                    turn=0;
                    looped = true;
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                    break;
            }

        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnTantrum(),true,3);
                    addToList(cardsList, new EnFlyingSleeves(), extraUpgrades);
                    addToList(cardsList, new EnFearNoEvil(), false);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(b, b, new EnemyFearNoEvilPower(b)));
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnFollowUp(), false);
                    addToList(cardsList, new EnPressurePoints()， false);
                    addToList(cardsList, new EnEmptyBody(), true);
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnTantrum(),true,3);
                    addToList(cardsList, new EnWreathOfFlame(), false);
                    addToList(cardsList, new EnFearNoEvil(), false);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(b, b, new EnemyFearNoEvilPower(b)));
//                    AbstractCharBoss.boss.powerhouseTurn = true;
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnPressurePoints(), true);
                    addToList(cardsList, new EnRagnarok(), false);
                    addToList(cardsList, new EnVigilance(), false);
//                    AbstractCharBoss.boss.powerhouseTurn = true;
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
