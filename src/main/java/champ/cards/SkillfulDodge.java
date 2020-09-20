package champ.cards;

import champ.actions.ModifyBlockAndMagicAction;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SkillfulDodge extends AbstractChampCard {

    public final static String ID = makeID("SkillfulDodge");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 4;
    private static final int MAGIC = 4;

    public SkillfulDodge() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        baseCool = cool = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new CounterPower(magicNumber));
        atb(new ModifyBlockAndMagicAction(uuid, cool));
    }

    public void upp() {
        upgradeCool(2);
    }
}