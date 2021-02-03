package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;

public class Sear extends AbstractHexaCard {

    public final static String ID = makeID("Sear");

    //stupid intellij stuff SKILL, ENEMY, BASIC

    private static final int MAGIC = 9;

    public Sear() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        baseBurn = burn = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new FireballEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.5F));
        burn(m, burn);
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBurn(6);
        }
    }
}