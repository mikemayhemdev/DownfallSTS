package charbosses.bosses.Silent.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Silent.ArchetypeBaseSilent;
import charbosses.bosses.Silent.CharBossSilent;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnJAX;
import charbosses.cards.colorless.EnShiv;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnDecay;
import charbosses.cards.green.*;
import charbosses.cards.purple.EnFlyingSleeves;
import charbosses.powers.bossmechanicpowers.IroncladStatusPower;
import charbosses.powers.bossmechanicpowers.SilentMirrorImagePower;
import charbosses.relics.*;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

public class ArchetypeAct2MirrorImageNewAge extends ArchetypeBaseSilent {

    public ArchetypeAct2MirrorImageNewAge() {
        super("SI_MIRROR_ARCHETYPE", "Mirror");

        maxHPModifier += 220;
        actNum = 2;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        CharBossSilent p = (CharBossSilent) AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SilentMirrorImagePower(p)));

      //  p.spawnImage(false);
    }

    public void initialize() {

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        // addRelic(new CBR_BagOfPreparation());
        addRelic(new CBR_BagOfMarbles());  //Swapped in from Ginger because the mirror image can't be affected by Ginger.  Gives away the trick due to the debuff animation.
        addRelic(new CBR_Anchor());
        addRelic(new CBR_PaperKrane());
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
                    addToList(cardsList, new EnAfterImage(), true);
                    addToList(cardsList, new EnBackstab());
                    addToList(cardsList, new EnDoppleganger(), true);  //Removed
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnNightmare(), false);
                    addToList(cardsList, new EnDeflect(), extraUpgrades);
                    addToList(cardsList, new EnJAX(), true);
                    addToList(cardsList, new EnDaggerSpray());
                    addToList(cardsList, new EnDecay());  //Not played here
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnDeflect(), extraUpgrades);
                    addToList(cardsList, new EnDeflect(), extraUpgrades);
                    addToList(cardsList, new EnDeflect(), extraUpgrades);
                    addToList(cardsList, new EnOutmaneuver());
                    addToList(cardsList, new EnFlyingKnee());
                    addToList(cardsList, new EnBlur());
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnFootwork()); //Removed
                    addToList(cardsList, new EnLegSweep()); //Removed
                    addToList(cardsList, new EnRiddleWithHoles());  //Not played here
                    turn = 0;
                    looped = true;
                    break;

            }
        } else {

            switch (turn) {
                case 0:
                    addToList(cardsList, new EnJAX(), true);
                    addToList(cardsList, new EnDeflect(), extraUpgrades);
                    addToList(cardsList, new EnDecay());  //Not played here
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnDeflect(), extraUpgrades);
                    addToList(cardsList, new EnDeflect(), extraUpgrades);
                    addToList(cardsList, new EnDaggerSpray());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnOutmaneuver());
                    addToList(cardsList, new EnFlyingKnee());
                    addToList(cardsList, new EnDeflect(), extraUpgrades);
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnLegSweep()); //Removed
                    addToList(cardsList, new EnRiddleWithHoles());  //Not played here
                    addToList(cardsList, new EnBlur());
                    turn = 0;
                    break;
            }
        }
        return cardsList;
    }


    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_OddlySmoothStone());
    }
}