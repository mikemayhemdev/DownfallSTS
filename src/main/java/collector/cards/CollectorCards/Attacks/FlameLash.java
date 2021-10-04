package collector.cards.CollectorCards.Attacks;

import collector.CollectorChar;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FlameLash extends AbstractCollectorCard {
    public final static String ID = makeID("FlameLash");

    public FlameLash() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 15;
        douBaseDamage = douDamage = baseDamage;
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
        if (CollectorChar.isRearYou()) {
            atb(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
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
        } else {
            atb(new DamageAction(m, new DamageInfo(CollectorChar.torch, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
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
    }

    @Override
    public void upp() {
        upgradeDamage(5);
    }
}
