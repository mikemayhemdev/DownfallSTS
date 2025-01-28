package collector.cards;

import collector.actions.DrawCardFromCollectionAction;
import collector.powers.DoomPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.atb;

public class Blightning extends AbstractCollectorCard {
    public final static String ID = makeID(Blightning.class.getSimpleName());
    // intellij stuff attack, enemy, special, 8, 2, , , 8, 2

    public Blightning() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = 6;
        baseMagicNumber = magicNumber = 6;
        exhaust = true;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SFXAction("ORB_LIGHTNING_EVOKE"));
        atb(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.05F));
        applyToEnemy(m, new DoomPower(m, magicNumber));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        atb(new DrawCardFromCollectionAction());
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }
}