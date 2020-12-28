package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class BronzeArmor extends AbstractBronzeCard {

    public final static String ID = makeID("BronzeArmor");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = -1;

    public BronzeArmor() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        baseAuto = auto = 1;
        thisEncodes();
        tags.add(AutomatonMod.BAD_COMPILE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ArtifactPower(p, auto));
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            for (AbstractMonster q : monsterList()) {
                applyToEnemy(q, new ArtifactPower(q, magicNumber));
            }
        }
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}