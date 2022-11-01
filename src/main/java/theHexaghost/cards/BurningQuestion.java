package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class BurningQuestion extends AbstractHexaCard {
    public final static String ID = makeID("BurningQuestion");
    // intellij stuff skill, self, rare, 10, 2, 10, 2, 8, 2
    //ART:

    public BurningQuestion() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseDamage = 10;
        baseBlock = 10;
        baseMagicNumber = magicNumber = 3;
        isMultiDamage = true;
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, magicNumber));
    }

    @Override
    public void afterlife() {
        flash();
        applyToSelf(new DexterityPower(AbstractDungeon.player, 2));
    }

//    @Override
//    public void calculateCardDamage(AbstractMonster mo) {
//        this.damage = baseDamage;
//    }


    @Override
    protected boolean useAfterlifeVFX() {
        return true;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}