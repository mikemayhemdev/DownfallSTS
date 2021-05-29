package charbosses.bosses.Watcher.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Watcher.ArchetypeBaseWatcher;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnPanacea;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnParasite;
import charbosses.cards.curses.EnRegret;
import charbosses.cards.purple.*;
import charbosses.powers.bossmechanicpowers.WatcherAngryPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct1RetainNewAge extends ArchetypeBaseWatcher {

    private AbstractBossCard theVeryImportantSandsOfTime = null;
    private AbstractBossCard theVeryImportantPerseverence = null;

    public ArchetypeAct1RetainNewAge() {
        super("WA_ARCHETYPE_RETAIN", "Retain");

        maxHPModifier += 88;
        actNum = 1;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WatcherAngryPower(p)));
    }

    public void initialize() {

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_CloakClasp());
        addRelic(new CBR_BagOfPreparation());
        //addRelic(new CBR_TungstenRod());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    //Turn 1
                    addToList(cardsList, new EnWallop(), extraUpgrades);
                    addToList(cardsList, new EnHalt());
                    addToList(cardsList, new EnDefendPurple(), true);
                    theVeryImportantSandsOfTime = new EnSandsOfTime();
                    theVeryImportantSandsOfTime.newPrio = 1;
                    addToList(cardsList, theVeryImportantSandsOfTime);
                    addToList(cardsList, new EnClumsy());
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    theVeryImportantSandsOfTime.lockIntentValues = false;
                    addToList(cardsList, new EnTalkToTheHand());
                    addToList(cardsList, new EnHalt());
                    addToList(cardsList, new EnCrushJoints());
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    theVeryImportantSandsOfTime.lockIntentValues = false;
                    addToList(cardsList, new EnPanacea());
                    addToList(cardsList, new EnFasting());
                    addToList(cardsList, new EnClumsy());
                    turn++;
                    break;
                case 3:
                    theVeryImportantSandsOfTime.newPrio = -1;
                    theVeryImportantSandsOfTime.lockIntentValues = false;
                    addToList(cardsList, new EnHalt());
                    addToList(cardsList, new EnRegret());
                    addToList(cardsList, new EnParasite());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnHalt());
                    addToList(cardsList, new EnCrushJoints());
                    addToList(cardsList, new EnDefendPurple(), true);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnWallop(), extraUpgrades);
                    addToList(cardsList, new EnHalt());
                    addToList(cardsList, new EnRegret());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnSandsOfTime(3));
                    addToList(cardsList, new EnHalt());
                    addToList(cardsList, new EnParasite());
                    turn = 0;
                    break;
            }
        }

        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_MercuryHourglass());
    }
}