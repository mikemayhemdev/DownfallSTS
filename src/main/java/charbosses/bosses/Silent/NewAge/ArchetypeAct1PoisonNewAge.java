package charbosses.bosses.Silent.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Silent.ArchetypeBaseSilent;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnShiv;
import charbosses.cards.green.*;
import charbosses.cards.other.Antidote;
import charbosses.powers.bossmechanicpowers.SilentPoisonPower;
import charbosses.relics.*;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ArchetypeAct1PoisonNewAge extends ArchetypeBaseSilent {

    public ArchetypeAct1PoisonNewAge() {
        super("SI_POISON_ARCHETYPE", "Poison");


        maxHPModifier += 100;
        actNum = 1;
        bossMechanicName = SilentPoisonPower.NAME;
        bossMechanicDesc = SilentPoisonPower.DESC[0];
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
//        AbstractCreature p = AbstractDungeon.player;
//        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PoisonProtectionPower(p)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new SilentPoisonPower(AbstractCharBoss.boss)));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Antidote(), 1));
    }

    public void initialize() {

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_TwistedFunnel());
        addRelic(new CBR_Anchor());
        addRelic(new CBR_Lantern());

        // animation
        try {
            Method loadAnimationMethod = AbstractCreature.class.getDeclaredMethod("loadAnimation", String.class, String.class, float.class);
            loadAnimationMethod.setAccessible(true);
            loadAnimationMethod.invoke(AbstractCharBoss.boss, "expansioncontentResources/images/bosses/silent/1/Poison_Silent.atlas", "expansioncontentResources/images/bosses/silent/1/Poison_Silent.json", 1.0f);
            AnimationState.TrackEntry e = AbstractCharBoss.boss.state.setAnimation(0, "Idle", true);
            e.setTimeScale(0.9f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        //boolean WhetstoneUpgrades = AbstractDungeon.ascensionLevel >= 19;
        if (!looped) {
            switch (turn) {
                case 0: //Turn 1

                    addToList(cardsList, new EnBurst());
                    addToList(cardsList, new EnCripplingCloud());
                    AbstractBossCard c = new EnCripplingCloud();
                    c.cost = 0;
                    c.freeToPlayOnce = true;
                    c.modifyCostForCombat(-2);
                    addToList(cardsList, c);
                    addToList(cardsList, new EnSurvivor());
                    turn++;
                    break;

                case 1: //Turn 2

                    addToList(cardsList, new EnPoisonedStab(), true);
                    addToList(cardsList, new EnDodgeAndRoll());
                    addToList(cardsList, new EnStrikeGreen());
                    turn++;
                    break;

                case 2: //Turn 3

                    addToList(cardsList, new EnFootwork(),extraUpgrades);
                    addToList(cardsList, new EnNoxiousFumes());
                    addToList(cardsList, new EnPoisonedStab());
                    turn++;
                    break;

                case 3: //Turn 4

                    addToList(cardsList, new EnBane());
                    addToList(cardsList, new EnStrikeGreen());
                    addToList(cardsList, new EnDeflect());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {

            switch (turn) {
                case 0: // Turn 5 + 3n
                    addToList(cardsList, new EnDodgeAndRoll());
                    addToList(cardsList, new EnPoisonedStab());
                    addToList(cardsList, new EnSurvivor());
                    turn++;
                    break;
                case 1: // Turn 6 + 3n
                    addToList(cardsList, new EnBurst());
                    addToList(cardsList, new EnDeflect());
                    addToList(cardsList, new EnDeflect());
                    addToList(cardsList, new EnPoisonedStab(), true);
//                    c.cost = 0;
//                    c.freeToPlayOnce = true;
//                    c.modifyCostForCombat(-1);
//                    addToList(cardsList, c);
//                    addToList(cardsList, new EnDeflect());
                    turn++;
                    break;
                case 2: // Turn 7 + 3n
                    addToList(cardsList, new EnBane());
                    addToList(cardsList, new EnStrikeGreen());
                    addToList(cardsList, new EnDefendGreen());
                    turn = 0;
                    break;
            }
        }
        return cardsList;
    }


    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Vajra());
    }
}