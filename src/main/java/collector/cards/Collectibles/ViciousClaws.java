package collector.cards.Collectibles;

import collector.Interfaces.PerpetualCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ViciousClaws extends AbstractCollectibleCard implements PerpetualCard {
    public final static String ID = makeID("ViciousClaws");

    public ViciousClaws() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY, CollectorCardSource.FRONT);
        FrontBaseDamage = douDamage = damage= baseDamage = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        if (upgraded){
            dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        }
    }

    @Override
    public void upp() {
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void PerpetualBonus() {

    }
}
