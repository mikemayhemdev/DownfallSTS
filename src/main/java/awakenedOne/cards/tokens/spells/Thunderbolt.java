package awakenedOne.cards.tokens.spells;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.att;
import static awakenedOne.util.Wiz.vfx;

public class Thunderbolt extends AbstractSpellCard {
    public final static String ID = makeID(Thunderbolt.class.getSimpleName());
    // intellij stuff attack, enemy, 13, 6, , , , 

    public Thunderbolt() {
        super(ID, CardType.ATTACK, CardTarget.ENEMY);
        baseDamage = 3;
        baseSecondDamage = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        att(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
        vfx(new LightningEffect(m.hb.cX, m.hb.cY));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
    }

    public void upp() {
        upgradeDamage(3);
    }
}