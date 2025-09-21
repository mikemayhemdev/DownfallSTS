package charbosses.monsters;

import charbosses.powers.bossmechanicpowers.TangerinePower;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.CurlUpPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.downfallMod;

public class LouseTangerine extends AbstractMonster {
    public static final String ID = "FuzzyLouseTangerine";
    private static final MonsterStrings monsterStrings;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("hermit:SpecialFriend");
    public static final String NAME;
    private int biteDamage;
    public boolean sleepy = true;

    public LouseTangerine(float x, float y) {
        super(NAME, "FuzzyLouseNormal", 38, 0.0F, -5.0F, 180.0F, 140.0F, (String)null, x, y);
        this.loadAnimation("expansioncontentResources/images/bosses/hermit/1/tangerine/skeleton_2.atlas", "expansioncontentResources/images/bosses/hermit/1/tangerine/skeleton_2.json", 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        // Set damage.
        if (AbstractDungeon.ascensionLevel >= 9) {
            biteDamage = 8;
        } else {
            biteDamage = 6;
        }

        damage.add(new DamageInfo(this, biteDamage));
    }

    @Override
    public void usePreBattleAction()
    {
        int curl_amount = 8;
        if (AbstractDungeon.ascensionLevel >= 9) curl_amount = 12;

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, curl_amount)));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new TangerinePower(this, 3)));
        setMove((byte)1, AbstractMonster.Intent.SLEEP);
    }

    @Override
    public void takeTurn() {
        switch(this.nextMove) {
            case 3:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                break;
            case 4:
                if (AbstractDungeon.ascensionLevel >= 9) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 4), 4));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 3), 3));
                }
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    protected void getMove(int num) {
        if (sleepy)
        {
            if (this.lastMove((byte)1))
            {
                setMove((byte)2, AbstractMonster.Intent.SLEEP);
            }
            else {
                sleepy = false;
                setMove(MOVES[0], (byte) 4, Intent.BUFF);
            }
        }
        else if (this.lastMove((byte)4)) {
                this.setMove((byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
            } else {
                this.setMove(MOVES[0], (byte)4, Intent.BUFF);
            }
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("FuzzyLouseNormal");
        NAME = uiStrings.TEXT[0];
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}