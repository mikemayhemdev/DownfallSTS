package theHexaghost.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.downfallMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.AdvanceAction;
import theHexaghost.actions.ChargeCurrentFlameAction;
import theHexaghost.actions.ExtinguishAction;
import theHexaghost.actions.ExtinguishCurrentFlameAction;
import theHexaghost.ghostflames.InfernoGhostflame;
import theHexaghost.ghostflames.MayhemGhostflame;
import theHexaghost.powers.CrispyPower_new;
import theHexaghost.powers.StopFromAdvancingPower;

import static theHexaghost.HexaMod.renderFlames;

@SpirePatch(
        clz = GameActionManager.class,
        method = "callEndOfTurnActions"
)
public class EndTurnAdvance {
    public static void Postfix(GameActionManager __instance) {
        if (HexaMod.renderFlames) {
            if (GhostflameHelper.activeGhostFlame instanceof MayhemGhostflame)
                GhostflameHelper.activeGhostFlame.advanceTrigger(null);
            if (GhostflameHelper.activeGhostFlame.charged) {
//                if ( GhostflameHelper.activeGhostFlame instanceof InfernoGhostflame ) {
//                    for (int j = GhostflameHelper.hexaGhostFlames.size() - 1; j >= 0; j--) {
//                        AbstractGhostflame gf = GhostflameHelper.hexaGhostFlames.get(j);
//                        if (gf.charged) {
//                            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));  //Critical for keeping the UI not broken, and helps sell the anim
//                            AbstractDungeon.actionManager.addToBottom(new ExtinguishAction(gf));
//                        }
//                    }
//                }

                if(AbstractDungeon.player.hasPower(CrispyPower_new.POWER_ID)){
                    if (AbstractDungeon.player.hasPower(StopFromAdvancingPower.POWER_ID)) {
                        AbstractDungeon.actionManager.addToBottom(new ExtinguishCurrentFlameAction());
                        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                    }
                    int time_of_ignition = AbstractDungeon.player.getPower(CrispyPower_new.POWER_ID).amount;
                    int cards_already_exhausted = CrispyPower_new.exhausted_cards_this_turn;
                    System.out.println("hand size " + AbstractDungeon.player.hand.group.size());
                    for(AbstractCard c: AbstractDungeon.player.hand.group){
                        if(c.isEthereal){
                            cards_already_exhausted += 1;
                        }
                    }
                    if(cards_already_exhausted >= 2){
                        for(int i = 0; i < time_of_ignition; i++){
                            if ( renderFlames ) {
                                AbstractDungeon.actionManager.addToBottom(new ChargeCurrentFlameAction());
                            }
                        }
                        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                    }
                } else if (AbstractDungeon.player.hasPower(StopFromAdvancingPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ExtinguishCurrentFlameAction());
                }

                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (GhostflameHelper.activeGhostFlame.charged) {
                            AbstractDungeon.actionManager.addToBottom(new AdvanceAction(true));
                        }
                        this.isDone = true;
                    }
                });



            }else{

                if(AbstractDungeon.player.hasPower(CrispyPower_new.POWER_ID)){
                    int time_of_ignition = AbstractDungeon.player.getPower(CrispyPower_new.POWER_ID).amount;
                    int cards_already_exhausted = CrispyPower_new.exhausted_cards_this_turn;
                    System.out.println("hand size " + AbstractDungeon.player.hand.group.size());
                    for(AbstractCard c: AbstractDungeon.player.hand.group){
                        if(c.isEthereal){
                            cards_already_exhausted += 1;
                        }
                    }
                    if(cards_already_exhausted >= 2){
                        for(int i = 0; i < time_of_ignition; i++){
                            if ( renderFlames ) {
                                AbstractDungeon.actionManager.addToBottom(new ChargeCurrentFlameAction());
                            }
                        }
                    }
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                }

                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (GhostflameHelper.activeGhostFlame.charged) {
                            AbstractDungeon.actionManager.addToBottom(new AdvanceAction(true));
                        }
                        this.isDone = true;
                    }
                });

            }

            if(GhostflameHelper.hexaGhostFlames.get(5) instanceof InfernoGhostflame) { // only auto extinguish inferno when it's not replaced to others
                AbstractDungeon.actionManager.addToBottom(new ExtinguishAction(GhostflameHelper.hexaGhostFlames.get(5)));
            }
        }
        downfallMod.playedBossCardThisTurn = false;
    }
}