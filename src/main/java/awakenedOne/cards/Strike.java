package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class Strike extends AbstractAwakenedCard {
    public final static String ID = makeID(Strike.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , , 

    public Strike() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 6;
        tags.add(CardTags.STARTER_STRIKE);
        this.tags.add(CardTags.STRIKE);
        loadJokeCardImage(this, makeBetaCardPath(ID + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
    }

    public void upp() {
        upgradeDamage(3);
    }
}