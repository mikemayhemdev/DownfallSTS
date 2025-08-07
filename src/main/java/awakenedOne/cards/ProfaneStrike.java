package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class ProfaneStrike extends AbstractAwakenedCard {
    public final static String ID = makeID(ProfaneStrike.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public ProfaneStrike() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
        tags.add(CardTags.STRIKE);
        loadJokeCardImage(this, makeBetaCardPath(ProfaneStrike.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        this.addToBot(new PutOnDeckAction(p, p, 1, false));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}