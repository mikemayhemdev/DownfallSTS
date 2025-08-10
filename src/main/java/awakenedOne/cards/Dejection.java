package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.actions.HandSelectAction;
import hermit.util.Wiz;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;
import static awakenedOne.util.Wiz.atb;

public class Dejection extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Dejection.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");

    public Dejection() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 7;
        //this.baseMagicNumber = this.magicNumber = 2;
        loadJokeCardImage(this, makeBetaCardPath(Dejection.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        Wiz.atb(new HandSelectAction(1, (c) -> true, list -> {
            for (AbstractCard c : list) {
                Wiz.p().hand.moveToExhaustPile(c);
                if (c instanceof AbstractSpellCard) {
                    atb(new GainEnergyAction(1));
                }

            }
            list.clear();
        }, null, uiStrings.TEXT[0], false, false, false));
    }


    @Override
    public void upp() {
        upgradeDamage(3);
    }
}