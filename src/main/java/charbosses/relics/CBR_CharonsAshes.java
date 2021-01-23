package charbosses.relics;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Anchor;
import com.megacrit.cardcrawl.relics.CharonsAshes;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.Iterator;

public class CBR_CharonsAshes extends AbstractCharbossRelic {
    public static final String ID = "CharonsAshes";

    public CBR_CharonsAshes() {
        super(new CharonsAshes());
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1];
    }

    @Override
    public void onExhaust(AbstractCard card) {
        this.flash();
        this.addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(this.owner, 3, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDead) {
                this.addToTop(new RelicAboveCreatureAction(mo, this));
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_CharonsAshes();
    }
}
