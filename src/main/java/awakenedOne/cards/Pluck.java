package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;

public class Pluck extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Pluck.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Pluck() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 1;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (!upgraded)
        atb(new DrawCardAction(p, this.magicNumber));

        if (upgraded) {
            atb(new DrawCardAction(magicNumber, new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractCard c: DrawCardAction.drawnCards)
                        att(new UpgradeSpecificCardAction(c));
                    isDone = true;
                }
            }));
        }

    }

    @Override
    public void upp() {
    }
}