package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class FaceSlap extends AbstractChampCard {
    public final static String ID = makeID("FaceSlap");

    public FaceSlap() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseMagicNumber = magicNumber = 2;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBOBERSERKER);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (bcombo()) applyToEnemy(m, autoVuln(m, magicNumber));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}