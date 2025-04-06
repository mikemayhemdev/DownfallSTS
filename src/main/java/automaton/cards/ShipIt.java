package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShipIt extends AbstractBronzeCard {

    public final static String ID = makeID("ShipIt");

    // Attack card constants
    private static final int DAMAGE = 5;
    private static final int UPGRADE_DAMAGE = 2;

    public ShipIt() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("ShipIt.png"));
    }

    public static int countCards() {
        int statusCount = 0;
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.type == CardType.STATUS) {
                statusCount++;
            }
        }
        return statusCount;
    }

    public void applyPowers() {
        super.applyPowers();

        if (AbstractDungeon.player != null) {
            this.rawDescription = cardStrings.DESCRIPTION;

            int statusCount = 0;
            for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                if (c.type == CardType.STATUS) {
                    statusCount++;
                }
            }

            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + statusCount + cardStrings.EXTENDED_DESCRIPTION[1];

            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        int count = countCards();
        for (int i = 0; i < count; i++) {
            dmg(m, AbstractGameAction.AttackEffect.FIRE);
        }
    }

    @Override
    public void upp() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}
