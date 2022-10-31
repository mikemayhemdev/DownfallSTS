package collector.cards.CollectorCards.Attacks;

import collector.CollectorChar;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FlameLash extends AbstractCollectorCard {
    public final static String ID = makeID("FlameLash");

    public FlameLash() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY,CollectorCardSource.BACK);
        baseDamage = damage = 15;
        RearBaseDamage = douBaseDamage = douDamage = baseDamage;
        magicNumber = baseMagicNumber = 1;
        this.exhaust = true;
    }
    @Override
    public void applyPowers() {
        super.applyPowers();

        if (CollectorChar.isRearYou()) {
            rawDescription = DESCRIPTION;
        } else {
            rawDescription = EXTENDED_DESCRIPTION[0];
        }
        initializeDescription();
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
           dmg(m,AbstractGameAction.AttackEffect.FIRE);
            atb(new SelectCardsInHandAction(magicNumber, ExhaustAction.TEXT[0], true, true, card -> true, Cards -> {
                if (Cards.size() > 0) {
                    atb(new ExhaustSpecificCardAction(Cards.get(0), AbstractDungeon.player.hand));
                    AbstractCard copy = this.makeStatEquivalentCopy();
                    if (cost > 0) {
                        copy.cost = this.cost - 1;
                    }
                    atb(new MakeTempCardInDiscardAction(copy, 1));
                }
            }));
    }

    @Override
    public void upp() {
        upgradeDamage(5);
        upgradeRearDamage(5);
    }
}
