package evilWithin.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import evilWithin.EvilWithinMod;
import evilWithin.powers.DrawReductionPowerPlus;

import java.util.ArrayList;

public class FaceTrader extends AbstractMonster {

    public static final String ID = EvilWithinMod.makeID("FaceTrader");
    public static final String NAME = "Face Trader";
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 320.0F;

    int turnNum = 0;

    public FaceTrader() {
        super(NAME, ID, 100, HB_X, HB_Y, HB_W, HB_H, "evilWithinResources/images/monsters/facetrader/facetrader.png");
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

        this.damage.add(new DamageInfo(this, 15));
        this.damage.add(new DamageInfo(this, 10));
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));
                addToBot(new MakeTempCardInDrawPileAction(new Doubt(), 1, true, false));
                break;
            case 2:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.FIRE));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, 1, false), 1));
                break;
            case 3:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.FIRE));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawReductionPowerPlus(AbstractDungeon.player, 1), 1));
                break;
            case 4:
                addToBot(new GainBlockAction(this, 10));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 3), 3));
                break;
            case 5:
                addToBot(new GainBlockAction(this, 10));
                addToBot(new HealAction(this, this, 16));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        ArrayList<Integer> bruh = new ArrayList<Integer>();
        bruh.add(0);
        bruh.add(1);
        bruh.add(2);
        bruh.add(3);
        bruh.add(4);
        if (this.lastMove((byte) 1)) {
            bruh.remove(0);
        } else if (this.lastMove((byte) 2)) {
            bruh.remove(1);
        } else if (this.lastMove((byte) 3)) {
            bruh.remove(2);
        } else if (this.lastMove((byte) 4)) {
            bruh.remove(3);
        } else if (this.lastMove((byte) 5)) {
            bruh.remove(4);
        }
        turnNum = bruh.get(AbstractDungeon.cardRandomRng.random(bruh.size() - 1));
        switch (turnNum) {
            case 0:
                this.setMove((byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
                break;
            case 1:
                this.setMove((byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(1).base);
                break;
            case 2:
                this.setMove((byte) 3, Intent.ATTACK_DEBUFF);
                break;
            case 3:
                this.setMove((byte) 4, Intent.DEFEND_BUFF);
                break;
            case 4:
                this.setMove((byte) 5, Intent.DEFEND_BUFF);
                break;
        }
    }
}
