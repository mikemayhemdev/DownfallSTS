package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

import static awakenedOne.AwakenedOneMod.makeID;

public class CultDaggers extends AbstractAwakenedCard {
    public final static String ID = makeID(CultDaggers.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 6, 2, , , , 

    public CultDaggers() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard ref = this;
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard q : AbstractDungeon.player.hand.group) {
                    if ((q.costForTurn == 0 || q.freeToPlayOnce) && q != ref) {
                        dmgTop(m, AttackEffect.NONE);
                        if (m != null && m.hb != null) {
                            this.addToTop(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
                        }
                    }
                }
            }
        });
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void upp() {
        upgradeDamage(2);
    }

    public void applyPowers() {
        super.applyPowers();
        int count = 0;
        for (AbstractCard q : AbstractDungeon.player.hand.group) {
            if ((q.costForTurn == 0 || q.freeToPlayOnce) && q != this) {
                count++;
            }
        }

        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + count;
        if (count == 1) {
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[1];
        } else {
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[2];
        }

        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }
}