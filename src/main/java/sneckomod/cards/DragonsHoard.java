package sneckomod.cards;

import com.megacrit.cardcrawl.actions.unique.LimitBreakAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hermit.cards.AbstractDynamicCard;
import sneckomod.SneckoMod;

public class DragonsHoard extends AbstractSneckoCard {

    public final static String ID = makeID("DragonsHoard");

    //cool muddle payout kind of, I like it - blue

    public DragonsHoard() {
        super(ID, 0, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(SneckoMod.OVERFLOW);
        SneckoMod.loadJokeCardImage(this, "DragonsHoard.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
        applyToSelf(new StrengthPower(p, costForTurn));
        }
        if (upgraded) {
            applyToSelf(new StrengthPower(p, costForTurn+1));
        }
        if (isOverflowActive(this)){
            AbstractDungeon.actionManager.addToBottom(new LimitBreakAction());
        }

    }


    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}