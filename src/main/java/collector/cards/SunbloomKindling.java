package collector.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import expansioncontent.expansionContentMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;
import static collector.util.Wiz.makeInHand;

public class SunbloomKindling extends AbstractCollectorCard {
    public final static String ID = makeID(SunbloomKindling.class.getSimpleName());
    // intellij stuff skill, none, rare, , , , , 2, 1

    public SunbloomKindling() {
        super(ID, -2, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new Ember();
        tags.add(expansionContentMod.UNPLAYABLE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerOnExhaust() {
        CardCrawlGame.sound.play("HEAL_1");
        applyToSelf(new StrengthPower(AbstractDungeon.player, magicNumber));
        makeInHand(new Ember(), 2);
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}