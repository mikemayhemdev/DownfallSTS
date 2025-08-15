package awakenedOne.cards.tokens.spells;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import hermit.util.Wiz;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToEnemy;
import static awakenedOne.util.Wiz.applyToSelf;

public class BurningStudy extends AbstractSpellCard {
    public final static String ID = makeID(BurningStudy.class.getSimpleName());
    // intellij stuff skill, self, , , , , 2, 1

    public BurningStudy() {
        super(ID, 1, CardType.POWER, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        this.setBackgroundTexture("awakenedResources/images/512/bg_power_awakened.png", "awakenedResources/images/1024/bg_power_awakened.png");
        loadJokeCardImage(this, makeBetaCardPath(BurningStudy.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
//        int roll = MathUtils.random(2);
//        if (roll == 0) {
//            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1A"));
//        } else if (roll == 1) {
//            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1B"));
//        } else {
//            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1C"));
//        }
        this.addToBot(new VFXAction(p, new InflameEffect(p), 1.0F));
        applyToSelf(new StrengthPower(p, magicNumber));
        Wiz.forAllMonstersLiving(q -> applyToEnemy(q, new WeakPower(q, 1, false)));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}