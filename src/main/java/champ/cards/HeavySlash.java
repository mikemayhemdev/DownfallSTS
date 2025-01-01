package champ.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;
import downfall.powers.NextTurnPowerPower;
import hermit.util.Wiz;

import static champ.ChampMod.loadJokeCardImage;

public class HeavySlash extends AbstractChampCard {
    public final static String ID = makeID("HeavySlash");

    public HeavySlash() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 9;
        postInit();
        loadJokeCardImage(this, "HeavySlash.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        int unblockedDamage = Math.max(damage - m.currentBlock, 0);

        if (unblockedDamage > 0) {
            Wiz.atb(new ApplyPowerAction(p, p, new NextTurnPowerPower(p, new VigorPower(p,unblockedDamage)), unblockedDamage));
            if (Settings.FAST_MODE) {
                this.addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.1F));
            } else {
                this.addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.5F));
            }
        }

    }

    public void upp() {
        upgradeDamage(3);
    }
}