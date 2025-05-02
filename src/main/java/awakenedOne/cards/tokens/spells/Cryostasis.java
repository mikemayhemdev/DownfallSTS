package awakenedOne.cards.tokens.spells;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class Cryostasis extends AbstractSpellCard {
    public final static String ID = makeID(Cryostasis.class.getSimpleName());
    // intellij stuff skill, self, , , 7, 1, 1, 1

    public Cryostasis() {
        super(ID, CardType.SKILL, CardTarget.ENEMY);
        baseBlock = 3;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.1F);
        AbstractDungeon.effectsQueue.add(new FrostOrbActivateEffect(p.hb.cX, p.hb.cY));
        blck();
        atb(new ApplyPowerAction(m, AbstractDungeon.player, new WeakPower(m, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.LIGHTNING));
    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}