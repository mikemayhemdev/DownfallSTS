package collector.cards.CollectorCards.Attacks;

import collector.CollectorChar;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TagTeam extends AbstractCollectorCard {
    public final static String ID = makeID("TagTeam");

    public TagTeam() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY, CollectorCardSource.BACK,CollectorCardSource.FRONT);
        RearBaseDamage = douBaseDamage = baseDamage = 9;
        FrontBaseBlock = douBaseBlock = baseBlock = 9;
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
        dmg(m, AbstractGameAction.AttackEffect.SMASH, AbstractGameAction.AttackEffect.SMASH);
        blck();
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}