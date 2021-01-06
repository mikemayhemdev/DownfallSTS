package collector.cards.Collectibles;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ViciousClaws extends AbstractCollectibleCard {
    public final static String ID = makeID("ViciousClaws");

    public ViciousClaws() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
       damage= baseDamage = 1;
        this.exhaust = true;
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
}
