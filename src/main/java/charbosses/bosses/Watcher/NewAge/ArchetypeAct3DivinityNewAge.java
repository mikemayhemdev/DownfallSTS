package charbosses.bosses.Watcher.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.bosses.Watcher.ArchetypeBaseWatcher;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnBlind;
import charbosses.cards.colorless.EnGoodInstincts;
import charbosses.cards.colorless.EnSwiftStrike;
import charbosses.cards.curses.EnInjury;
import charbosses.cards.curses.EnNormality;
import charbosses.cards.purple.*;
import charbosses.powers.bossmechanicpowers.WatcherDivinityPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct3DivinityNewAge extends ArchetypeBaseWatcher {

    private AbstractBossCard theVeryImportantSandsOfTime = null;
    private AbstractBossCard theVeryImportantPerseverence = null;

    public ArchetypeAct3DivinityNewAge() {
        super("WA_ARCHETYPE_DIVINITY", "Divinity");

        maxHPModifier += 348;
        actNum = 3;
        bossMechanicName = WatcherDivinityPower.NAME;
        bossMechanicDesc = WatcherDivinityPower.DESC[0];
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WatcherDivinityPower(p)));

    }

    public void initialize() {
        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_ThreadAndNeedle());
        addRelic(new CBR_Torii());
        addRelic(new CBR_TungstenRod());
        addRelic(new CBR_VelvetChoker());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    //turn 1
                    //nothing
                    addToList(cardsList, new EnWishPlated(), true);
                    addToList(cardsList, new EnGoodInstincts());
                    addToList(cardsList, new EnInjury());
                    turn++;
                    break;
                case 1:
                    //turn 2
                    //25~ damage
                    addToList(cardsList, new EnWaveOfTheHand());
                    addToList(cardsList, new EnBrilliance(), extraUpgrades);  // This is probably a terrible idea. This is where the Blind was, which was moved to where the Strike was.
                    addToList(cardsList, new EnSwivel());
                    turn++;
                    break;
                case 2:
                    //turn 3
                    //off turn
                    addToList(cardsList, new EnSwiftStrike());
                    addToList(cardsList, new EnConjurBlade(), false);
                    addToList(cardsList, new EnSanctity());    //Not played
                    turn++;
                    break;
                case 3:
                    //turn 4
                    //off turn / mercy
                    addToList(cardsList, new EnProtect(), true);
                    addToList(cardsList, new EnEmptyFist(), true);    //Exit Divinity
                    addToList(cardsList, new EnNormality());
                    turn++;
                    break;
                case 4:
                    //turn 5
                    //big attack
                    addToList(cardsList, new EnExpunger());     //Big Attack // I mean, not really that big compared to what time eater hits for.
                    addToList(cardsList, new EnBlind()); // blind moved here to replace the strike
                    addToList(cardsList, new EnDevotion());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    //loop starts here
                    //mercy turn / strength scaling if so
                    addToList(cardsList, new EnWaveOfTheHand());
                    AbstractBossCard c = new EnSwivel();
                    c.energyGeneratedIfPlayed = 1;
                    addToList(cardsList, c);
                    c = new EnEmptyFist();                      //Exit Divinity
                    c.freeToPlayOnce = true;
                    addToList(cardsList, c, true);
                    turn++;
                    break;
                case 1:
                    //big attack turn
                    addToList(cardsList, new EnExpunger());     //Big Attack
                    addToList(cardsList, new EnSanctity());    //Exit Divinity
                    addToList(cardsList, new EnInjury());
                    turn++;
                    break;
                case 2:
                    //spam punish turn
                    addToList(cardsList, new EnProtect(), true);
                    addToList(cardsList, new EnBrilliance(), true); // this might be too difficult to block for but uhhhhhhhhhh
                    addToList(cardsList, new EnBlind());
                    turn++;
                    break;
                case 3:
                    //off turn
                    addToList(cardsList, new EnSwiftStrike(), false); // signature move replaced with wallop
                    addToList(cardsList, new EnGoodInstincts());
                    addToList(cardsList, new EnNormality());
                    turn = 0;
                    break;
            }
        }

        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_DuvuDoll(2));
    }
}