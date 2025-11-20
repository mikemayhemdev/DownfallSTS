package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;

public class FlareShot extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(FlareShot.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public FlareShot() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 4;
        this.baseMagicNumber = 1;
        this.tags.add(AwakenedOneMod.CHANT);
        this.magicNumber = this.baseMagicNumber;
        loadJokeCardImage(this, makeBetaCardPath(FlareShot.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        //applyToSelf(new FlarePower(this.magicNumber));


        if (isTrig_chant()) {
            chant();
        }
    }


    @Override
    public void chant() {

        int times = checkChantEffectBonus();

        for (int i = 0; i < times; i++) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, magicNumber * times), 1));

        }

        checkOnChant();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }


    @Override
    public void upp() {
        upgradeDamage(3);
    }
}