package charbosses.bosses.Watcher.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.bosses.Watcher.ArchetypeBaseWatcher;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.*;
import charbosses.cards.curses.EnInjury;
import charbosses.cards.curses.EnNormality;
import charbosses.cards.purple.*;
import charbosses.powers.bossmechanicpowers.WatcherDivinityPower;
import charbosses.powers.bossmechanicpowers.WatcherSkillPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct3DualityNewAge extends ArchetypeBaseWatcher {

    private AbstractBossCard theVeryImportantSandsOfTime = null;
    private AbstractBossCard theVeryImportantPerseverence = null;

    public ArchetypeAct3DualityNewAge() {
        super("WA_ARCHETYPE_DIVINITY", "Duality");

        maxHPModifier += 380;
        actNum = 3;
        bossMechanicName = WatcherSkillPower.NAME;
        bossMechanicDesc =  WatcherSkillPower.DESCRIPTIONS[0] + 1 + WatcherSkillPower.DESCRIPTIONS[1] + 1 + WatcherSkillPower.DESCRIPTIONS[2] + 1 + WatcherSkillPower.DESCRIPTIONS[3];
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new  WatcherSkillPower(p, 1)));

    }

    public void initialize() {

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_VelvetChoker());
        addRelic(new CBR_Duality());
        addRelic(new CBR_RedMask());
        addRelic(new CBR_Vajra());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        boolean egg = AbstractDungeon.ascensionLevel >= 19;
        if (!looped) {
            switch (turn) {
                case 0:
                    //turn 1
                    //nothing
                    addToList(cardsList, new EnVigilance());
                    addToList(cardsList, new EnCrushJoints(), true);
                    addToList(cardsList, new EnNormality());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnLikeWater());
                    addToList(cardsList, new EnSwiftStrike(), egg);
                    addToList(cardsList, new EnConclude(), egg);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnSashWhip(), true);
                    addToList(cardsList, new EnJAX(), extraUpgrades);
                    addToList(cardsList, new EnInjury());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnFasting());
                    addToList(cardsList, new EnDarkShackles());
                    addToList(cardsList, new EnEmptyFist(), egg);
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnFollowUp(), egg);
                    addToList(cardsList, new EnGoodInstincts());
                    addToList(cardsList, new EnSignatureMove(), egg);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnSwiftStrike(), egg);
                    addToList(cardsList, new EnVigilance());
                    addToList(cardsList, new EnConclude(), egg);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnInjury());
                    addToList(cardsList, new EnFollowUp(), egg);
                    addToList(cardsList, new EnSashWhip(), egg);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnEmptyFist(), egg);
                    addToList(cardsList, new EnGoodInstincts());
                    addToList(cardsList, new EnCrushJoints(), true);
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnJAX(), extraUpgrades);
                    addToList(cardsList, new EnSignatureMove(), egg);
                    addToList(cardsList, new EnNormality());
                    turn = 0;
                    break;
            }
        }

        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_MoltenEgg());
    }
}