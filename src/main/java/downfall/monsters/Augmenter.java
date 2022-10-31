package downfall.monsters;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.MutagenicStrength;
import downfall.downfallMod;
import downfall.powers.TransformDrawnCardsPower;

public class Augmenter extends AbstractMonster {

    public static final String ID = downfallMod.makeID("Augmenter");
    public static final String NAME = CardCrawlGame.languagePack.getEventString("Drug Dealer").NAME;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 320.0F;
    boolean move2 = false;

    public Augmenter() {
        super(NAME, ID, 200, HB_X, HB_Y, HB_W, HB_H, "downfallResources/images/monsters/augmenter/augmenter.png");
        this.loadAnimation(downfallMod.assetPath("images/monsters/augmenter/augmenter.atlas"), downfallMod.assetPath("images/monsters/augmenter/augmenter.json"), 1.0F);

        this.damage.add(new DamageInfo(this, 12));
        this.damage.add(new DamageInfo(this, 18));

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.2F);
        this.stateData.setMix("Attack", "Idle", 0.2F);
        this.state.setTimeScale(0.8F);

        this.type = EnemyType.ELITE;
    }

    @Override
    public void usePreBattleAction() {
        this.addToTop(new ApplyPowerAction(this, this, new StrengthPower(this, 3), 3));// 25
        this.addToTop(new ApplyPowerAction(this, this, new LoseStrengthPower(this, 3), 3));// 31
        this.addToTop(new RelicAboveCreatureAction(this, RelicLibrary.getRelic(MutagenicStrength.ID)));// 37
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));
                addToBot(new LoseHPAction(this, this, 3));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 3), 3));// 25
                break;
            case 2:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.FIRE));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TransformDrawnCardsPower(AbstractDungeon.player, 2), 2));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }


    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    protected void getMove(int num) {
        if (move2) {
            move2 = false;
            setMove((byte) 1, Intent.ATTACK_BUFF, damage.get(0).base);
        } else {
            move2 = true;
            setMove((byte) 2, Intent.ATTACK_DEBUFF, damage.get(1).base);
        }
    }
}
