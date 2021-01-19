package champ.cards;

import basemod.helpers.CardModifierManager;
import champ.util.TechniqueMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TripleStrike extends AbstractChampCard {

    public final static String ID = makeID("TripleStrike");

    // intellij stuff attack, enemy, rare

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public TripleStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        exhaust = true;
        AbstractCard r = new Strike();
        r.updateCost(-999);
        CardModifierManager.addModifier(r, new TechniqueMod());
        cardsToPreview = r;
        CardModifierManager.addModifier(this, new TechniqueMod());
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        AbstractCard r = new Strike();
        if (upgraded) {
            r.upgrade();
        }
        r.updateCost(-999);
        CardModifierManager.addModifier(r, new TechniqueMod());
        makeInHand(r, 2);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
        cardsToPreview.upgrade();
    }
}