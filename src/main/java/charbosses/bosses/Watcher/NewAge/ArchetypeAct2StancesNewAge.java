package charbosses.bosses.Watcher.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Watcher.ArchetypeBaseWatcher;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnApotheosis;
import charbosses.cards.curses.EnDoubt;
import charbosses.cards.curses.EnInjury;
import charbosses.cards.purple.*;
import charbosses.cards.status.EnWound;
import charbosses.powers.bossmechanicpowers.WatcherCripplePower;
import charbosses.powers.bossmechanicpowers.WatcherSkillPower;
import charbosses.powers.cardpowers.EnemyFearNoEvilPower;
import charbosses.powers.cardpowers.EnemySpotWeaknessPower;
import charbosses.relics.*;
import charbosses.stances.EnCalmStance;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct2StancesNewAge extends ArchetypeBaseWatcher {

    private AbstractBossCard tranquility = null;

    public ArchetypeAct2StancesNewAge() {
        super("WA_ARCHETYPE_STANCES", "Stances");
        maxHPModifier += 160;
        actNum = 2;
        bossMechanicName = WatcherSkillPower.NAME;
        bossMechanicDesc = WatcherSkillPower.DESCRIPTIONS[2];
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WatcherSkillPower(p, 1), 1));

    }

    public void initialize() {
       /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_HornCleat());
        addRelic(new CBR_TungstenRod());
        addRelic(new CBR_BagOfMarbles());

    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        AbstractCharBoss b = AbstractCharBoss.boss;
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnMentalFortress(), false);
                    addToList(cardsList, new EnTantrum(), false);
                    addToList(cardsList, new EnDoubt());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnWeave(), false);
                    addToList(cardsList, new EnFearNoEvil(), false);
                    addToList(cardsList, new EnSimmeringFury(), false);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(b, b, new EnemyFearNoEvilPower(b)));
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnTalkToTheHand(), extraUpgrades);
                    boolean isCalm = b.stance instanceof EnCalmStance;
                    addToList(cardsList, new EnEmptyFist(), false, isCalm ? 2 : 0);
                    addToList(cardsList, new EnWreathOfFlame(), false); // Sometimes used
                    addToList(cardsList, new EnDefendPurple(), false); // Sometimes used
                    tranquility = new EnTranquility();
                    tranquility.newPrio = 1;
                    addToList(cardsList,tranquility, false);  // kept for later
                    turn++;
                    break;
                case 3:
                    tranquility.newPrio = -2;
                    tranquility.lockIntentValues = false;
                    addToList(cardsList, new EnTantrum(), false, 2);
                    addToList(cardsList, new EnEmptyBody(), false);
                    addToList(cardsList, new EnInnerPeace(), false);
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnFlyingSleeves(), extraUpgrades);
                    addToList(cardsList, new EnEmptyFist(), false, 2);
                    addToList(cardsList, new EnMeditate(), false); // TODO: make pretty
                    turn++;
                    break;
                case 5:
                    addToList(cardsList, new EnTantrum(), false, 2);
                    addToList(cardsList, new EnApotheosis(), false);
                    addToList(cardsList, new EnHalt(), true);
                    addToList(cardsList, new EnFearNoEvil(), true);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(b, b, new EnemyFearNoEvilPower(b)));
                    turn=0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    boolean isCalm = b.stance instanceof EnCalmStance;
                    addToList(cardsList, new EnEmptyBody(), true, isCalm ? 2 : 0);
                    addToList(cardsList, new EnSimmeringFury(), true);
                    addToList(cardsList, new EnWreathOfFlame(), true);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnInnerPeace(), true);
                    addToList(cardsList, new EnTantrum(), true, 2);
                    addToList(cardsList, new EnHalt(), true);
                    addToList(cardsList, new EnEmptyFist(), true);
                    addToList(cardsList, new EnMeditate(), true);
                    addToList(cardsList, new EnDoubt());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnTantrum(), true, 2);
                    addToList(cardsList, new EnWeave(), true);
                    addToList(cardsList, new EnEmptyFist(), true);
                    addToList(cardsList, new EnDefendPurple(), true);
                    addToList(cardsList, new EnFearNoEvil(), true);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(b, b, new EnemyFearNoEvilPower(b)));
                    turn = 0;
                    break;
            }
        }

        return cardsList;

    }

    private void addToList(ArrayList<AbstractCard> cardsList, AbstractBossCard card, boolean upgraded, int energyGeneratedIfPlayed) {
        card.energyGeneratedIfPlayed = energyGeneratedIfPlayed;
        addToList(cardsList, card, upgraded);
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_IncenseBurner(2));
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