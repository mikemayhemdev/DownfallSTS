package awakenedOne.cards;

import awakenedOne.powers.ManaburnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import hermit.powers.Drained;

import static awakenedOne.AwakenedOneMod.*;

public class DeathCoil extends AbstractAwakenedCard {
    public final static String ID = makeID(DeathCoil.class.getSimpleName());
    // intellij stuff attack, enemy, 13, 6, , , ,

    public DeathCoil() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseMagicNumber = magicNumber = 4;
        loadJokeCardImage(this, makeBetaCardPath(DeathCoil.class.getSimpleName() + ".png"));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToTop(new VFXAction(new HemokinesisEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.5F));
        }
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        this.addToBot(new ApplyPowerAction(m, p, new ManaburnPower(m, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new Drained(p, p, 1), 1));
    }

    public void upp() {
        upgradeMagicNumber(2);
        upgradeDamage(3);
    }
}