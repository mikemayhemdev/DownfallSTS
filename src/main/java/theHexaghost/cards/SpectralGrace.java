package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;

public class SpectralGrace extends AbstractHexaCard{


    public final static String ID = makeID("SpectralGrace");

    private static final int DAMAGE = 13;
    private static final int UPG_DAMAGE = 4;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = -1;

    public SpectralGrace() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        HexaMod.loadJokeCardImage(this, "SpectralGrace.png");
    }

//    @Override
//    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
//        return p.hand.group.size() >= this.magicNumber + 1;
//    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DiscardAction(p, p, this.magicNumber, false));
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(UPG_DAMAGE);
//            this.upgradeMagicNumber(UPG_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

}