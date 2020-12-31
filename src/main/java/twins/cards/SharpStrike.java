package twins.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import twins.DonuDecaMod;

public class SharpStrike extends AbstractTwinsCard {

    public final static String ID = makeID("SharpStrike");

    //stupid intellij stuff skill, self, starter

    public SharpStrike() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseDamage = 5;
        baseMagicNumber = 2;
        tags.add(DonuDecaMod.DECA_CARD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        applyToSelf(new ThornsPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}