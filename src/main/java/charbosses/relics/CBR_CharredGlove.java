package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import charbosses.powers.general.EnemyVigorPower;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
        return DESCRIPTIONS[0] + 3 + DESCRIPTIONS[1];
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
                if (AbstractCharBoss.boss != null && !AbstractCharBoss.boss.isDead && !AbstractCharBoss.boss.isDying) {
                    for (AbstractCard q : AbstractCharBoss.boss.hand.group) {
                        if (q.type == AbstractCard.CardType.CURSE || q.color == AbstractCard.CardColor.CURSE) {
                            flash();
                            addToTop(new ApplyPowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, new EnemyVigorPower(AbstractCharBoss.boss, 3)));
                        }
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
