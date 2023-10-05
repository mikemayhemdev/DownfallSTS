package collector.cards;

import collector.powers.DoomPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;
import hermit.util.Wiz;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;

public class DarkwoodKindling extends AbstractCollectorCard {
    public final static String ID = makeID(DarkwoodKindling.class.getSimpleName());
    // intellij stuff skill, none, common, , , , , 10, 4

    public DarkwoodKindling() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 8;
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
        CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);
        CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);
        Wiz.forAllMonstersLiving(q -> applyToEnemy(q, new DoomPower(q, magicNumber)));
    }

    public void upp() {
        upgradeMagicNumber(3);
    }
}