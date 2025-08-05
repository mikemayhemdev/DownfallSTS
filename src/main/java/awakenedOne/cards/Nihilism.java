package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.actions.HandSelectAction;
import hermit.util.Wiz;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;

public class Nihilism extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Nihilism.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");

    public Nihilism() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 7;
        this.baseMagicNumber = this.magicNumber = 2;
        loadJokeCardImage(this, makeBetaCardPath(Nihilism.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        Wiz.atb(new HandSelectAction(1, (c) -> true, list -> {
            for (AbstractCard c : list)
            {
                Wiz.p().hand.moveToExhaustPile(c);
                if (c.type == CardType.POWER) {
                    Wiz.atb(new DrawCardAction(magicNumber));
                }

            }
            list.clear();
        }, null, uiStrings.TEXT[0],false,false,false));
    }


    @Override
    public void upp() {
        upgradeDamage(3);
    }
}