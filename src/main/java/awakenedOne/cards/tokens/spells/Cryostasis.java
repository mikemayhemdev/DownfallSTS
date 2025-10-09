package awakenedOne.cards.tokens.spells;

import awakenedOne.powers.AphoticFountPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbActivateEffect;
import hermit.actions.SoundAction;

import static awakenedOne.AwakenedOneMod.*;

public class Cryostasis extends AbstractSpellCard {
    public final static String ID = makeID(Cryostasis.class.getSimpleName());

    public Cryostasis() {
        super(ID, 1, CardType.SKILL, CardTarget.SELF);
        baseBlock = 10;
        this.setBackgroundTexture("awakenedResources/images/512/bg_skill_awakened.png", "awakenedResources/images/1024/bg_skill_awakened.png");
        loadJokeCardImage(this, makeBetaCardPath(Cryostasis.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToTop(new SoundAction(makeID("ICESPELL")));
        AbstractDungeon.effectsQueue.add(new FrostOrbActivateEffect(p.hb.cX, p.hb.cY));
        blck();

        if (AbstractDungeon.player.hasPower(AphoticFountPower.POWER_ID)) {
            AbstractDungeon.player.getPower(AphoticFountPower.POWER_ID).onSpecificTrigger();
        }
    }

    public void upp() {
        upgradeBlock(3);
    }
}