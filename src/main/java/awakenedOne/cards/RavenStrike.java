package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.att;

public class RavenStrike extends AbstractAwakenedCard {
    public final static String ID = makeID(RavenStrike.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public RavenStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 15;
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

        int times = checkChantEffectBonus();
        for (int i = 0; i < times; i++) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    if (!checksoftlock()) {
                        att(new PlayTopCardAction(AbstractDungeon.getRandomMonster(), false));
                    }
                }
            });
        }
        checkOnChant();
    }

    public static boolean checksoftlock(){
        if (!AbstractDungeon.player.drawPile.isEmpty() || AbstractDungeon.actionManager.cardsPlayedThisTurn.size() < 50) {
            return false;
        }
        Iterator var1 = AbstractDungeon.player.discardPile.group.iterator();
        AbstractCard c;
        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (!(c instanceof RavenStrike)) {
                return false;
            }
        }
        return true;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }


    @Override
    public void upp() {
        upgradeDamage(5);
    }
}