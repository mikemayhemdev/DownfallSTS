package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Apparition;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CouncilsJustice extends AbstractHexaCard {

    public final static String ID = makeID("CouncilsJustice");

    //stupid intellij stuff ATTACK, ENEMY, SPECIAL

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 4;

    public CouncilsJustice() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = DAMAGE;
        baseMagicNumber = 3;
        exhaust = true;
        isEthereal = true;
        tags.add(CardTags.STRIKE);
        cardsToPreview = new Apparition();
    }

    @Override
    public void applyPowers() {
        int oldBase = baseDamage;
        for (AbstractCard q : AbstractDungeon.player.exhaustPile.group) {
            if (q.cardID.equals(Apparition.ID)) {
                baseDamage += magicNumber;
            }
        }
        super.applyPowers();
        baseDamage = oldBase;
        if (baseDamage != damage) {
            isDamageModified = true;
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int oldBase = baseDamage;
        for (AbstractCard q : AbstractDungeon.player.exhaustPile.group) {
            if (q.cardID.equals(Apparition.ID)) {
                baseDamage += magicNumber;
            }
        }
        super.calculateCardDamage(mo);
        baseDamage = oldBase;
        if (baseDamage != damage) {
            isDamageModified = true;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(1);
        }
    }
}