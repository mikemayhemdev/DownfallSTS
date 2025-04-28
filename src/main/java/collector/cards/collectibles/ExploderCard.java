package collector.cards.collectibles;

import collector.cards.Ember;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static collector.CollectorMod.SHAPESWARM;
import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class ExploderCard extends AbstractCollectibleCard {
    public final static String ID = makeID(ExploderCard.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , 3, 1

    public ExploderCard() {
        super(ID, -1, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 10;
        this.tags.add(SHAPESWARM);
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
        allDmg(AbstractGameAction.AttackEffect.FIRE);
    }

    public void upp() {
        upgradeDamage(5);
    }
}