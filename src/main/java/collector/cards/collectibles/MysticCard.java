package collector.cards.collectibles;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import java.util.concurrent.atomic.AtomicBoolean;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class MysticCard extends AbstractCollectibleCard {
    public final static String ID = makeID(MysticCard.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , 6, 2

    public MysticCard() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        cardsToPreview = new EnragedCenturion();
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
        atb(new HealAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber));
        //TODO what's the best way to change all Centurion cards in all combat piles to a new card, keeping it at the same location and index

    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}