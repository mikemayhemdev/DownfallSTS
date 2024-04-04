package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DiscardAndDamage extends AbstractHexaCard{


    public final static String ID = makeID("DiscardAndDamage");

    private static final int DAMAGE = 18;
    private static final int UPG_DAMAGE = 1;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = -1;

    public DiscardAndDamage() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return p.hand.group.size() >= this.magicNumber + 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DiscardAction(p, p, this.magicNumber, false));
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPG_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

}
