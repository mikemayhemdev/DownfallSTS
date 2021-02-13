package charbosses.monsters;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.DemonFormPower;
import downfall.downfallMod;

public class Fortification extends AbstractMonster {

    public static final String ID = downfallMod.makeID("Fortification");
    public static final String NAME = CardCrawlGame.languagePack.getMonsterStrings(ID).NAME;

    public Fortification() {
        super(NAME, "SpireShield", 170, 0.0F, -20.0F, 250.0F, 290.0F, (String)null, -450.0F, -15F);
        this.type = EnemyType.NORMAL;
        this.loadAnimation("images/monsters/theEnding/shield/skeleton.atlas", "images/monsters/theEnding/shield/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.1F);
        flipHorizontal= true;
    }

    @Override
    public void takeTurn() {
        if (AbstractCharBoss.boss != null) {
            if (!AbstractCharBoss.boss.isDead && !AbstractCharBoss.boss.isDying)
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractCharBoss.boss, this, 10));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        }
    }


    protected void getMove(int num) {
        this.setMove((byte)0, Intent.DEFEND);  // This is irrelevant!
    }

    @Override
    public void die() {
        super.die();
        if (AbstractCharBoss.boss != null) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, BarricadePower.POWER_ID));
            //AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(AbstractCharBoss.boss, AbstractCharBoss.boss));
            this.addToBot(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new DemonFormPower(AbstractCharBoss.boss, 5), 5));

        }
    }
}
