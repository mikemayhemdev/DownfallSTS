package charbosses.bosses.Silent.Simpler;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Silent.ArchetypeBaseSilent;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.green.*;
import charbosses.cards.other.Antidote;
import charbosses.cards.other.AntidoteAsPower;
import charbosses.cards.other.AntidoteAsPowerExpensive;
import charbosses.powers.bossmechanicpowers.SilentPoisonPower;
import charbosses.powers.general.EnemyPoisonPower;
import charbosses.relics.CBR_HornCleat;
import charbosses.relics.CBR_MarkOfPain;
import charbosses.relics.CBR_SneckoSkull;
import charbosses.relics.CBR_TwistedFunnel;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.actions.SpeechBubbleAction;
import slimebound.SlimeboundMod;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static awakenedOne.util.Wiz.atb;
//import static charbosses.bosses.Silent.CharBossSilent.POISONSPEECH;

public class ArchetypeAct1PoisonSimple extends ArchetypeBaseSilent {

    private int glassKnives;
    public ArchetypeAct1PoisonSimple() {
        super("SI_POISON_ARCHETYPE", "Poison");


        maxHPModifier += 100;
        maxHPModifierAsc = 10;
        actNum = 1;
    }



    @Override
    public void addedPreBattle() {
        super.addedPreBattle();

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new AntidoteAsPower(), 1));

    }

    public void initialize() {

        addRelic(new CBR_SneckoSkull());
        if (AbstractDungeon.ascensionLevel >= 19){
            addRelic(new CBR_HornCleat());
        }

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

        if (!AbstractDungeon.player.hasPower(EnemyPoisonPower.POWER_ID)){

            if (turn != 0) {
                SlimeboundMod.logger.info("Successful poison reset");
                //AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(AbstractCharBoss.boss, TextAboveCreatureAction.TextType.INTERRUPTED));
                //AbstractDungeon.actionManager.addToBottom(new SpeechBubbleAction(POISONSPEECH, AbstractCharBoss.boss, 2F));
                turn = 0;
            }

        }
            switch (turn) {
                case 0: //Turn 1
                    if (looped){
                        addToList(cardsList, new EnBouncingFlask(), extraUpgrades);
                        addToList(cardsList, new EnPoisonedStab(), false);
                    } else {
                        if (extraUpgrades) //Poison Potion
                        addToList(cardsList, new EnNewToxins());
                        addToList(cardsList, new EnBouncingFlask(), extraUpgrades);
                    }

                    turn++;
                    break;

                case 1: //Turn 2

                    addToList(cardsList, new EnDodgeAndRoll());
                    addToList(cardsList, new EnDodgeAndRoll());
                    turn++;
                    break;

                case 2: //Turn 3

                    addToList(cardsList, new EnCripplingCloud(),false);
                    addToList(cardsList, new EnGlassKnife(glassKnives));
                    turn++;
                    break;

                case 3: //Turn 4

                    glassKnives++; //this is here because it confirms glass knife actually got played, not reset
                    addToList(cardsList, new EnBane(), false);
                    addToList(cardsList, new EnBane(), false);

                    turn++;
                    break;

                case 4: //Turn 5
                    addToList(cardsList, new EnDeadlyPoison(), false);
                    addToList(cardsList, new EnNoxiousFumes(), false);
                    turn++;

                    turn = 3;
                    break;


        }
        return cardsList;
    }

}