package champ.cards;

import champ.ChampMod;
import champ.powers.EntangleNextTurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SwordThrow extends AbstractChampCard {

    public final static String ID = makeID("SwordThrow");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 8;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public SwordThrow() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.COMBOBERSERKER);
        tags.add(ChampMod.COMBO);
        // tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //   techique();
        for (int i = 0; i < magicNumber; i++) dmg(m, AbstractGameAction.AttackEffect.SMASH);
        if (!bcombo()) applyToSelf(new EntangleNextTurnPower(1));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}