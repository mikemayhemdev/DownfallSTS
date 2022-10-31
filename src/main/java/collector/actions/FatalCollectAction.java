package collector.actions;

import collector.CollectorCollection;
import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

public class FatalCollectAction extends AbstractGameAction {
    public FatalCollectAction() {
        this.actionType = ActionType.DAMAGE;
    }
    @Override
    public void update() {
        for (AbstractMonster M : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (M.isDead || M.isDying){
                System.out.println("Collected!");
                addToTop(new VFXAction(new BiteEffect(M.drawX,M.drawY, CollectorMod.characterColor)));
                CollectorCollection.GetCollectible(M);
            }
        }
        isDone = true;
    }
}
