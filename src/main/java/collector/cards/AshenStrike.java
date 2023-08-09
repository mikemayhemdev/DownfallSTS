package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.shuffleIn;

public class AshenStrike extends AbstractCollectorCard {
    public final static String ID = makeID(AshenStrike.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 4, , , , 

    public AshenStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 10;
        cardsToPreview = new Ember();
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        AbstractCard q = new Ember();
        shuffleIn(q);
    }

    public void upp() {
        upgradeDamage(3);
    }
}