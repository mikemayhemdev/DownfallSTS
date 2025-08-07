package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.att;

public class RavenStrike extends AbstractAwakenedCard {
    public final static String ID = makeID(RavenStrike.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public RavenStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 16;
        tags.add(CardTags.STRIKE);
        this.tags.add(AwakenedOneMod.CHANT);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        loadJokeCardImage(this, makeBetaCardPath(RavenStrike.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if (isTrig_chant()) {
            chant();
        }
    }

    @Override
    public void chant() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                att(new PlayTopCardAction(AbstractDungeon.getRandomMonster(), false));
            }
        });
        checkOnChant();
    }


    public void triggerOnGlowCheck() {
        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }


    @Override
    public void upp() {
        upgradeDamage(5);
    }
}