package collector.cards.CollectorCards.Attacks;

import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Strike extends AbstractCollectorCard {
    public final static String ID = makeID("Strike");

    public Strike() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY, CollectorCardSource.FRONT);
        FrontBaseDamage = douDamage = baseDamage = 6;
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
        frontDealsDmg = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SMASH, AbstractGameAction.AttackEffect.SMASH);
    }

    @Override
    public void upp() {
        upgradeFrontDamage(3);
        upgradeTorchHeadDamage(3);
    }
}
