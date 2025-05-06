package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

@Deprecated
@CardIgnore
public class SoulStrike extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(SoulStrike.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public SoulStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        isEthereal = true;
        baseDamage = 5;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        }
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }
}