package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.vfx;

public class Brainshock extends AbstractAwakenedCard {
    public final static String ID = makeID(Brainshock.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1

    public Brainshock() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 12;
        // baseSecondMagic = secondMagic = 1;
        loadJokeCardImage(this, makeBetaCardPath(Brainshock.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        CardCrawlGame.sound.playA("ORB_LIGHTNING_EVOKE", 0.9F);
        CardCrawlGame.sound.playA("ORB_LIGHTNING_PASSIVE", -0.3F);
        vfx(new LightningEffect(m.hb.cX, m.hb.cY));
        dmg(m, AbstractGameAction.AttackEffect.NONE);

        atb(new MakeTempCardInDiscardAction(new VoidCard(), 1));
        //HexCurse(magicNumber, m, p);
        //this.addToBot(new ForTheHexAction(this.magicNumber, m));
    }

//    public void triggerOnGlowCheck() {
//        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
//        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
//
//        while(var1.hasNext()) {
//            AbstractMonster m = (AbstractMonster)var1.next();
//            if (!m.isDeadOrEscaped() && m.getIntentBaseDmg() == 0) {
//                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
//                break;
//            }
//        }
//
//    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        this.keywords.add(GameDictionary.VOID.NAMES[0].toLowerCase());
    }

    public void upp() {
        upgradeDamage(4);
    }
}