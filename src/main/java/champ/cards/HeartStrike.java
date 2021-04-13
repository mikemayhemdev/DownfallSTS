package champ.cards;

import champ.ChampMod;

import champ.stances.BerserkerStance;
import champ.stances.UltimateStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import sneckomod.SneckoMod;

public class HeartStrike extends AbstractChampCard {

    public final static String ID = makeID("HeartStrike");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 20;

    public HeartStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(ChampMod.FINISHER);
        baseMagicNumber = magicNumber = 50;
        postInit();
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();

        if (m != null) {
            addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        }

        addToBot(new WaitAction(0.8F));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        finisher();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(!super.canUse(p, m)) return false; // prefer the cantUseMessage from being out of stance

        if (m != null) {
            // targeting check
            if (m.currentHealth >= (m.maxHealth * magicNumber / 100f)) {
                this.cantUseMessage = EXTENDED_DESCRIPTION[1];
                return false;
            }
        } else {
            // in-hand glow check
            for (AbstractMonster m2 : monsterList()) {
                if (m2.currentHealth < (m2.maxHealth * magicNumber / 100f))
                    return true;
            }
            return false;
        }
        return true;
    }

    public void upp() {
        upgradeDamage(10);
    }
}