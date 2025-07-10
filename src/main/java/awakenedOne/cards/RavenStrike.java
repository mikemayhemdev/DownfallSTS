package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.AwakenButton.awaken;

public class RavenStrike extends AbstractAwakenedCard {
    public final static String ID = makeID(RavenStrike.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public RavenStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 7;
        tags.add(CardTags.STRIKE);
        this.tags.add(AwakenedOneMod.CHANT);
        this.exhaust = true;
        loadJokeCardImage(this, makeBetaCardPath(RavenStrike.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (isTrig_chant()) {
            chant();
        }
    }

    @Override
    public void chant() {
        this.addToBot(new BetterDiscardPileToHandAction(1));
        //this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new NextTurnBlockPower(AbstractDungeon.player, this.block), this.block));
        checkOnChant();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void upp() {
        this.exhaust = false;
    }
}