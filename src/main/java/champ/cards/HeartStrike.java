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

    public HeartStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 10;
        baseMagicNumber = magicNumber = 50;
        postInit();
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        att(new AbstractGameAction() {
            @Override
            public void update() {
                if (m != null && m.currentHealth < m.maxHealth * magicNumber / 100f) {
                    dmg(m, AttackEffect.SLASH_HEAVY);
                }
                isDone = true;
            }
        });
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    public void triggerOnGlowCheck() {
        boolean shouldGlowBeYellow = false;
        for (AbstractMonster m2 : monsterList()) {
            if (m2.currentHealth < (m2.maxHealth * magicNumber / 100f)) {
                shouldGlowBeYellow = true;
                break;
            }
        }
        glowColor = shouldGlowBeYellow ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(5);
    }
}