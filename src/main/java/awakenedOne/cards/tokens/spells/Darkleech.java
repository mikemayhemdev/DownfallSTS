package awakenedOne.cards.tokens.spells;

import awakenedOne.powers.ManaburnPower;
import awakenedOne.relics.EyeOfTheOccult;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.GiantEyeEffect;
import hermit.actions.SoundAction;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.*;

public class Darkleech extends AbstractSpellCard {
    public final static String ID = makeID(Darkleech.class.getSimpleName());
    // intellij stuff skill, all_enemy, , , , , 7, 2

    public Darkleech() {
        super(ID, 1, CardType.SKILL, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 4;
        this.setBackgroundTexture("awakenedResources/images/512/bg_skill_awakened.png", "awakenedResources/images/1024/bg_skill_awakened.png");
        loadJokeCardImage(this, makeBetaCardPath(Darkleech.class.getSimpleName() + ".png"));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)) {
            target = CardTarget.ALL_ENEMY;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SoundAction(makeID("DARKSPELL")));
        if (!AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)) {
            this.addToBot(new VFXAction(new GiantEyeEffect(m.hb.cX, m.hb.cY + 300.0F * Settings.scale, new Color(1.0F, 0.3F, 1.0F, 0.0F))));
            this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
            this.addToBot(new ApplyPowerAction(m, p, new ManaburnPower(m, this.secondMagic), this.secondMagic));
        } else {
            Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            while (var3.hasNext()) {
                AbstractMonster mo = (AbstractMonster) var3.next();
                this.addToBot(new VFXAction(new GiantEyeEffect(mo.hb.cX, mo.hb.cY + 300.0F * Settings.scale, new Color(1.0F, 0.3F, 1.0F, 0.0F))));
                this.addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                this.addToBot(new ApplyPowerAction(mo, p, new ManaburnPower(mo, this.secondMagic), this.secondMagic));
            }
        }

    }

    public void upp() {
        upgradeSecondMagic(2);
        upgradeMagicNumber(1);
    }
}