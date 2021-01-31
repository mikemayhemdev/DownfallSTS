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
        if (m.currentHealth >= (m.maxHealth * (magicNumber / 100))) return false;
        return super.canUse(p, m);
    }

    public void upp() {
        upgradeDamage(8);
    }
}