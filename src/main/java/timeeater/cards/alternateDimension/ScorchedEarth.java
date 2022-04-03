package timeeater.cards.alternateDimension;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.suspend.SuspendHelper;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.atb;
import static timeeater.util.Wiz.att;

public class ScorchedEarth extends AbstractDimensionalCard {
    public final static String ID = makeID("ScorchedEarth");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public ScorchedEarth() {
        super(ID, 2, CardType.ATTACK, CardTarget.ENEMY);
        baseDamage = 6;

        setFrame("scorchedearthframe.png");
        exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {

        int count = 0;
        for (AbstractCard c : SuspendHelper.suspendGroup.group) {
            if (c.type != CardType.ATTACK) {
                addToBot(new ExhaustSpecificCardAction(c, p.hand));
                count++;
            }
        }
        for (AbstractCard c : p.discardPile.group) {
            if (c.type != CardType.ATTACK) {
                addToBot(new ExhaustSpecificCardAction(c, p.discardPile));
                count++;
            }
        }
        for (int i = 0; i < count; i++)
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractMonster q = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
                   dmg(m, AttackEffect.FIRE);
                   // att(new DamageAction(q, new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.FIRE));
                }
            });
    }

    public void upp() {
        upgradeDamage(2);
    }
}