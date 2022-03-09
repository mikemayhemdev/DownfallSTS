package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.ReApplyPowersAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hermit.relics.CharredGlove;

public class CBR_CharredGlove extends AbstractCharbossRelic {
    public static final String ID = "CharredGlove";

    public CBR_CharredGlove() {
        super(new CharredGlove());
        setTextureOutline(CharredGlove.IMG, CharredGlove.OUTLINE);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStartPostDraw() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                addToTop(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        AbstractCharBoss.boss.hand.applyPowers();
                    }
                });
                for (AbstractCard q : AbstractCharBoss.boss.hand.group) {
                    if (q.type == AbstractCard.CardType.CURSE || q.color == AbstractCard.CardColor.CURSE) {
                        flash();
                        addToTop(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new LoseStrengthPower(AbstractCharBoss.boss, 4), 4));
                        addToTop(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new StrengthPower(AbstractCharBoss.boss, 4), 4));
                    }
                }
            }
        });
    }

    public void setTexture(Texture t) {
        this.img = t;
        this.outlineImg = t;
    }

    public void setTextureOutline(Texture t, Texture o) {
        this.img = t;
        this.outlineImg = o;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_CharredGlove();
    }
}
