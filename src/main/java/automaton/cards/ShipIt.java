package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShipIt extends AbstractBronzeCard {

    public final static String ID = makeID("ShipIt");

    private static final int DAMAGE = 5;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public ShipIt() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("ShipIt.png"));
    }

    public static int countCards() {
        int count = 0;
        for (AbstractCard card : AbstractDungeon.player.exhaustPile.group) {
            if (card.type == CardType.STATUS) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int count = countCards();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + count + cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);

        // Deal damage for each status card in the exhaust pile
        int statusCount = countCards();
        for (int i = 0; i < statusCount; i++) {
            dmg(m, AbstractGameAction.AttackEffect.FIRE);
        }
    }

    @Override
    public void upp() {
        upgradeDamage(1);
    }
}
