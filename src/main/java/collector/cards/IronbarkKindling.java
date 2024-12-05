package collector.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class IronbarkKindling extends AbstractCollectorCard {
    public final static String ID = makeID(IronbarkKindling.class.getSimpleName());
    // intellij stuff skill, none, uncommon, , , , , 3, 2

    public IronbarkKindling() {
        super(ID, -2, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        baseBlock = 8;
        tags.add(expansionContentMod.UNPLAYABLE);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void triggerOnExhaust() {
        CardCrawlGame.sound.play("GUARDIAN_ROLL_UP");
        blck();
        atb(new DrawCardAction(1));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    public void upp() {
        selfRetain = true;
        uDesc();
    }
}