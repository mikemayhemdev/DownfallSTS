package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Philosophize extends AbstractBronzeCard {

    public final static String ID = makeID("Philosophize");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = -1;

    public Philosophize() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        baseAuto = auto = 1;
        thisEncodes();
        tags.add(AutomatonMod.BAD_COMPILE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, auto));
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            for (AbstractMonster q : monsterList()) {
                applyToEnemy(q, new StrengthPower(q, magicNumber));
            }
        }
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}