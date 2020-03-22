package evilWithin.monsters;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.MutagenicStrength;
import evilWithin.EvilWithinMod;
import evilWithin.powers.TransformDrawnCardsPower;

public class Augmenter extends AbstractMonster {

    public static final String ID = EvilWithinMod.makeID("Augmenter");
    public static final String NAME = "Augmenter";
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 320.0F;
    boolean move2 = false;

    public Augmenter() {
        super(NAME, ID, 100, HB_X, HB_Y, HB_W, HB_H, "evilWithinResources/images/monsters/augmenter/augmenter.png");
        this.loadAnimation(EvilWithinMod.assetPath("images/monsters/augmenter/augmenter.atlas"), EvilWithinMod.assetPath("images/monsters/augmenter/augmenter.json"), 1.0F);
        switch (AbstractDungeon.actNum) {
            case 1:
                setHp(100);
                break;
            case 2:
                setHp(150);
                break;
            case 3:
                setHp(175);
                break;
        }

        this.damage.add(new DamageInfo(this, 10));
        this.damage.add(new DamageInfo(this, 12));

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.2F);
        this.stateData.setMix("Attack", "Idle", 0.2F);
        this.state.setTimeScale(0.8F);
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
