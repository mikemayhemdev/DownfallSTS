package collector.cards;

import collector.effects.FingerOfDeathEffect;
import collector.powers.DoomPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class FingerOfDeath extends AbstractCollectorCard {
    public final static String ID = makeID(FingerOfDeath.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 50, , , , , 

    public FingerOfDeath() {
        super(ID, 4, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 60;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(p, new FingerOfDeathEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1f));
        if (upgraded) {
            forAllMonstersLiving(q -> applyToEnemy(q, new DoomPower(q, magicNumber)));
        } else {
            applyToEnemy(m, new DoomPower(m, magicNumber));
        }
    }

    public void upp() {
        uDesc();
        target = CardTarget.ALL_ENEMY;
    }
}