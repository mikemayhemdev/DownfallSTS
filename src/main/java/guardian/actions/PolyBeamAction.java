//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package guardian.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

public class PolyBeamAction extends AbstractGameAction {
    private AbstractCard card;

    public PolyBeamAction(AbstractCard card) {
        this.card = card;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.SLASH_HORIZONTAL;
        this.duration = 0.1F;
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null && this.target.currentHealth > 0) {
            CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT", 0.5F);

            float randoX = MathUtils.random(-80, 80);
            float randoY = MathUtils.random(-80, 80);
            AbstractDungeon.topLevelEffects.add(new SmallLaserEffect(this.target.hb.cX + (randoX * Settings.scale), this.target.hb.cY + (randoY * Settings.scale), AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY));
            AbstractDungeon.topLevelEffects.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));

            this.card.calculateCardDamage((AbstractMonster)this.target);
            addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn)));
        }
        this.isDone = true;
    }

}
