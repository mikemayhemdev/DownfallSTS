package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.actions.ForTheHexAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.shuffleIn;

public class MagicStrike extends AbstractAwakenedCard {
    public final static String ID = makeID(MagicStrike.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1

    public MagicStrike() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 7;
        tags.add(CardTags.STRIKE);
        this.tags.add(AwakenedOneMod.DELVE);
       // baseSecondMagic = secondMagic = 1;
        loadJokeCardImage(this, makeBetaCardPath(MagicStrike.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        atb(new ConjureAction(false));
        shuffleIn(new VoidCard());
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
        upgradeDamage(3);
    }
}