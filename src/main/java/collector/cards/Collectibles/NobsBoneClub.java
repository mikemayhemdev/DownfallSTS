package collector.cards.Collectibles;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import static collector.CollectorMod.makeID;

public class NobsBoneClub extends AbstractCollectibleCard {
    public final static String ID = makeID("NobsBoneClub");

    public NobsBoneClub() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        damage = baseDamage = 13;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(4);
    }
}