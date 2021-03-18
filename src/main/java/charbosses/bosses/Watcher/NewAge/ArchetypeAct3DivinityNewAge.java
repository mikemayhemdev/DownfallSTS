package charbosses.bosses.Watcher.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnBlind;
import charbosses.cards.colorless.EnGoodInstincts;
import charbosses.cards.curses.EnNormality;
import charbosses.cards.curses.EnShame;
import charbosses.cards.purple.*;
import charbosses.powers.bossmechanicpowers.WatcherDivinityPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct3DivinityNewAge extends ArchetypeBaseDefect {

    private AbstractBossCard theVeryImportantSandsOfTime = null;
    private AbstractBossCard theVeryImportantPerseverence = null;

    public ArchetypeAct3DivinityNewAge() {
        super("WA_ARCHETYPE_DIVINITY", "Divinity");

        maxHPModifier += 398;
        actNum = 3;
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
        addRelic(new CBR_CaptainsWheel());
        addRelic(new CBR_Torii());
        addRelic(new CBR_VelvetChoker());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    //Turn 2
                    addToList(cardsList, new EnWishPlated(), false);
                    addToList(cardsList, new EnGoodInstincts(), false);
                    addToList(cardsList, new EnShame(), false);
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnWaveOfTheHand(), false);
                    addToList(cardsList, new EnSwivel(), true);
                    addToList(cardsList, new EnBlind(), false);
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    AbstractBossCard c = new EnSignatureMove();
                    c.freeToPlayOnce = true;
                    c.energyGeneratedIfPlayed = 2;
                    addToList(cardsList, c, extraUpgrades);
                    addToList(cardsList, new EnConjurBlade(), false);
                    addToList(cardsList, new EnDeceiveReality(), extraUpgrades);
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnProtect(), true);
                    addToList(cardsList, new EnCrushJoints(), false);
                    addToList(cardsList, new EnNormality(), false);
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnExpunger(), false);
                    addToList(cardsList, new EnStrikePurple(), false);
                    addToList(cardsList, new EnDevotion(), false);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnWaveOfTheHand(), false);
                    AbstractBossCard c = new EnSwivel();
                    c.energyGeneratedIfPlayed = 1;
                    addToList(cardsList, c, true);
                    c = new EnCrushJoints();
                    c.freeToPlayOnce = true;
                    addToList(cardsList, c, false);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnExpunger(), false);
                    addToList(cardsList, new EnDeceiveReality(), extraUpgrades);
                    addToList(cardsList, new EnShame(), false);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnProtect(), true);
                    addToList(cardsList, new EnStrikePurple(), false);
                    addToList(cardsList, new EnBlind(), false);
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnSignatureMove(), false);
                    addToList(cardsList, new EnGoodInstincts(), false);
                    addToList(cardsList, new EnNormality(), false);
                    turn = 0;
                    break;
            }
        }

        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Damaru());
    }
}